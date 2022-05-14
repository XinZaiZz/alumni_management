package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.User;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-12 00:54
 */
public interface AlumniSearchService {

    List<User> findUsersByUserName(String userName);

    //根据昵称查询
    List<User> findUsersByNickName(String nickName);

    //根据年级查询
    List<User> findUsersByGrade(Integer grade);

    //根据专业查询
    List<User> findUsersByMajor(String major);

    //根据学号查询
    List<User> findUsersByStudentNumber(Long studentNumber);

    //根据寝室号查询
    List<User> findUsersByBedroom(String bedroomNumber);

    //根据辅导员查询
    List<User> findUsersByInstructorName(String instructorName);

    //根据电子邮箱查询
    List<User> findUsersByEmail(String email);

    //根据用户id查询用户信息
    User findUserDetailByUserId(Integer userId);
}
