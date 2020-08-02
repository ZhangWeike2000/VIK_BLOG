package com.zwk.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lize
 */
@Data
@TableName(value = "t_user")
public class User {

    /**编号*/
    @TableId
    private Long id;

    /**昵称*/
    private String nickname;

    /**用户名*/
    private String username;

    /**密码*/
    private String password;

    /**邮箱地址*/
    private String email;

    /**头像地址*/
    private String avatar;

    /**用户类型*/
    private Integer type;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;


    /**级联关系*/
    private List<Blog> blogs = new ArrayList<>();

}
