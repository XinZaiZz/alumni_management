package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 活动Mapper
 * @date 2022-04-27 15:25
 */

@Mapper
public interface ActivityMapper {

    //查询所有活动
    List<Activity> selAllActivity();

    //发布活动
    int insActivity(Activity activity);

    Activity selActivityById(Integer activityId);
}
