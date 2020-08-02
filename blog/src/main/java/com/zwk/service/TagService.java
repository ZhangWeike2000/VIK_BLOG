package com.zwk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwk.pojo.Tag;

import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/14 - 18:42
 */
public interface TagService  extends IService<Tag> {
    //从字符串中获取tag集合
    List<Tag> getTagByString(String text);
}
