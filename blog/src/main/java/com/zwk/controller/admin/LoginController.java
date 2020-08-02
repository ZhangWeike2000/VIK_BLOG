package com.zwk.controller.admin;

import com.zwk.pojo.User;
import com.zwk.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.jar.Attributes;

/**
 * @author mr.z
 * @date 2020/7/12 - 11:48
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    /*服务类*/
    @Autowired
    UserService userService;

    //跳转到登录页面，不写value默认是类上面的地址
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (session.getAttribute("user") != null) {
            return "admin/index";
        } else {
            return "redirect:/admin";
        }
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token); //执行登录的方法，如果没有异常说明ok
            return "admin/index";
        } catch (UnknownAccountException e) {//用户名不存在
            attributes.addFlashAttribute("message", "用户名错误");
            return "redirect:/admin";
        } catch (IncorrectCredentialsException e) {//密码不存在
            attributes.addFlashAttribute("message", "密码错误");
            return "redirect:/admin";
        }
    }

    /***
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "admin/login";
    }
}
