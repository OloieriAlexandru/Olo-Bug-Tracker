package com.olo.olobugtracker.middlewares;

import com.olo.olobugtracker.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AppRequestInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null) {
            String jwtToken = requestTokenHeader.substring(JwtTokenUtil.tokenHeaderPrefix.length());
            Long userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
            request.setAttribute("userId", userId);
        }

        return true;
    }
}
