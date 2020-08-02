package com.zwk.service;

import com.zwk.pojo.Comment;

import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/24 - 15:47
 */
public interface CommentService  {
    //新增评论
    public void saveComment(Comment comment);
    //根据博客Id获取评论
    public List<Comment> getCommentByBlogId(Long blogId);
}
