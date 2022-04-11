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
}
