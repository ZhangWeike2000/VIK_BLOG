package com.zwk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lize
 */

@Data
@TableName(value = "t_tag")
public class Tag {

    /**编号*/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**标签名*/
    private String name;

    /**级联关系*/
    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();

}
