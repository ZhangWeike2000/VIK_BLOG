package com.zwk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mr.z
 * @date 2020/7/29 - 15:27
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
