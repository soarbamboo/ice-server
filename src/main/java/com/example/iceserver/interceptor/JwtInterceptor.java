package com.example.iceserver.interceptor;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;


/**
 * Jwt 拦截器
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
//    @Autowired
//    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        String token = request.getHeader("token");
        if (!(handle instanceof HandlerMethod)) {
            return true;
        }
        log.info("--------token");
        log.info(token);
//        //执行认证
//        if (StrUtil.isBlankIfStr(token)) {
//            System.out.println(1);
//            authError(response, "无token信息,请重新登录！");
//            return false;
//        }
//        //获取token中的userID
//        String userID;
//        try {
//            userID = JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException j) {
//            authError(response, "token 验证失败");
//            return false;
//        }
//        User user = userMapper.selectById(userID);
//        if (user == null) {
//            authError(response, "该用户不存在！");
//            return false;
//        }
//        //用户密码加签验证token
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
//        try {
//            jwtVerifier.verify(token);
//        } catch (JWTVerificationException j) {
//            authError(response, "token 验证失败");
//            return true;
//        }
        return true;
    }


    /**
     * JWT认证错误输出
     *
     * @param response 响应对象
     * @param msg      错误信息
     * @return
     */
    private void authError(HttpServletResponse response, String msg) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("code",401);
        map.put("status",false);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String json = JSONUtil.toJsonStr(map);
        response.setStatus(401);
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.close();
    }

}
