package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.mapper.MenuMapper;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Description: session拦截、权限控制
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 13:59
 * @Version: 1.0
 **/
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String path = request.getRequestURI();
        if (path.contains("login")) {
            return true;
        } else {
            HttpSession session = request.getSession(false);
            if (null == session) {
                response.sendRedirect(request.getContextPath() + "/login/toLogin");
                return false;
            } else {
                SysUser user = (SysUser) session.getAttribute("user");
                if (null == user) {
                    response.sendRedirect(request.getContextPath() + "/login/toLogin");
                    return false;
                } else {
                    List<Menu> list = menuMapper.findMenusByUserId(user.getId());
                    boolean isauth = false;
                    for (Menu menu : list) {
                        if (path.contains(menu.getMenuKey())) {
                            isauth = true;
                            break;
                        }
                    }
                    if (isauth) {
                        return true;
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login/noauth");
                        return false;
                    }
                }
            }
        }


    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        request.setAttribute("ctx", request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
