package com.kancy.district.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kancy.district.dao.DistrictAreaChangeInfoDao;
import com.kancy.district.dao.DistrictAreaDetailDao;
import com.kancy.district.dao.DistrictAreaInfoDao;
import com.kancy.district.entity.DistrictAreaChangeInfo;
import com.kancy.district.entity.DistrictAreaDetail;
import com.kancy.district.entity.DistrictAreaInfo;
import com.kancy.district.service.DistrictAreaInfoService;
import com.kancy.district.service.parser.AreaData;
import com.kancy.district.service.parser.DistrictFileParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 服务接口实现
 *
 * @author kancy
 * @since 2022-01-12 11:25:25
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DistrictAreaInfoServiceImpl implements DistrictAreaInfoService {
    private final DistrictAreaInfoDao districtAreaInfoDao;
    private final DistrictAreaChangeInfoDao districtAreaChangeInfoDao;
    private final DistrictAreaDetailDao districtAreaDetailDao;

    @PostConstruct
    @SneakyThrows
    @Override
    public void init() {
        districtAreaInfoDao.remove(Wrappers.lambdaQuery());
        districtAreaChangeInfoDao.remove(Wrappers.lambdaQuery());
        districtAreaDetailDao.remove(Wrappers.lambdaQuery());

        Map<String, List<AreaData>> dataProperties = DistrictFileParser.parseRangeDistrictFiles(1990);

        // 变更的城市
        List<DistrictAreaChangeInfo> districtAreaChangeInfoList = new ArrayList<>();
        List<DistrictAreaInfo> districtAreaInfoList = new ArrayList<>();
        List<DistrictAreaDetail> districtAreaDetailList = new ArrayList<>();

        dataProperties.forEach((code, list)->{
            AreaData newestAreaData = list.get(0);

            int size = list.size();
            for (AreaData areaData : list) {
                // 码表数据
                DistrictAreaInfo districtAreaInfo = initDistrictAreaInfo(areaData);
                districtAreaInfoList.add(districtAreaInfo);
                // 变更数据
                if (size > 1 && !Objects.equals(newestAreaData, areaData)){
                    DistrictAreaChangeInfo districtAreaChangeInfo = initDistrictAreaChangeInfo(areaData);
                    districtAreaChangeInfo.setNewestCode(newestAreaData.getCode());
                    districtAreaChangeInfo.setNewestAddress(newestAreaData.getName());
                    districtAreaChangeInfoList.add(districtAreaChangeInfo);
                }
                // 整理明细数据
                if (areaData.isArea()){
                    List<AreaData> cityAreaDatas = dataProperties.get(areaData.getParentCode());
                    List<AreaData> propertyAreaDatas = dataProperties.get(String.format("%s0000", areaData.getCode().substring(0,2)));
                    // 城市不存在的，用县区代替
                    if (Objects.isNull(cityAreaDatas)){
                        cityAreaDatas = Collections.singletonList(areaData);
                    }
                    for (AreaData propertyAreaData : propertyAreaDatas) {
                        for (AreaData cityAreaData : cityAreaDatas) {
                            districtAreaDetailList.add(getDistrictAreaDetail(areaData, propertyAreaData, cityAreaData));
                        }
                    }
                }
            }
        });
        // 补充特别行政区和台湾省
        districtAreaDetailList.add(initProvinceDistrictAreaDetail("710000",dataProperties));
        districtAreaDetailList.add(initProvinceDistrictAreaDetail("810000",dataProperties));
        districtAreaDetailList.add(initProvinceDistrictAreaDetail("820000",dataProperties));
        dataProperties.clear();

        // 排序入库
        districtAreaInfoList.sort((o1, o2) -> {
            if (o1.getCode().compareTo(o2.getCode()) == 0) {
                return o1.getYear().compareTo(o2.getYear());
            }
            return o1.getCode().compareTo(o2.getCode());
        });
        districtAreaInfoDao.saveBatch(districtAreaInfoList, 2000);
        districtAreaInfoList.clear();

        // 变更排序入库
        districtAreaChangeInfoList.sort((o1, o2) -> {
            if (o1.getCode().compareTo(o2.getCode()) == 0) {
                return o1.getYear().compareTo(o2.getYear());
            }
            return o1.getCode().compareTo(o2.getCode());
        });
        districtAreaChangeInfoDao.saveBatch(districtAreaChangeInfoList);
        districtAreaChangeInfoList.clear();

        // 变更详情
        districtAreaDetailList.sort((o1, o2) -> {
            if (o1.getAreaCode().compareTo(o2.getAreaCode()) == 0) {
                return o1.getYear().compareTo(o2.getYear());
            }
            return o1.getAreaCode().compareTo(o2.getAreaCode());
        });
        districtAreaDetailDao.saveBatch(districtAreaDetailList,2000);
        districtAreaDetailList.clear();

    }

    private DistrictAreaDetail getDistrictAreaDetail(AreaData areaData, AreaData propertyAreaData, AreaData cityAreaData) {
        DistrictAreaDetail detail = new DistrictAreaDetail();
        detail.setAreaCode(areaData.getCode());
        detail.setAreaName(areaData.getName());
        detail.setCityCode(cityAreaData.getCode());
        detail.setCityName(cityAreaData.getName());
        detail.setProvinceCode(propertyAreaData.getCode());
        detail.setProvinceName(propertyAreaData.getName());
        detail.setVersion(areaData.getVersion());
        detail.setYear(areaData.getYear());
        detail.setIsAutonomousRegion(propertyAreaData.isAutonomousRegion());
        detail.setIsSpecialAdministrativeRegion(propertyAreaData.isSpecialAdministrativeRegion());
        detail.setIsProvincialCounties(areaData.isProvincialCounties());
        detail.setIsMunicipality(propertyAreaData.isMunicipality());
        return detail;
    }

    private DistrictAreaDetail initProvinceDistrictAreaDetail(String code, Map<String, List<AreaData>> dataProperties) {
        List<AreaData> areaDatas = dataProperties.get(code);
        AreaData areaData = areaDatas.iterator().next();
        return getDistrictAreaDetail(areaData, areaData, areaData);
    }

    private DistrictAreaInfo initDistrictAreaInfo(AreaData areaData) {
        DistrictAreaInfo districtAreaInfo = new DistrictAreaInfo();
        districtAreaInfo.setCode(areaData.getCode());
        districtAreaInfo.setAddress(areaData.getName());
        districtAreaInfo.setCodeType(areaData.getType());
        districtAreaInfo.setParentCode(areaData.getParentCode());
        districtAreaInfo.setVersion(areaData.getVersion());
        districtAreaInfo.setYear(areaData.getYear());
        districtAreaInfo.setLabel(areaData.getLabel());
        return districtAreaInfo;
    }

    private DistrictAreaChangeInfo initDistrictAreaChangeInfo(AreaData areaData) {
        DistrictAreaChangeInfo districtAreaChangeInfo = new DistrictAreaChangeInfo();
        districtAreaChangeInfo.setCode(areaData.getCode());
        districtAreaChangeInfo.setAddress(areaData.getName());
        districtAreaChangeInfo.setCodeType(areaData.getType());
        districtAreaChangeInfo.setParentCode(areaData.getParentCode());
        districtAreaChangeInfo.setVersion(areaData.getVersion());
        districtAreaChangeInfo.setYear(areaData.getYear());
        districtAreaChangeInfo.setLabel(areaData.getLabel());
        return districtAreaChangeInfo;
    }
}