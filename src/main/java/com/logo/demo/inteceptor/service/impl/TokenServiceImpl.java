package com.logo.demo.inteceptor.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.logo.demo.inteceptor.service.RedisService;
import com.logo.demo.inteceptor.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * token引用了redis服务，创建token采用随机算法工具类生成随机uuid字符串,
 * 然后放入到redis中(为了防止数据的冗余保留,这里设置过期时间为10000秒,
 * 具体可视业务而定)，如果放入成功，最后返回这个token值。
 * checkToken方法就是从header中获取token到值
 * (如果header中拿不到，就从paramter中获取)，
 * 如若不存在,直接抛出异常。
 * 这个异常信息可以被拦截器捕捉到，然后返回给前端。
 */
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisService redisService;


    @Override
    public String createToken() {
        String str = RandomUtil.randomUUID();
        StringBuilder token = new StringBuilder();
        try {
            token.append("TOKEN_PREFXX").append(str);
            redisService.setEx(token.toString(), token.toString(), 10000L);
            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader("TOKEN_NAME");
        if(StrUtil.isBlank(token)){//header中不存在token
            token = request.getParameter("TOKEN_NAME");
            if(StrUtil.isBlank(token)){//parameter中也不存在token
                throw new Exception("TOKEN 不存在");
            }
        }
        if(!redisService.exists(token)){
            throw new Exception("TOKEN 不存在");
        }
        boolean remove = redisService.remove(token);
        if(!remove){
            throw new Exception("删除TOKEN失败");
        }
        return true;
    }
}
