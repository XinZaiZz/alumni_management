package com.youxin.alumni_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youxin.alumni_management.mapper.BackManagementMapper;
import com.youxin.alumni_management.pojo.Comment;
import com.youxin.alumni_management.utils.DateUtil;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RequiredArgsConstructor
class AlumniManagementApplicationTests {
    @Autowired
    DateUtil dateUtil;

    private final BackManagementMapper backManagementMapper;

    @Test
    @SneakyThrows
    void contextLoads() {

        ArrayList<Object> objects = new ArrayList<>();
        Result result = new Result();
        objects.add(new Comment(1, 21, "as", "sc", "as", "da"));
        objects.add(new Comment(1, 21, "as", "sc", "as", "da"));
        objects.add(new Comment(1, 21, "as", "sc", "as", "da"));

//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(Result.success(ResultCode.SUCCESS, objects));
        System.out.println(Result.success(ResultCode.SUCCESS, objects));

    }

    @Test
    @SneakyThrows
    void test01() {
        List<Map<String, Object>> maps = backManagementMapper.countByOccupationGroup(1001);
        System.out.println(maps);
    }

}
