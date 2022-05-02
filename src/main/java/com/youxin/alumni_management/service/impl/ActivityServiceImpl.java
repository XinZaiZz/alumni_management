package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.ActivityMapper;
import com.youxin.alumni_management.pojo.Activity;
import com.youxin.alumni_management.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 活动服务实现类
 * @date 2022-04-27 15:54
 */

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;


    @Override
    public List<Activity> selAllActivity() {
        return activityMapper.selAllActivity();
    }

    @Override
    public int insActivity(Activity activity) {
        return activityMapper.insActivity(activity);
    }

    @Override
    public Activity selActivityById(Integer activityId) {
        return activityMapper.selActivityById(activityId);
    }
}
