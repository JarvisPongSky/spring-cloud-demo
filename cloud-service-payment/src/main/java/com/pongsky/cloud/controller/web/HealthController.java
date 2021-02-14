package com.pongsky.cloud.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检测模块
 *
 * @author pengsenhao
 * @create 2021-02-14
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * 健康检查
     */
    @RequestMapping("/check")
    public void check() {
    }

}
