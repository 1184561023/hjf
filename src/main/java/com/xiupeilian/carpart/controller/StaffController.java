package com.xiupeilian.carpart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 员工管理
 * @author: hejiafei
 * @date: 2019/08/22 10:52
 */
@Controller
@RequestMapping("/staff")
public class StaffController {


    @RequestMapping("/staffList")
    public void StaffList(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("访问成功");
    }
}
