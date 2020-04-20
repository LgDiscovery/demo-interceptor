package com.logo.demo.inteceptor.component;

import cn.hutool.json.JSONUtil;
import com.logo.demo.inteceptor.annotation.AutoIdempotent;
import com.logo.demo.inteceptor.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 主要的功能是拦截扫描到AutoIdempotent到注解到方法,
 * 然后调用tokenService的checkToken()方法校验token是否正确，
 * 如果捕捉到异常就将异常信息渲染成json返回给前端
 */
public class AutoIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //被ApiIdempotment标记的扫描
        AutoIdempotent methodAnnotation = method.getAnnotation(AutoIdempotent.class);
        if (methodAnnotation != null) {
            try {
                //幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
                return tokenService.checkToken(request);
            } catch (Exception e) {
                writeReturnJson(response, JSONUtil.toJsonStr(""));
                e.printStackTrace();
            }
        }
        //必须返回true,否则会被拦截一切请求
        return true;
    }


    /**
     * 返回json值
     *
     * @param response
     * @param json
     * @throws Exception
     */
    private void writeReturnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


}
