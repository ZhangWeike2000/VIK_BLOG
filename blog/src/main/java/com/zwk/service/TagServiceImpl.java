package com.zwk.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwk.mapper.TagMapper;
import com.zwk.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/14 - 18:43
 */
@Service
@Transactional
public class TagServiceImpl extends ServiceImpl<TagMapper,Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<Tag> getTagByString(String text) {
        //从tagIds字符串中获取id，根据id获取tag集合
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long long1 : longs) {
            tags.add(tagMapper.selectById(long1));
        }
        return tags;
    }
    private List<Long> convertToList(String ids) {
        //把前端的tagIds字符串转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
