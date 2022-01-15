package com.kancy.district.controller;

import com.kancy.district.service.DistrictAreaInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.common.support.IController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务控制器
 *
 * @author kancy
 * @since 2022-01-12 11:25:25
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/districtAreaInfo")
public class DistrictAreaInfoController implements IController {

    private final DistrictAreaInfoService districtAreaInfoService;


}