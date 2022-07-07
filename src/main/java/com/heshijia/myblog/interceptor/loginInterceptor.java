package com.heshijia.myblog.interceptor;

import com.heshijia.myblog.utils.SessionInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登录拦截器
 */
public class loginInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle (HttpServletRequest request , HttpServletResponse response , Object handler) throws Exception {
        if (request.getSession().getAttribute(SessionInfo.LOGIN_INFO)==null){
            response.sendRedirect("/toUnauthorized");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle (HttpServletRequest request , HttpServletResponse response , Object handler , ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion (HttpServletRequest request , HttpServletResponse response , Object handler , Exception ex) throws Exception {

    }
}
