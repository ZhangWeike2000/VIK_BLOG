package com.zwk.controller.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zwk.pojo.Tag;
import com.zwk.pojo.Type;
import com.zwk.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mr.z
 * @date 2020/7/13 - 16:16
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String list(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> tags= typeService.list(null);
        PageInfo pageInfo=new PageInfo<>(tags);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type()); //给新增页面传递一个object对象
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        //首先通过编号获取到分类
        Type type=typeService.getById(id);
        //回显数据
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes, Model model) {
        //通过分类名获取分类
        QueryWrapper<Type> queryMapper = new QueryWrapper<>();
        queryMapper.eq("name", type.getName());
        List list = typeService.list(queryMapper);
        //判断分类是否存在
        if (list.size() == 0) {
            boolean rs = typeService.save(type);
            attributes.addFlashAttribute("message", "新增成功");
            return "redirect:/admin/types";
        } else {
            model.addAttribute("message", "新增失败，分类名重复");
            return "admin/types-input";
        }
    }
    //编辑分类
    @PostMapping("/types/{id}")
    public String editPost(Type type, RedirectAttributes attributes,Model model) {
        //通过分类名获取分类
        QueryWrapper<Type> queryMapper = new QueryWrapper<>();
        queryMapper.eq("name", type.getName());
        List list = typeService.list(queryMapper);
        //判断分类是否已经存在
        if (list.size() == 0 ) {
            typeService.updateById(type);
            attributes.addFlashAttribute("message", "更新成功");
            return "redirect:/admin/types";
        } else {
            model.addAttribute("message", "更新失败，分类名重复");
            return "admin/types-input";
        }
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes) {
        typeService.removeById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
