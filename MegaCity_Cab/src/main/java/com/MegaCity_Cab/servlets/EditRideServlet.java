package com.MegaCity_Cab.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;
import java.time.format.DateTimeParseException;

@WebServlet("/editRide")
public class EditRideServlet extends HttpServlet {
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
			 if (ride == null) {
			        response.sendRedirect("user.jsp?error=ride_not_found");
			        return;
			    }

			    if (!ride.isEditable()) {
			    	 try {
			 	        if (rideDAO.updateRide(ride)) {
			 	            response.sendRedirect("user.jsp?success=ride_updated");
			 	        } else {
			 	            response.sendRedirect("user.jsp?error=update_failed");
			 	        }
			 	    } catch (Exception e) {
			 	        e.printStackTrace();
			 	        response.sendRedirect("user.jsp?error=server_error");
			 	    }
			        response.sendRedirect("user.jsp?error=ride_not_editable");
			        return;
			    }

			    try {
			        ride.setPickupLocation(request.getParameter("pickup"));
			        ride.setDestination(request.getParameter("destination"));
			        ride.setScheduledTime(LocalDateTime.parse(request.getParameter("scheduledTime")));
			    } catch (DateTimeParseException e) {
			        response.sendRedirect("user.jsp?error=invalid_date_format");
			        return;
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   

	   
	}
}