package com.kancy.district.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (district_area_info)实体类
 *
 * @author kancy
 * @since 2022-01-14 11:23:12
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DistrictAreaInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 代码类型（PROVINCE、CITY、AREA）
     */
    private String codeType;
    /**
     * 省/城市/区域代码
     */
    private String code;
    /**
     * 省/城市/区域名称
     */
    private String address;
    /**
     * 父节点代码
     */
    private String parentCode;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 标签
     */
    private String label;
    /**
     * 是否删除（1：是，0：否）
     */
    private Integer isDeleted;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;

}