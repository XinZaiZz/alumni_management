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
}
