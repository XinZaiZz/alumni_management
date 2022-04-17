package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author youxin
 * @program alumni_management
 * @description 评论实体类
 * @date 2022-04-16 17:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    //论坛文章发布者id，从文章类中获取
    private Integer forumId;

    //评论者Id
    private Integer commentUserId;

    //评论者昵称
    private String commentNickName;

    //评论内容
    private String commentContent;

    //评论头像路径
    private String commentUserImgUri;

    //评论日期
    private String commentDate;
}
