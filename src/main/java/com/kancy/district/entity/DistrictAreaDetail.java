package com.kancy.district.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (district_area_detail)实体类
 *
 * @author kancy
 * @since 2022-01-12 14:16:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("district_area_detail")
public class DistrictAreaDetail extends Model<DistrictAreaDetail> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 县区代码
     */
    private String areaCode;
    /**
     * 县区名称
     */
    private String areaName;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 省代码
     */
    private String provinceCode;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 是否属于直辖市（1：是，0：否）
     */
    private Boolean isMunicipality;
    /**
     * 是否属于省直辖县（1：是，0：否）
     */
    private Boolean isProvincialCounties;
    /**
     * 是否自治区（1：是，0：否）
     */
    private Boolean isAutonomousRegion;
    /**
     * 是否属于特别行政区（1：是，0：否）
     */
    private Boolean isSpecialAdministrativeRegion;
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