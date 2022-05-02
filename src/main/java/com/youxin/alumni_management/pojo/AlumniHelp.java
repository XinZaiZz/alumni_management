package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友帮扶文章实体类
 * @date 2022-04-29 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumniHelp {

    //帮扶文章id
    private Integer helpId;

    //发布帮扶文章管理员id
    private Integer adminId;

    //帮扶文章所属学院id
    private Integer departmentId;

    //帮扶文章内容
    private String helpContent;

    //帮扶文章html格式内容
    private String helpHTMLContent;

    //帮扶文章标题
    private String helpTitle;

    //帮扶文章摘要
    private String helpTabloid;

    //帮扶文章发布日期
    private String helpDate;

    //帮扶文章附属文件名
    private String helpFileName;

    //帮扶文章封面图名
    private String helpHeadImage;

    //所属学院类
    private Department department;
}
