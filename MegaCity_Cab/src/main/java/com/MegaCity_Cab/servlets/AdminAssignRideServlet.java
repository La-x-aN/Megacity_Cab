package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/admin/assignRide")
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

	        boolean success = new RideDAO().assignRider(rideId, riderId);
	        if (success) {
	            response.sendRedirect("../admin.jsp?success=ride_assigned");
	        } else {
	            response.sendRedirect("../admin.jsp?error=assignment_failed");
	        }
	    } catch (NumberFormatException e) {
	        response.sendRedirect("../admin.jsp?error=invalid_id");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("../admin.jsp?error=server_error");
	    }
	}
    
}