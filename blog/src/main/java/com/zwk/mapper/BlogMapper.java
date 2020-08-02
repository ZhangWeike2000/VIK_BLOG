package com.zwk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwk.pojo.Blog;
import com.zwk.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/14 - 19:22
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     *
     */ int saveBlogAndTag(BlogAndTag blogAndTag);


    /**
     * 后台获取博客列表
     * @return
     */
    List<Blog> selectBlogAll();

    /**
     * 后台搜索
     * @param blog
     * @return
     */
     List<Blog> searchBlog(Blog blog);

    /**
     * 前台获取博客
     * @return
     */
    List<Blog> getIndexBlog();

    /**
     * 查询当前分类的所有博客
     * @param typeId
     * @return
     */
     List<Blog> getBlogByTypeId(@Param("typeId")Long typeId);

    /**
     * 查询当前标签的所有博客
     * @param tagId
     * @return
     */
     List<Blog> getBlogByTagId(@Param("tagId")Long tagId);

    /***
     * 查询最新的推荐博客
     * @return
     */
     List<Blog> getRecommendBlog();

    /***
     * 前端全局搜索
     * @param query
     * @return
     */
     List<Blog> searchIndexBlog(@Param("query")String query);

    /**
     * 获取博客详情
     * @param id
     * @return
     */
     Blog getDetailedBlog(@Param("id") Long id);

    /***
     * 更新浏览次数
     * @param id
     */
    void updateViews(@Param("id")Long id);

    /***
     * 博客归档
     * @return
     */
     Integer countBlog();
     List<String> findGroupYear();
     List<Blog> findByYear(@Param("year")String year);

}
