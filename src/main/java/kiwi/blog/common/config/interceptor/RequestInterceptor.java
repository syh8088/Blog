package kiwi.blog.common.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// error URI에 대한 IP 체크 추가
		if (request.getRequestURI().indexOf("error") > 0){
			String remoteHost = "";
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
				remoteHost = request.getRemoteHost();
			}
			log.info("ApiRequestInterceptor request: {}, ipAddress: {}, remoteHost: {}", request.getRequestURI(), ipAddress, remoteHost);
		} else {
			log.info("ApiRequestInterceptor request: {}", request.getRequestURI());
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

	}
}


