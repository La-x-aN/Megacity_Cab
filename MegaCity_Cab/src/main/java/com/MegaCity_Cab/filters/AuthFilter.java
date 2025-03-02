package com.MegaCity_Cab.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.MegaCity_Cab.model.User;
import com.MegaCity_Cab.model.User.Role;

import java.io.IOException;

@WebFilter({
		"/user.jsp", 
	    "/userDashboard", 
	    "/bookRide", 
	    "/deleteRide",
	    "/logout",
	    "/riderDashboard",
	    "/completeRide"
			})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        
        if (path.equals("/rider.jsp")) {
            HttpSession session = request.getSession(false);
            if (session == null || ((User) session.getAttribute("user")).getRole() != Role.RIDER) {
                ((HttpServletResponse) res).sendRedirect("login.jsp");
                return;
            }            
        }else if (path.equals("/user.jsp")) {
                HttpSession session = request.getSession(false);
                if (session == null || ((User) session.getAttribute("user")).getRole() != Role.USER) {
                    ((HttpServletResponse) res).sendRedirect("login.jsp");
                    return;
                }
        } else if (path.equals("/admin.jsp")) {
            HttpSession session = request.getSession(false);
            if (session == null || ((User) session.getAttribute("user")).getRole() != Role.ADMIN) {
                ((HttpServletResponse) res).sendRedirect("login.jsp");
                return;
            }
        }

        chain.doFilter(req, res);
    }

}