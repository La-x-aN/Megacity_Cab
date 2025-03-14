package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.User;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/completeRide")
public class CompleteRideServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException {
		    
		    HttpSession session = request.getSession(false);
		    if (session == null || session.getAttribute("user") == null) {
		        response.sendRedirect(request.getContextPath() + "/login.jsp");
		        return;
		    }

		    User user = (User) session.getAttribute("user");
		    if (user.getRole() != User.Role.RIDER) {
		        response.sendRedirect(request.getContextPath() + "/login.jsp?error=unauthorized");
		        return;
		    }

		    try {
		        int rideId = Integer.parseInt(request.getParameter("rideId"));
		        RideDAO rideDAO = new RideDAO();		       
		        
		        if (rideDAO.completeRide(rideId)) {
		            response.sendRedirect("riderDashboard?success=ride_completed");
		        } else {
		            response.sendRedirect("riderDashboard?error=completion_failed");
		        }
		        
		    } catch (NumberFormatException e) {
		        response.sendRedirect("riderDashboard?error=invalid_ride_id");
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.sendRedirect("riderDashboard?error=database_error");
		    } catch (Exception e) {
		        e.printStackTrace();
		        response.sendRedirect("riderDashboard?error=server_error");
		    }
	}
}