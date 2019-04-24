package com.javaex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.javaex.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public Object resolveArgument(MethodParameter parameter, 
								  ModelAndViewContainer mavContainer, 
								  NativeWebRequest webRequest, 
								  WebDataBinderFactory binderFactory) 
										throws Exception {
		if (this.supportsParameter(parameter) == false) {
		//쓸 수 있는 형식이 아니면 > @AuthUser -> UserVo (x)
			return WebArgumentResolver.UNRESOLVED;
		}
		//@AuthUser -> UserVo (o)
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = httpServletRequest.getSession();
		if(session == null) {
			return WebArgumentResolver.UNRESOLVED;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		return authUser;
	}
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		
		//@AuthUser 안들어 있으
		if(authUser == null) {
			return false;
		}
		// 파라미터 타입이 userVo 모양이 아니면 
		if(parameter.getParameterType().equals(UserVo.class)==false) {
			return false;
		}
		return true;
	}
}