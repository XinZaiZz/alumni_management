package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.AlumniSearchService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-11 16:56
 */
@Controller
@RequiredArgsConstructor
public class AlumniSearchController {

    private final AlumniSearchService alumniSearchService;

    @GetMapping("/toSearchPage")
    public String toSearchPage() {
        return "alumni_search/search_page";
    }

    @GetMapping("/searchUsersByUserName/{userName}")
    @ResponseBody
    public String searchUsersByUserName(@PathVariable("userName") String userName) {
        List<User> usersByUserName = alumniSearchService.findUsersByUserName(userName);
        if (usersByUserName != null) {
            return Result.success(ResultCode.SUCCESS, usersByUserName);
        }
        return Result.failure(ResultCode.RESULT_EMPTY);
    }
}
