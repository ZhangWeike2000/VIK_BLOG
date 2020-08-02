package com.zwk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zwk.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @author mr.z
 * @date 2020/7/14 - 19:18
 */
public interface BlogService extends IService<Blog> {
    /**
     * 功能描述：根据 编码+名称查询客户信息   查询结果以列表形式展示并支持分页
     *
     * @return
     */
    PageInfo<Blog> selectBlogAll(int pageNum, int pageSize);

    PageInfo<Blog> searchBlog(int pageNum, int pageSize, Blog blog);

    PageInfo<Blog> getIndexBlog(int pageNum, int pageSize);

    List<Blog> getBlogByTypeId(@Param("typeId") Long typeId);

    List<Blog> getBlogByTagId(@Param("tagId") Long tagId);

    List<Blog> getRecommendBlog();

    List<Blog> searchIndexBlog(String query);

    Blog getDetailedBlog(@Param("id") Long id) throws NotFoundException;

    void updateViews(@Param("id") Long id);

     Integer countBlog();
     Map<String,List<Blog>> archiveBlog();

}
