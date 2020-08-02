package com.zwk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwk.mapper.BlogMapper;
import com.zwk.mapper.UserMapper;
import com.zwk.pojo.*;
import com.zwk.service.BlogService;
import com.zwk.service.TagService;
import com.zwk.service.TypeService;
import com.zwk.test.MyThread;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class BlogApplicationTests {

    @Resource
    private TagService tagService;
    @Resource
    private TypeService typeService;
    @Resource
    private BlogService blogService;

    @Test
    void contextLoads() {
        // 创建线程
        Thread thread1=new Thread(new MyThread());
        Thread thread2=new Thread(new MyThread());
        // 启动线程,线程进入到就绪状态
        thread1.start();
        //thread2.start();
    }

}


