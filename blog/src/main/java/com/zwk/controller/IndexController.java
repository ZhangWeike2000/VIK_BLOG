package com.zwk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwk.pojo.Blog;
import com.zwk.pojo.Tag;
import com.zwk.pojo.Type;
import com.zwk.service.BlogService;
import com.zwk.service.TagService;
import com.zwk.service.TypeService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/11 - 13:12
 */
@Controller
public class IndexController {

    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;

    @GetMapping("/")
    @Transactional
    public String index(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                        Model model) {
        //获取所有的分类信息
        PageHelper.startPage(1, 6);
        List<Type> types = typeService.list();
        for (Type type : types) {
            //通过分类信息Id--->查询当前分类的博客
            type.setBlogs(blogService.getBlogByTypeId(type.getId()));
        }

        //获取所有标签
        PageHelper.startPage(1, 6);
        List<Tag> tags = tagService.list();
        for (Tag tag : tags) {
            //通過標簽信息ID---->查询当前标签的博客
            tag.setBlogs(blogService.getBlogByTagId(tag.getId()));
        }

        //前台获取推荐博客
        PageHelper.startPage(1, 6);
        List<Blog> recommendBlogs = blogService.getRecommendBlog();

        PageInfo pageInfo = blogService.getIndexBlog(pageNum, pageSize);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", recommendBlogs);
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,
                         @RequestParam String query, Model model) {
        PageHelper.startPage(pageNum,6);
        List<Blog> blogs=blogService.searchIndexBlog(query);
        PageInfo pageInfo=new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) throws NotFoundException {
        Blog blog=blogService.getDetailedBlog(id);
        System.out.println(blog);
        model.addAttribute("blog", blog);
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        PageHelper.startPage(1, 3);
        List<Blog> blogs=  blogService.getRecommendBlog();
        model.addAttribute("newblogs",blogs);
        return "_fragments :: newblogList";
    }
}
