package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.Activity;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-04-27 15:53
 */
public interface ActivityService {

    List<Activity> selAllActivity();

    int insActivity(Activity activity);

    Activity selActivityById(Integer activityId);

    List<Activity> selActivityByDepartmentId(Integer departmentId);
}
