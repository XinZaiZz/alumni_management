package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.AlumniSearchService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final JavaMailSender javaMailSender;

    @GetMapping("/toSearchPage")
    public String toSearchPage() {
        return "alumni_search/search_page";
    }

    @GetMapping("/searchUsersByUserName/{selectValue}/{value}")
    @ResponseBody
    public String searchUsersByUserName(@PathVariable("selectValue")String selectValue,@PathVariable("value") String inputValue) {
        List<User> users = null;
        switch (selectValue){
            case "userName" :
                users = alumniSearchService.findUsersByUserName(inputValue);break;
            case "nickName" :
                users = alumniSearchService.findUsersByNickName(inputValue);break;
            case "grade" :
                users = alumniSearchService.findUsersByGrade(Integer.parseInt(inputValue));break;
            case "studentNumber" :
                users = alumniSearchService.findUsersByStudentNumber(Long.parseLong(inputValue));break;
            case "major" :
                users = alumniSearchService.findUsersByMajor(inputValue);break;
            case "bedroomNumber" :
                users = alumniSearchService.findUsersByBedroom(inputValue);break;
            case "instructorName" :
                users = alumniSearchService.findUsersByInstructorName(inputValue);break;
            case "email" :
                users = alumniSearchService.findUsersByEmail(inputValue);break;
        }

        if (users != null) {
            return Result.success(ResultCode.SUCCESS, users);
        }
        return Result.failure(ResultCode.RESULT_EMPTY);
    }

    @GetMapping("/search_detail/{userId}")
    public String searchUserDetailByUserId(@PathVariable("userId")Integer userId, HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/unAuthorized";
        }

        //查询用户详细信息
        User userDetails = alumniSearchService.findUserDetailByUserId(userId);
        model.addAttribute("userDetails", userDetails);
        return "alumni_search/user_details";
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Origin", "*");

        //复杂邮件发送
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //组装
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        mimeMessageHelper.setSubject(request.getParameter("title"));
        mimeMessageHelper.setText(request.getParameter("message") +  "<hr/><b>想要回复此信息请回复到:" + request.getParameter("email") + "</b>", true);
        mimeMessageHelper.setFrom("3228105317@qq.com");
        String sendToEmail = request.getParameter("sendToEmail");
        if (sendToEmail != null) {
            //发送邮箱地址
            mimeMessageHelper.setTo(sendToEmail);
            try {
                javaMailSender.send(mimeMessage);
                //发送成功
                return Result.success(ResultCode.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                //错误返回失败
                return Result.failure(ResultCode.FAIL);
            }
        }
        else {
            //错误返回失败
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }


    }
}
