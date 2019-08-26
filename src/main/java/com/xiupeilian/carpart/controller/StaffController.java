package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.mapper.SysUserMapper;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @description: 员工管理
 * @author: hejiafei
 * @date: 2019/08/22 10:52
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("/staffList")
    public String StaffList(String username,Integer pageSize,Integer pageNum, HttpServletRequest request) throws IOException {
        pageSize=pageSize==null?10:pageSize;
        pageNum=pageNum==null?1:pageNum;
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(username);
        HttpSession session = request.getSession(false);
           SysUser user = (SysUser) session.getAttribute("user");
           loginVo.setCompanyId(user.getCompanyId());
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser>  staffList =sysUserMapper.findUserByCompanyIdAndUsername(loginVo);
        PageInfo<SysUser> page = new PageInfo<>(staffList);
        request.setAttribute("page",page);
           request.setAttribute("username",username);

       return "index/staffList";
    }
}
