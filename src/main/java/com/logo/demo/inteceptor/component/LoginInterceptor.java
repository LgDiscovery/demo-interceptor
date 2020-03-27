package com.logo.demo.inteceptor.component;

import com.logo.demo.inteceptor.constant.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = request.getServletPath();
        if(path.matches(Const.NO_INTERCEPTOR_PATH)){
            //不需要拦截
            return true;
        }else {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等
            System.out.println("====================================");
            return true;
        }
    }

}
