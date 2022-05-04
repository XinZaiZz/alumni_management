package com.youxin.alumni_management.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName alumni_photo
 */
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
    private Date photoDate;

    /**
     * 校友照片名称
     */
    private String photoImageName;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AlumniPhoto other = (AlumniPhoto) that;
        return (this.getPhotoId() == null ? other.getPhotoId() == null : this.getPhotoId().equals(other.getPhotoId()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
            && (this.getPhotoContent() == null ? other.getPhotoContent() == null : this.getPhotoContent().equals(other.getPhotoContent()))
            && (this.getPhotoHTMLContent() == null ? other.getPhotoHTMLContent() == null : this.getPhotoHTMLContent().equals(other.getPhotoHTMLContent()))
            && (this.getPhotoTitle() == null ? other.getPhotoTitle() == null : this.getPhotoTitle().equals(other.getPhotoTitle()))
            && (this.getPhotoDate() == null ? other.getPhotoDate() == null : this.getPhotoDate().equals(other.getPhotoDate()))
            && (this.getPhotoImageName() == null ? other.getPhotoImageName() == null : this.getPhotoImageName().equals(other.getPhotoImageName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPhotoId() == null) ? 0 : getPhotoId().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getPhotoContent() == null) ? 0 : getPhotoContent().hashCode());
        result = prime * result + ((getPhotoHTMLContent() == null) ? 0 : getPhotoHTMLContent().hashCode());
        result = prime * result + ((getPhotoTitle() == null) ? 0 : getPhotoTitle().hashCode());
        result = prime * result + ((getPhotoDate() == null) ? 0 : getPhotoDate().hashCode());
        result = prime * result + ((getPhotoImageName() == null) ? 0 : getPhotoImageName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", photoId=").append(photoId);
        sb.append(", adminId=").append(adminId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", photoContent=").append(photoContent);
        sb.append(", photoHTMLContent=").append(photoHTMLContent);
        sb.append(", photoTitle=").append(photoTitle);
        sb.append(", photoDate=").append(photoDate);
        sb.append(", photoImageName=").append(photoImageName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}