package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;

import java.util.List;

/**
 * @description: 用户
 * @author: hejiafei
 * @date: 2019/08/21 20:03
 */
public interface UserService {
    public SysUser findUserByLoginNameAndPassword(LoginVo vo);

    List<Menu> findMenusById(int id);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);
    List<SysUser> findUserByCompanyIdAndUsername(LoginVo loginVo);

    SysUser findUserByLoginName(String loginName);

    SysUser findUserByPhone(String telnum);

    SysUser findUserByEmail(String email);

    Company findCompanyByName(String companyname);

    void addRegsiter(RegisterVo vo);
}