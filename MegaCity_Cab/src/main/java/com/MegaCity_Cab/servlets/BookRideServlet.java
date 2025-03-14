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
        String selectedVehicleStr = request.getParameter("SelectedVehicle");
        if (selectedVehicleStr == null || selectedVehicleStr.isEmpty()) {
            response.sendRedirect("userDashboard?error=invalid_vehicle");
            return;
        }
        // Validate inputs
        if (scheduledTimeStr == null || scheduledTimeStr.isEmpty()) {
        	response.sendRedirect("userDashboard");
            return;
        }

        try {
            LocalDateTime scheduledTime = LocalDateTime.parse(scheduledTimeStr);
            double distance = Double.parseDouble(distanceStr);
            Ride.SelectedVehicle selectedVehicle = Ride.SelectedVehicle.fromString(selectedVehicleStr);
            
            Ride ride = new Ride();
            ride.setUserId(user.getId());
            ride.setPickupLocation(pickup);
            ride.setDestination(destination);
            ride.setScheduledTime(scheduledTime);
            ride.setSelectedVehicle(selectedVehicle);
            ride.setDistance(distance);
            
            // Set deadline 1 hour before scheduled time
            ride.setDeadlineTime(scheduledTime.minusHours(1));
            ride.setStatus(Ride.Status.REQUESTED);
            ride.setCost(ride.calculateCost());

            if (new RideDAO().createRide(ride)) {
                response.sendRedirect("userDashboard");
            } else {
                response.sendRedirect("userDashboard?error=creation_failed");
            }
         
        } catch (NumberFormatException e) {
            response.sendRedirect("userDashboard?error=invalid_distance");
        } catch (DateTimeParseException e) {
        	response.sendRedirect("userDashboard?error=invalid_time_format");
        } catch (IllegalArgumentException e) {
            response.sendRedirect("userDashboard?error=invalid_vehicle");
            return;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
	}
}