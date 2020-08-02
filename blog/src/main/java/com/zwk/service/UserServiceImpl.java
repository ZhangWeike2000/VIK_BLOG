package com.zwk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwk.mapper.UserMapper;
import com.zwk.pojo.User;
import com.zwk.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mr.z
 * @date 2020/7/12 - 11:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(String username) {
        User user=userMapper.checkUser(username);
        return user;
    }
}
