package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 论坛文章实体类
 * @date 2022-04-05 11:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forum {

    //论坛id
    private Integer forumId;

    //编写者id
    private Integer userId;

    //文章所属学院id
    private Integer departmentId;

    //文章内容
    private String forumContent;

    //文章的HTML格式内容
    private String forumHTMLContent;

    //文章标题
    private String forumTitle;

    //文章摘要
    private String forumTabloid;

    //发布时间
    private String forumDate;

    //关联的用户信息
    private User user;
}
