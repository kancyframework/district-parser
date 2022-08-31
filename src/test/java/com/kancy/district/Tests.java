package com.kancy.district;

import com.kancy.district.service.parser.AreaData;
import com.kancy.district.service.parser.DistrictFileParser;
import lombok.SneakyThrows;
import net.dreamlu.mica.core.utils.FileUtil;

import java.io.File;
import java.util.*;

/**
 * Tests
 *
 * @author huangchengkang
 * @date 2022/1/15 22:48
 */
public class Tests {

    @SneakyThrows
    public static void main(String[] args) {
        List<Map<String, Object>> provinces = new ArrayList<>();
        Map<String, List<AreaData>> stringListMap = DistrictFileParser.parseRangeDistrictFiles(2000, 2022);
        Map<String, AreaData> areaDataMap = new HashMap<>();
        stringListMap.forEach((k,v)->{
            areaDataMap.put(k, v.get(0));
        });
        System.out.println(stringListMap);
    }
}
