package com.zwk.controller;


import com.zwk.pojo.Comment;
import com.zwk.pojo.User;
import com.zwk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/24 - 15:14
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model) {
        List<Comment> comments=commentService.getCommentByBlogId(blogId);
        System.out.println(avatar);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        //获取博客编号
        Long blogId = comment.getBlog().getId();
        comment.setBlogId(blogId);

        //获取被回复的昵称
        String replyName = comment.getReplyName();
        if (replyName.equals("")) {
            comment.setReplyName(null);
        }

        //判断用户是否登录
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //登录，则用用户设置的头像
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            //是游客，使用默认的头像
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}