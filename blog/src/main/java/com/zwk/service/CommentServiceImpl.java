package com.zwk.service;

import com.zwk.mapper.CommentMapper;
import com.zwk.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/24 - 15:49
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        //获取父亲评论
        Long parentCommentId = comment.getParentCommentId();
        if(parentCommentId==-1){
            comment.setParentCommentId(parentCommentId);
            comment.setTopCommentId((long) -1);
        }else{
            comment.setParentCommentId(parentCommentId);
            //查询上一级评论或回复的情况
            Comment comment1=commentMapper.getCommentByIdAndBlogId(parentCommentId,comment.getBlogId());
            if(comment1.getTopCommentId()==-1){
                //表明它的上一级是最高级评论
                comment.setTopCommentId(comment1.getId());
            }
            else{
                comment.setTopCommentId(comment1.getTopCommentId());
            }
        }

        comment.setCreateTime(new Date());
        commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        //获取该博客下的所有的最高级评论
        List<Comment> topComments=commentMapper.getCommentByParentCommentIdAndBlogId((long)-1,blogId);
        for(Comment topComment:topComments){
            Long topCommentId=topComment.getId();
            topComment.setChildComments(commentMapper.getCommentByTopCommentAndBlogId(topCommentId,blogId));
        }
        return topComments;
    }
}
