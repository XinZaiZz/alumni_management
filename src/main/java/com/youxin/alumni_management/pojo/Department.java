package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 学院信息类
 * @date 2022-04-28 15:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    //学院id
    private Integer departmentId;

    //学院名称
    private String departmentName;
}
