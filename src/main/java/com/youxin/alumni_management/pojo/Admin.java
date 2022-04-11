package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 管理员实体类
 * @date 2022-02-08 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    //管理员id
    private Integer adminId;

    //管理员名
    private String adminName;

    //管理员密码
    private String password;

    //管理员昵称
    private String nickName;

    //管理员性别
    private String gender;

    //管理员所属学院id
    private Integer departmentId;

    //管理员权限
    private String perms;
}
