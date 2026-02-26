package com.example.bilibili.util.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {

            // 从请求头中获取token，并校验
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
//            System.out.println(token);
            boolean isValid = JwtUtils.verifyToken(token);

            if (!isValid) {
                // 如果token无效，则返回401错误
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 如果token有效，则将用户信息放入request中
            String userInfo = JwtUtils.getUserInfo(token);
            request.setAttribute("userInfo", userInfo);
        }

        filterChain.doFilter(request, response);
    }
}