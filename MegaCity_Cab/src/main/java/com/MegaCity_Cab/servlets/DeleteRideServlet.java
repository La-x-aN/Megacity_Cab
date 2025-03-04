package com.MegaCity_Cab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;

@WebServlet("/deleteRide")
public class DeleteRideServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int rideId = Integer.parseInt(request.getParameter("rideId"));
        RideDAO rideDAO = new RideDAO();
        Ride ride;
		try {
			ride = rideDAO.getRideById(rideId);
			
			if (ride != null && "requested".equals(ride.getStatus())) {
	            try {
	                if (rideDAO.deleteRide(rideId)) {
	                    response.sendRedirect("user.jsp?success=ride_deleted");
	                } else {
	                    response.sendRedirect("user.jsp?error=delete_failed");
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.sendRedirect("user.jsp?error=server_error");
	            }
	        } else {
	            response.sendRedirect("user.jsp?error=ride_not_deletable");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }
}