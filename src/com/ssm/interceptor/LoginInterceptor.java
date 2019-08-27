package com.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.pojo.User;

public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, 
    		HttpServletResponse response, Object handler)throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER_SESSION");
		if(user != null) {
			return true;
		}
		//如果没有登录，转发到登录页面
		request.getRequestDispatcher("/toLogin").forward(request, response);
	    return false;
	}
    
	@Override
    public void postHandle(HttpServletRequest request, 
    		HttpServletResponse response, Object handler, 
    			ModelAndView modelAndView) throws Exception {
		
    }
    
	@Override
    public void afterCompletion(HttpServletRequest request, 
    		HttpServletResponse response, Object handler, 
    			Exception ex) throws Exception {		
    }
}