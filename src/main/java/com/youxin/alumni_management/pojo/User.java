package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 用户实体类
 * @date 2022-02-08 21:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //用户id
    private Integer userId;

    //用户名
    private String userName;

    //用户密码
    private String password;

    //用户所属学院id
    private Integer departmentId;

    //用户昵称
    private String nickName;

    //用户性别
    private String gender;

    //出生日期
    private String birth;

    //用户年龄
    private Integer age;

    //用户学号
    private Long studentNumber;

    //专业
    private String major;

    //年级
    private Integer grade;

    //寝室号
    private String bedroomNumber;

    //职业
    private String occupation;

    //职业类别
    private String occupationGroup;

    //地区
    private String region;

    //公司名称
    private String company;

    //电话号码
    private Long number;

    //电子邮箱
    private String email;

    //个人简介
    private String personalProfile;

    //辅导员姓名
    private String instructorName;

    //学院信息
    private Department department;

}
