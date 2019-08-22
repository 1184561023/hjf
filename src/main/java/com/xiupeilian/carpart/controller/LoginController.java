package com.xiupeilian.carpart.controller;


import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @description: denglu
 * @author: hejiafei
 * @date: 2019/08/21 19:51
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * @Description: 去往登录页面
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
     **/
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login/login";
    }

    @RequestMapping("/login")
    public void login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //判断验证码是否正确
        String code=(String)request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
        if(vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            //验证码正确

            //将密码加密之后再判断
            vo.setPassword(SHA1Util.encode(vo.getPassword()));
            SysUser user= userService.findUserByLoginNameAndPassword(vo);
            if(null==user){
                response.getWriter().write("2");
            }else{
                //登录成功
                request.getSession().setAttribute("user",user);
                response.getWriter().write("3");
            }
        }else{
            //验证码错误
            response.getWriter().write("1");
        }
    }

    @RequestMapping("/noauth")
    public String noauth(){
        return "exception/noauth";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "login/forgetPassword";
    }

    @RequestMapping("/getPassword")
    public void getPassword(LoginVo vo,HttpServletResponse response) throws IOException {
     SysUser user = userService.findUserByLoginNameAndEmail(vo);
     if (null==user){
         response.getWriter().write("1");
     }else {
         String password = new Random().nextInt(899999)+100000+"";

         user.setPassword(SHA1Util.encode(password));
         userService.updateUser(user);

         SimpleMailMessage mailMessage = new SimpleMailMessage();
         mailMessage.setFrom("15239022217@sina.cn");
         mailMessage.setTo(user.getEmail());
         mailMessage.setSubject("修配连汽配市场密码找回功能:");
         mailMessage.setText("您的新密码是"+password);
         MailTask mailTask=new MailTask(mailSender,mailMessage);
         //让线程池去执行该任务就可以了
         executor.execute(mailTask);

         response.getWriter().write("2");

     }

    }

}
