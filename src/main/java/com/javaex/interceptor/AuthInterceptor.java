package com.javaex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javaex.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("+++++++++++++++++++++++++");
		System.out.println("AuthInterceptor preHandle");
		// 1. handler 종류 확인
		// controller 에 있는 메소드 체크 
		if(handler instanceof HandlerMethod == false) {
			System.out.println("coutroller 에 있는 메소드가 아님");
			//coutroller 에 있는 메소드가 아니면 
			return true;
		}
		
		// 2.@Auth 가 있는지?
		Auth auth = ((HandlerMethod)handler).getMethodAnnotation(Auth.class);
		if(auth == null) {
			System.out.println("Nothing @Auth");
			return true;
		}
		// 3.@Auth 가 있으면 session 있는지 체크
		HttpSession session = request.getSession();
		if(session == null ) { // 없으면 loginform 으로 이동 
			System.out.println("@Auth 있음 , session 없음 ");
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		// 4.@Auth 가 있고 session 이 있고 session에  authUser 정보가 있는지?
		UserVo authUser =(UserVo)session.getAttribute("authUser");
		if(authUser == null) {// 없으면 로그인폼 리다이렉트
			System.out.println("@Auth 있음 , session 있음  ,session 안에 authUser 없음 ");
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		// 5.모든 조건을 만족한 사용자는 요청한 곳으로 보낸다.
		System.out.println("+++++++++++++++++++++++++");
		System.out.println("@Auth 있음 , session 있음  ,session 안에 authUser 있음 ");
		return true;
	}
}