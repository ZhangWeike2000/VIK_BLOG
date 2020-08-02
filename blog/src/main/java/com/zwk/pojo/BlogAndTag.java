package com.zwk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mr.z
 * @date 2020/7/28 - 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTag {
    private Long tagId;
    private Long blogId;
}
