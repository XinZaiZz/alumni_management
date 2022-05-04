package com.youxin.alumni_management.service;


import com.youxin.alumni_management.pojo.AlumniPhoto;

import java.util.List;

/**
* @author admin
* @description 针对表【alumni_photo】的数据库操作Service
* @createDate 2022-05-03 16:43:09
*/
public interface AlumniPhotoService{

    List<AlumniPhoto> findAllAlumniPhotos();

    int insAlumniPhoto(AlumniPhoto alumniPhoto);

    int insAlumniPhotoNoImage(AlumniPhoto alumniPhoto);

    List<AlumniPhoto> findTop12AlumniPhotos();

    List<AlumniPhoto> findOtherAlumniPhotos();

    AlumniPhoto findAlumniPhotoById(Integer photoId);

}
