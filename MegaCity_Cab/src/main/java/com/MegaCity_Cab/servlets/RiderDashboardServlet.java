package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RiderDashboard")
public class RiderDashboardServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("user") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }
	    User user = (User) session.getAttribute("user");
        	if (!user.getRole().equalsIgnoreCase("RIDER")) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }
	    try {
            RideDAO rideDAO = new RideDAO();
            List<Ride> assignedRides = rideDAO.getRidesByRider(user.getId());
            request.setAttribute("assignedRides", assignedRides);
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error loading rides");
	    }
	    request.getRequestDispatcher("/rider.jsp").forward(request, response);
	}
}