package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.User;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/bookRide")
public class BookRideServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		
		 HttpSession session = request.getSession(false);
		    if (session == null || session.getAttribute("user") == null) {
		        response.sendRedirect("login.jsp");
		        return;
		    }
		
        
		User user = (User) session.getAttribute("user");
        String pickup = request.getParameter("pickup");
        String destination = request.getParameter("destination");
        String scheduledTimeStr = request.getParameter("scheduledTime");
        String distanceStr = request.getParameter("distance");

        // Validate inputs
        if (scheduledTimeStr == null || scheduledTimeStr.isEmpty()) {
        	response.sendRedirect("userDashboard");
            return;
        }

        try {
        	LocalDateTime scheduledTime = LocalDateTime.parse(request.getParameter("scheduledTime") );
        	double distance = Double.parseDouble(distanceStr);
            Ride ride = new Ride();
            
            ride.setDistance(distance);
            ride.calculateCost();
            ride.setUserId(user.getId());
            ride.setPickupLocation(pickup);
            ride.setDestination(destination);
            ride.setScheduledTime(scheduledTime);

            if (new RideDAO().createRide(ride)) {
            	response.sendRedirect("userDashboard");
            } else {
            	response.sendRedirect("userDashboard?error=creation_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("userDashboard?error=invalid_distance");
        } catch (DateTimeParseException e) {
        	response.sendRedirect("userDashboard?error=invalid_time_format");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
