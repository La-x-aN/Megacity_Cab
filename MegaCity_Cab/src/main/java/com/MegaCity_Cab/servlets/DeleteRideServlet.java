package com.MegaCity_Cab.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.User;

@WebServlet("/deleteRide")
public class DeleteRideServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int rideId = Integer.parseInt(request.getParameter("rideId"));
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            RideDAO rideDAO = new RideDAO();
            Ride ride = rideDAO.getRideById(rideId);

            if (ride == null) {
                response.sendRedirect("user.jsp?error=ride_not_found");
                return;
            }

            if (ride.getUserId() != user.getId()) {
                response.sendRedirect("user.jsp?error=unauthorized");
                return;
            }

            if (ride.getStatus() != Ride.Status.REQUESTED) {
                response.sendRedirect("user.jsp?error=ride_not_deletable");
                return;
            }

            boolean deleted = rideDAO.deleteRide(rideId);
            if (deleted) {
                response.sendRedirect("userDashboard?success=ride_deleted");
            } else {
                response.sendRedirect("userDashboard?error=delete_failed");
            }
        } catch (Exception e) {
            response.sendRedirect("userDashboard?error=server_error");
        }
    }
}