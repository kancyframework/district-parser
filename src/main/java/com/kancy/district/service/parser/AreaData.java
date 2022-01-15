package com.kancy.district.service.parser;

import com.kancy.district.enums.AreaLabelEnum;
import com.kancy.district.enums.AreaTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * AreaData
 *
 * @author huangchengkang
 * @date 2022/1/14 10:59
 */
@Slf4j
@Getter
@Setter
public class AreaData implements Comparable<AreaData>{
    private String code;
    private String name;
    private String type;
    private String parentCode;
    private int version;
    private int year;
    private String label;

    public AreaData(String code, String name, int version, int year) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.year = year;

        if (code.length() !=6){
            log.error("行政区划代码的长度不等于6，code={}，出自{}年的行政区划文件", code, year);
        }

        if (Objects.equals("0000", code.substring(2, 6))){
            this.type = AreaTypeEnum.PROVINCE.name();
            this.parentCode = "";
        } else if (Objects.equals("00", code.substring(4,6))){
            this.type = AreaTypeEnum.CITY.name();
            this.parentCode = String.format("%s0000", code.substring(0,2));
        } else {
            this.type = AreaTypeEnum.AREA.name();
            this.parentCode = String.format("%s00", code.substring(0,4));
        }
    }
    public boolean isArea(){
        return Objects.equals(this.type, AreaTypeEnum.AREA.name());
    }
    public boolean isCity(){
        return Objects.equals(this.type, AreaTypeEnum.CITY.name());
    }
    public boolean isProvince(){
        return Objects.equals(this.type, AreaTypeEnum.PROVINCE.name());
    }
    public boolean isAutonomousRegion(){
        return Objects.equals(this.label, AreaLabelEnum.AUTONOMOUS_REGION.getDescription());
    }
    public boolean isMunicipality(){
        return Objects.equals(this.label, AreaLabelEnum.MUNICIPALITY.getDescription());
    }
    public boolean isSpecialAdministrativeRegion(){
        return Objects.equals(this.label, AreaLabelEnum.SPECIAL_ADMINISTRATIVE_REGION.getDescription());
    }
    public boolean isProvincialCounties(){
        return Objects.equals(this.label, AreaLabelEnum.PROVINCIAL_COUNTIES.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaData areaData = (AreaData) o;
        return code.equals(areaData.code) &&
                name.equals(areaData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return this.code + "="+this.name;
    }

    @Override
    public int compareTo(AreaData o) {
        return o.getVersion() - this.version;
    }
}
