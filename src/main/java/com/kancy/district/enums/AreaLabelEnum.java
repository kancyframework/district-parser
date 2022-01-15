package com.kancy.district.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AreaLabel
 *
 * @author huangchengkang
 * @date 2022/1/14 10:54
 */
@Getter
@AllArgsConstructor
public enum AreaLabelEnum {
    PROVINCIAL_COUNTIES("省直辖县"),
    MUNICIPALITY("直辖市"),
    AUTONOMOUS_REGION("自治区"),
    SPECIAL_ADMINISTRATIVE_REGION("特别行政区");

    /**
     * 描述
     */
    private final String description;
}
