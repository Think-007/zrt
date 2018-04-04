package com.think.zrt.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class UrlFilter
 */
//@WebFilter(filterName = "/UrlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {

	private List<String> urlList = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public UrlFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		String url = httpRequest.getRequestURI();

		System.out.println(url);

		if (urlList.contains(url)) {
			if (session.getAttribute("user") == null) {
				RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/relogin");
				requestDispatcher.forward(request, response);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		urlList.add("/product_list.html");
		urlList.add("/product_add.html");
		urlList.add("/product_edit.html");
		urlList.add("/auth");
	}

}
