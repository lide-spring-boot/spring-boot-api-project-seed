package com.company.project.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Lides
 * @Date: 2019/9/16 12:12
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String getTest(){
        return "ok";
    }
}
