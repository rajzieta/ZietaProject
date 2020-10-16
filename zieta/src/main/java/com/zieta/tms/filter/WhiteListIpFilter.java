package com.zieta.tms.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class WhiteListIpFilter extends OncePerRequestFilter {

	@Value("#{'${whitelist.ipaddress}'.split(',')}")
	private List<String> ipAddressList;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		if (matches(request)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}

	private boolean matches(HttpServletRequest request) throws UnknownHostException {

		String inboundIpAddress = request.getHeader("X-FORWARDED-FOR");
		if(inboundIpAddress == null) {
			inboundIpAddress = InetAddress.getLocalHost().getHostAddress();
		}
		System.out.println("In bound ipAddress: " + inboundIpAddress);
		System.out.println("In bound ipAddress: " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("In bound ipAddress: " + InetAddress.getLoopbackAddress().getHostAddress());
		if (ipAddressList.contains(inboundIpAddress)) {
			return true;
		}
		return false;
	}
}
