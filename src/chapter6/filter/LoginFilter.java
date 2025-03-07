package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

	@WebFilter(urlPatterns = {"/edit", "/setting"})
public class LoginFilter implements Filter {

		@Override
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {

			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse res = (HttpServletResponse)response;
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("loginUser");
			List<String> errorMessages = new ArrayList<String>();

			if (user != null) {
				chain.doFilter(request, response); // サーブレットを実行
			} else {
				errorMessages.add("ログインをしてください");
    	    	session.setAttribute("errorMessages", errorMessages);
				res.sendRedirect("./login");
			}
		}

		@Override
		public void init(FilterConfig config) {

		}

		@Override
		public void destroy() {
		}

	}

