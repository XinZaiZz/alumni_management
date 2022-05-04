package com.youxin.alumni_management.mapper;


import com.youxin.alumni_management.pojo.AlumniPhoto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author admin
* @description 针对表【alumni_photo】的数据库操作Mapper
* @createDate 2022-05-03 16:43:09
* @Entity com.youxin.alumni_management.pojo.AlumniPhoto
*/

@Mapper
public interface AlumniPhotoMapper{

    List<AlumniPhoto> findTop12AlumniPhotos();

    //查找12到末尾的所有校友风采文章
    List<AlumniPhoto> findOtherAlumniPhotos();

    List<AlumniPhoto> findAllAlumniPhotos();

    //发布校友展示文章
    int insAlumniPhoto(AlumniPhoto alumniPhoto);

    //发布校友展示文章,没有文件名
    int insAlumniPhotoNoImage(AlumniPhoto alumniPhoto);

    AlumniPhoto findAlumniPhotoById(Integer photoId);

}
