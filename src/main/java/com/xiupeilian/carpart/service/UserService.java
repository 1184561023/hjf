package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;

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
}