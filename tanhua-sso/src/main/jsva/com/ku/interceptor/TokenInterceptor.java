package com.ku.interceptor;

import com.ku.common.JwtConstans;
import com.ku.config.JwtUtils;
import com.ku.bean.SsoUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/19
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private SsoUrl ssoUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 解决跨域问题，及解决请求方式为OPTIONS时问题
         */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        String uri = request.getRequestURI();
        System.out.println("拦截到的地址:"+uri);
        String token = request.getHeader("authorization");
        System.out.println("拦截器获取到的token:"+token);
        String msg = "";
        if (null != token){
            String phone = "";
            try {
                Map<String, Object> result = JwtUtils.getInfoFromToken(token, secret);
                phone = (String)result.get(JwtConstans.JWT_KEY_Phone);
                if (StringUtils.isNotEmpty(phone)){
                    System.out.println("手机号:"+phone+"-验证通过");
                    response.addHeader("authorization",token);
                    return true;
                }
            }catch (Exception e){
                msg = "签名验证失败-"+phone;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print(msg);
                return false;
            }
        }

        System.out.println(ssoUrl.getUrl());
        for (String strUrl : ssoUrl.getUrl()) {
            if (uri.contains(strUrl)){
                System.out.println("放行："+uri);
                return true;
            }
        }
        msg = "请求未携带token";
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(msg);
        return false;
    }
}
