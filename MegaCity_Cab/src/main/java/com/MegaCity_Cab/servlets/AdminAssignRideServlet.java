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
        
        int rideId = Integer.parseInt(request.getParameter("rideId"));
        int riderId = Integer.parseInt(request.getParameter("riderId"));
        
        try {
            RideDAO rideDAO = new RideDAO();
            boolean success = rideDAO.assignRider(rideId, riderId);
            
            if(success) {
                response.sendRedirect("adminDashboard?success=Ride assigned");
            } else {
                response.sendRedirect("adminDashboard?error=Assignment failed");
            }
        } catch (Exception e) {
            response.sendRedirect("adminDashboard?error=" + e.getMessage());
        }
    }
}