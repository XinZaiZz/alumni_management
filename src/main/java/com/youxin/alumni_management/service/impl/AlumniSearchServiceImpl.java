package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.AlumniSearchMapper;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.AlumniSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-12 00:55
 */
@Service
@RequiredArgsConstructor
public class AlumniSearchServiceImpl implements AlumniSearchService {

    private final AlumniSearchMapper alumniSearchMapper;

    @Override
    public List<User> findUsersByUserName(String userName) {
        return alumniSearchMapper.findUsersByUserName(userName);
    }

    @Override
    public List<User> findUsersByNickName(String nickName) {
        return alumniSearchMapper.findUsersByNickName(nickName);
    }

    @Override
    public List<User> findUsersByGrade(Integer grade) {
        return alumniSearchMapper.findUsersByGrade(grade);
    }

    @Override
    public List<User> findUsersByMajor(String major) {
        return alumniSearchMapper.findUsersByMajor(major);
    }

    @Override
    public List<User> findUsersByStudentNumber(Long studentNumber) {
        return alumniSearchMapper.findUsersByStudentNumber(studentNumber);
    }

    @Override
    public List<User> findUsersByBedroom(String bedroomNumber) {
        return alumniSearchMapper.findUsersByBedroom(bedroomNumber);
    }

    @Override
    public List<User> findUsersByInstructorName(String instructorName) {
        return alumniSearchMapper.findUsersByInstructorName(instructorName);
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return alumniSearchMapper.findUsersByEmail(email);
    }

    @Override
    public User findUserDetailByUserId(Integer userId) {
        return alumniSearchMapper.findUserDetailByUserId(userId);
    }
}
