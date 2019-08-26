package com.xiupeilian.carpart.controller;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    CityService cityService;

    @Autowired
    BrandService brandService;

    @Autowired
    RedisTemplate jedis;

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
    @RequestMapping("/toRegister")
    public String  toRegister(HttpServletRequest request){

        //初始化数据  汽车品牌、配件种类、精品种类
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partsList=brandService.findPartsAll();
        List<Prime> primelist=brandService.findPrimeAll();
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("primeList",primelist);

        return  "login/register";

    }

    @RequestMapping("/checkLoginName")

    public void checkLoginName(String loginName,HttpServletResponse response) throws IOException {
        SysUser user = userService.findUserByLoginName(loginName);
        if (null ==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("/checkPhone")
    public void checkPhone(String telnum,HttpServletResponse response) throws IOException {
        SysUser user = userService.findUserByPhone(telnum);
        if (null ==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }

    @RequestMapping("/checkEmail")
    public void checkEmail(String email,HttpServletResponse response) throws IOException {
        SysUser user = userService.findUserByEmail(email);
        if (null ==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }

    @RequestMapping("/checkCompanyname")
    public void checkCompanyname(String companyname,HttpServletResponse response) throws IOException {

        Company company=userService.findCompanyByName(companyname);
        if(null==company){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("2");
        }
    }

    @RequestMapping("/getCity")
    public @ResponseBody
    List<City> getCity(Integer parentId){
            parentId = parentId==null?SysConstant.CITY_CHINA_ID:parentId;
            List<City> cityList = cityService.findCitysByParentId(parentId);
        return cityList;
    }
    @RequestMapping("/register")
    public String register(RegisterVo vo){
        userService.addRegsiter(vo);
        return "redirect:toLogin";
    }
    //获取验证码
    @RequestMapping("/smsControllter")
    public void smsControllter(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIkliA7jWPfBxU", "UKIXqBm6K1Nk8wJNe1BBrMmi6QIgKn");
        IAcsClient client = new DefaultAcsClient(profile);

        String  yzm =  new Random().nextInt(899999)+100000+"";
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "\u51ef\u6587\u6c7d\u4fee");
        request.putQueryParameter("TemplateCode", "SMS_172888591");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+yzm+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
                jedis.boundValueOps(phone).set(yzm);

                jedis.expire(phone,1, TimeUnit.MINUTES);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/smsQuery")
    public void smsQuery(String code,String phone,HttpServletResponse response) throws IOException {
        if (!jedis.hasKey(phone)){
            response.getWriter().write("1");
        }else {
          if (code.equals(jedis.boundValueOps(phone).get())){
              response.getWriter().write("2");
          }else {
              response.getWriter().write("3");
          }

        }
    }
}
