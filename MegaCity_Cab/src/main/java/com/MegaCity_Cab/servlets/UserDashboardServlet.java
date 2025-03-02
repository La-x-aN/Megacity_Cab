package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/userDashboard")
public class UserDashboardServlet extends HttpServlet {
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
	    try {
	        RideDAO rideDAO = new RideDAO();
	        List<Ride> userRides = rideDAO.getUserRides(user.getId());
	        request.setAttribute("userRides", userRides); // Critical line
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error loading rides");
	    }
	    request.getRequestDispatcher("/user.jsp").forward(request, response);
	}
}