package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.dao.RiderDAO;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.Rider;
import com.MegaCity_Cab.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/userDashboard")
public class UserDashboardServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            RideDAO rideDAO = new RideDAO();
            List<Ride> userRides = rideDAO.getUserRides(user.getId());
            
            // Get rider details for assigned rides
            Map<Integer, Rider> riderMap = new HashMap<>();
            RiderDAO riderDAO = new RiderDAO();
            
            for (Ride ride : userRides) {
                if (ride.getAssignedRiderId() != null) {
                    Rider rider = riderDAO.getRiderById(ride.getAssignedRiderId());
                    riderMap.put(ride.getRideId(), rider);
                }
            }
            
            request.setAttribute("userRides", userRides);
            request.setAttribute("riderMap", riderMap);
            
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load rides");
        }
        
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}