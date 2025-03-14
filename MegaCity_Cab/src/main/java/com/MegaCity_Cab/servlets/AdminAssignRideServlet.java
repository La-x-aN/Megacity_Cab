package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AdminAssignRideServlet")
public class AdminAssignRideServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	    
	        try {
	        	int rideId = Integer.parseInt(request.getParameter("rideId"));
	            int riderId = Integer.parseInt(request.getParameter("riderId"));
	            
	            RideDAO rideDAO = new RideDAO();
	            boolean success = rideDAO.assignRider(rideId, riderId);
	            
	            if(success) {
	                response.sendRedirect("adminDashboard?success=ride_assigned");
	            } else {
	                response.sendRedirect("adminDashboard?error=assignment_failed&rideId=" + rideId);
	            }
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid ID format: " + e.getMessage());
	            response.sendRedirect("adminDashboard?error=invalid_id");
	        } catch (Exception e) {
	            System.err.println("Assignment error: " + e.getMessage());
	            e.printStackTrace();
	            response.sendRedirect("adminDashboard?error=server_error");
	        }
	    }
}