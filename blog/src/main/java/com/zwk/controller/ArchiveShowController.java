package com.zwk.controller;

import com.zwk.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mr.z
 * @date 2020/7/29 - 11:08
 */
@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;


    @GetMapping("/archives")
   public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
   }

}
