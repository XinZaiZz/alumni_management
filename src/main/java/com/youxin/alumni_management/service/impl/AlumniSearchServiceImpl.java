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
}
