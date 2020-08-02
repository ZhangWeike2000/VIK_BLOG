package com.zwk.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zwk.mapper.BlogMapper;
import com.zwk.pojo.*;
import com.zwk.service.BlogService;
import com.zwk.service.TagService;
import com.zwk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/12 - 17:53
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //定义一个公共方法，获取分类和标签
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.list());
        model.addAttribute("tags", tagService.list());
    }

    //跳转到博客新增页面，同时将分类和标签显示出来
    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    //获取博客列表
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                        Model model, Blog blog) {
        //获取分类列表
        model.addAttribute("types", typeService.list());
        //获取博客列表
        PageInfo pageInfo = blogService.selectBlogAll(pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs";
    }

    //后台搜索博客列表
    @PostMapping("/blogs/search")
    public String search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
                         Blog blog, Model model) {
        PageInfo pageInfo = blogService.searchBlog(pageNum, pageSize, blog);
        setTypeAndTag(model);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        return "admin/blogs";
    }

    //新增,编辑博客
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        //设置用户编号
        blog.setUserId(((User) session.getAttribute("user")).getId());
        //设置分类编号
        blog.setType(typeService.getById(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if (blog.getId() == null) {
            //新增
            blogService.save(blog);
            //保存博客后才能获取自增的id
            Long id = blog.getId();
            //
            BlogAndTag blogAndTag=null;
            for (Tag tag : blog.getTags()) {
                //新增时无法获取自增的id,在mybatis里修改
                blogAndTag = new BlogAndTag(tag.getId(), id);
                blogMapper.saveBlogAndTag(blogAndTag);
            }
            attributes.addFlashAttribute("message", "操作成功");
        } else {
            //更新
            blogService.updateById(blog);
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getById(id);
        blog.setType(typeService.getById(id));
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.removeById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

}
