package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.User;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/rider/completeRide")
public class CompleteRideServlet extends HttpServlet {
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
        if (!user.getRole().equalsIgnoreCase("RIDER")) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        try {
            int rideId = Integer.parseInt(request.getParameter("rideId"));
            RideDAO rideDAO = new RideDAO();
            if (rideDAO.completeRide(rideId)) {
                response.sendRedirect("riderDashboard?success=ride_completed");
            } else {
                response.sendRedirect("riderDashboard?error=completion_failed");
            }
        } catch (Exception e) {
            response.sendRedirect("riderDashboard?error=server_error");
            e.printStackTrace();
        }
    }
}