package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 活动实体类
 * @date 2022-04-27 14:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    //活动id
    private Integer activityId;

    //发布用户id
    private Integer userId;

    //发布用户学院Id
    private Integer departmentId;

    //活动内容
    private String activityContent;

    //活动HTML格式内容
    private String activityHTMLContent;

    //活动标题
    private String activityTitle;

    //活动摘要
    private String activityTabloid;

    //活动发布日期
    private String activityDate;

    //活动文章名称
    private String activityFileName;

    //发布用户信息
    private User user;

    //活动所属学院信息
    private Department department;

}
