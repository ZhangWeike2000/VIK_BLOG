package com.zwk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwk.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author mr.z
 * @date 2020/7/12 - 11:38
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
     User checkUser(String username);
}
