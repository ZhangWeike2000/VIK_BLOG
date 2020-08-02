package com.zwk.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwk.mapper.BlogMapper;
import com.zwk.pojo.Blog;
import com.zwk.util.MarkdownUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mr.z
 * @date 2020/7/14 - 19:24
 */
@Service
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    TagService tagService;

    @Override
    public PageInfo<Blog> selectBlogAll(int pageNum, int pageSize) {
        //1.PageHelper.startPage设置当前页和每页显示的行数
        PageHelper.startPage(pageNum, pageSize);
        //2.填充自己的sql查询逻辑
        List<Blog> blogs = blogMapper.selectBlogAll();
        //3.PageHelper收尾，将返回结果信息进行分页处理
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    @Override
    public PageInfo<Blog> searchBlog(int pageNum, int pageSize, Blog blog) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.searchBlog(blog);
        PageInfo pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> getIndexBlog(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.getIndexBlog();
        PageInfo pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }

    @Override
    public List<Blog> getBlogByTypeId(Long typeId) {
        return blogMapper.getBlogByTypeId(typeId);
    }

    @Override
    public List<Blog> getBlogByTagId(Long tagId) {
        return blogMapper.getBlogByTagId(tagId);
    }

    @Override
    public List<Blog> getRecommendBlog() {
        return blogMapper.getRecommendBlog();
    }

    @Override
    public List<Blog> searchIndexBlog(String query) {
        return blogMapper.searchIndexBlog(query);
    }

    @Override
    public Blog getDetailedBlog(Long id) throws NotFoundException {
        Blog blog= blogMapper.getDetailedBlog(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }

        List tags = new ArrayList();
        if(blog.getTagIds()=="" || blog.getTagIds().equals("")){

        }else{
            String[] tagIds=blog.getTagIds().split(",");
            for(String tagId:tagIds){
                Long tagid=Long.parseLong(tagId);
                tags.add(tagService.getById(tagid));
            }
            blog.setTags(tags);
        }

        String content=blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新浏览次数
        blogMapper.updateViews(id);
        return blog;
    }

    @Override
    public void updateViews(Long id) {
        blogMapper.updateViews(id);
    }

    @Override
    public Integer countBlog() {
        return blogMapper.countBlog();
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogMapper.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String year:years){
            map.put(year,blogMapper.findByYear(year));
        }
        return map;
    }
}
