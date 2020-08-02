package com.zwk.mapper;

import com.zwk.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/24 - 15:40
 */
@Mapper
public interface CommentMapper {
     void saveComment(Comment comment);
     Comment getCommentByIdAndBlogId(@Param("id")Long id, @Param("blogId") Long blogId);

     List<Comment> getCommentByParentCommentIdAndBlogId(@Param("parentCommentId")Long parentCommentId,@Param("blogId")Long blogId);
     List<Comment> getCommentByTopCommentAndBlogId(@Param("topCommentId")Long topCommentId,@Param("blogId")Long blogId);

}
