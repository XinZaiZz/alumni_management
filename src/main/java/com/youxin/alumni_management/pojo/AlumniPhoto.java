package com.youxin.alumni_management.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName alumni_photo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlumniPhoto implements Serializable {
    /**
     * 校友展示文章Id
     */
    private Integer photoId;

    /**
     * 编写管理员Id
     */
    private Integer adminId;

    /**
     * 所属学院id
     */
    private Integer departmentId;

    /**
     * 校友展示文章内容
     */
    private String photoContent;

    /**
     * 校友展示文章html内容
     */
    private String photoHTMLContent;

    /**
     * 校友文章标题
     */
    private String photoTitle;

    /**
     * 编写日期
     */
    private String photoDate;

    /**
     * 校友照片名称
     */
    private String photoImageName;

    private Admin admin;

    private Department department;

    private static final long serialVersionUID = 1L;

}