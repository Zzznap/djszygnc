package com.example.messionproject.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.messionproject.util.token.*;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(request.getMethod().equals("OPTIONS")){
            response.addHeader("Access-Control-Allow-Headers","*");
            response.addHeader("Access-Control-Allow-Origin","*");
        }
        else{
            System.out.println(token);
        }
        if(token==null) {
            return false;
        }
        try{
            design(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
