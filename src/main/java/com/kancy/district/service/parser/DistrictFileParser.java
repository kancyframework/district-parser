package com.kancy.district.service.parser;

import com.kancy.district.enums.AreaLabelEnum;
import com.kancy.district.enums.AreaTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Utils
 *
 * @author huangchengkang
 * @date 2022/1/12 13:03
 */
@Slf4j
public class DistrictFileParser {

    private static final int MIN_START_YEAR = 1980;
    private static final String PATH_FORMAT = "/district/%d.properties";

    /**
     * 直辖市
     */
    private static final List<String> municipality = Arrays.asList("上海市", "北京市", "重庆市", "天津市");
    /**
     * 自治区
     */
    private static final List<String> autonomousRegion = Arrays.asList("内蒙古自治区", "新疆维吾尔自治区", "广西壮族自治区", "宁夏回族自治区", "西藏自治区");
    /**
     * 特别行政区
     */
    private static final List<String> specialAdministrativeRegion = Arrays.asList("香港特别行政区", "澳门特别行政区");

    /**
     * 省直辖县：全国一共有29个省直辖县，其中河南1个，湖北4个，海南15个，新疆9个
     * 城市用省直辖县填充
     */
    private static final Map<String, List<String>> provincialCounties = new HashMap<>();
    static {
        provincialCounties.put("河南省", Collections.singletonList("济源市"));
        provincialCounties.put("湖北省", Arrays.asList("仙桃市","潜江市","天门市","神农架林区"));
        provincialCounties.put("海南省", Arrays.asList("五指山市","文昌市","琼海市","万宁市","东方市","定安县","屯昌县","澄迈县","临高县","琼中黎族苗族自治县","保亭黎族苗族自治县","白沙黎族自治县","昌江黎族自治县","乐东黎族自治县","陵水黎族自治县"));
        provincialCounties.put("新疆维吾尔自治区", Arrays.asList("石河子市","阿拉尔市","图木舒克市","五家渠市","北屯市","铁门关市","双河市","可克达拉市","昆玉市","胡杨河市","新星市"));
    }

    /**
     * 解析所有行政区划文件
     * @return
     * @throws IOException
     */
    public static Map<String, List<AreaData>> parseAllDistrictFiles() throws IOException {
        return parseRangeDistrictFiles(MIN_START_YEAR);
    }

    /**
     * 解析最新的行政区划文件
     * @return
     * @throws IOException
     */
    public static Map<String, List<AreaData>> parseNewestDistrictFile() throws IOException {
        int newestYear = MIN_START_YEAR;
        for (int year = MIN_START_YEAR; year < MIN_START_YEAR + 100; year++) {
            String resourceName = String.format(PATH_FORMAT, year);
            if (Objects.nonNull(DistrictFileParser.class.getResource(resourceName))){
                newestYear = year;
            }
        }
        return parseRangeDistrictFiles(newestYear);
    }

    /**
     * 解析指定年份的行政区划文件
     * @param year
     * @return
     * @throws IOException
     */
    public static Map<String, List<AreaData>> parseDistrictFile(int year) throws IOException {
        return parseRangeDistrictFiles(year, year+1);
    }

    /**
     * 解析指定年份的行政区划文件
     * @param startYear
     * @return
     * @throws IOException
     */
    public static Map<String, List<AreaData>> parseRangeDistrictFiles(int startYear) throws IOException {
        return parseRangeDistrictFiles(startYear, startYear + 100);
    }
    /**
     * 解析指定年份范围的行政区划文件
     * @param startYear
     * @return
     * @throws IOException
     */
    public static Map<String, List<AreaData>> parseRangeDistrictFiles(int startYear, int endYear) throws IOException {
        Map<String, List<AreaData>> root = new HashMap<>();
        for (int year = startYear; year < endYear; year++) {
            String resourceName = String.format(PATH_FORMAT, year);
            InputStream resourceAsStream = DistrictFileParser.class.getResourceAsStream(resourceName);
            if (Objects.isNull(resourceAsStream)){
                continue;
            }

            Properties properties = new Properties();
            properties.load(new InputStreamReader(resourceAsStream, "UTF-8"));

            int finalYear = year;
            properties.forEach((code, name)->{
                if (root.containsKey(code)){
                    List<AreaData> areaDataSets = root.get(code);
                    AreaData areaData = initAreaData(code, name, areaDataSets.size() * 10, finalYear);
                    if (!areaDataSets.contains(areaData)){
                        areaDataSets.add(areaData);
                        log.info("改名的地区：code = {} , name = {} , version={}", code, name, finalYear);
                    }
                } else {
                    List<AreaData> areaDataSets = new LinkedList<>();
                    areaDataSets.add(initAreaData(code, name, 0, finalYear));
                    root.put(code.toString(), areaDataSets);
                }
            });

            log.info("{}年的行政区划解析完成：{}，行政区划数量：{}", year, resourceName, properties.size());
        }

        // 设置区域标签
        setAreaLabels(root);

        log.info("所有文件解析完成：{}", root.size());
        return root;
    }


    private static void setAreaLabels(Map<String, List<AreaData>> root) {
        root.forEach((code, list)->{
            Collections.sort(list);
            for (AreaData areaData : list) {
                String type = areaData.getType();
                String name = areaData.getName();
                if (Objects.equals(type, AreaTypeEnum.AREA.name())){
                    if (isProvincialCounties(root, areaData)){
                        // 省直辖县
                        for (AreaData data : list) {
                            //data.setParentCode(String.format("%s0000", data.getCode().substring(0, 2)));
                            data.setLabel(AreaLabelEnum.PROVINCIAL_COUNTIES.getDescription());
                        }
                        return;
                    }
                } else if(Objects.equals(type, AreaTypeEnum.PROVINCE.name())){
                    if (municipality.contains(name)){
                        // 直辖市
                        areaData.setLabel(AreaLabelEnum.MUNICIPALITY.getDescription());
                    } else if (autonomousRegion.contains(name)){
                        // 自治区
                        areaData.setLabel(AreaLabelEnum.AUTONOMOUS_REGION.getDescription());
                    } else if (specialAdministrativeRegion.contains(name)){
                        // 特别行政区
                        areaData.setLabel(AreaLabelEnum.SPECIAL_ADMINISTRATIVE_REGION.getDescription());
                    }
                }
            }
        });
    }

    private static boolean isProvincialCounties(Map<String, List<AreaData>> root, AreaData areaData) {
        List<AreaData> provinces = root.get(String.format("%s0000", areaData.getCode().substring(0, 2)));
        for (AreaData province : provinces) {
            String provinceName = province.getName();
            if (provincialCounties.containsKey(provinceName)){
                List<String> provinceAndTowns = provincialCounties.get(provinceName);
                return provinceAndTowns.contains(areaData.getName());
            }
        }
        return false;
    }

    private static AreaData initAreaData(Object code, Object name, int version, int year) {
        return new AreaData(code.toString(), name.toString(), version, year);
    }

}
