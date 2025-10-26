package com.ngymkana.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ngymkana.pojo.Result;
import com.ngymkana.utils.JwtUtil;
import com.ngymkana.utils.ThreadLocalUtil;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Token validation
        String token = request.getHeader("Authorization");
        // Verify token
        try {
            // Temporarily skip Redis validation for testing
            // TODO: Re-enable Redis validation after Redis is installed
            /*
            // Get token from Redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken==null){
                // Token expired
                throw new RuntimeException();
            }
            */
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // Store user data in ThreadLocal
            ThreadLocalUtil.set(claims);
            // Allow request
            return true;
        } catch (Exception e) {
            // Set 401 status code
            response.setStatus(401);
            // Block request
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Clear ThreadLocal data
        ThreadLocalUtil.remove();
    }
}
