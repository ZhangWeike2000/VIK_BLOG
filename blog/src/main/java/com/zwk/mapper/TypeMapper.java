package com.zwk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwk.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author mr.z
 * @date 2020/7/13 - 15:25
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

}
