package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

     SysUser findUserByLoginName(String loginName);

    SysUser findUserByLoginNameAndPassword(LoginVo vo);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    List<SysUser> findUserByCompanyIdAndUsername(LoginVo loginVo);

    SysUser findUserByPhone(String telnum);
    SysUser findUserByEmail(String email);
}