package com.youxin.alumni_management.service.impl;



import com.youxin.alumni_management.mapper.AlumniPhotoMapper;
import com.youxin.alumni_management.pojo.AlumniPhoto;
import com.youxin.alumni_management.service.AlumniPhotoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author admin
* @description 针对表【alumni_photo】的数据库操作Service实现
* @createDate 2022-05-03 16:43:09
*/
@Service
@RequiredArgsConstructor
public class AlumniPhotoServiceImpl implements AlumniPhotoService{

    private final AlumniPhotoMapper alumniPhotoMapper;

    @Override
    public List<AlumniPhoto> findAllAlumniPhotos() {
        return alumniPhotoMapper.findAllAlumniPhotos();
    }

    @Override
    public int insAlumniPhoto(AlumniPhoto alumniPhoto) {
        return alumniPhotoMapper.insAlumniPhoto(alumniPhoto);
    }

    @Override
    public int insAlumniPhotoNoImage(AlumniPhoto alumniPhoto) {
        return alumniPhotoMapper.insAlumniPhotoNoImage(alumniPhoto);
    }

    @Override
    public List<AlumniPhoto> findTop12AlumniPhotos() {
        return alumniPhotoMapper.findTop12AlumniPhotos();
    }

    @Override
    public List<AlumniPhoto> findOtherAlumniPhotos() {
        return alumniPhotoMapper.findOtherAlumniPhotos();
    }

    @Override
    public AlumniPhoto findAlumniPhotoById(Integer photoId) {
        return alumniPhotoMapper.findAlumniPhotoById(photoId);
    }
}
