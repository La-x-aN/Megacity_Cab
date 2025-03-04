package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RideDAO;
import com.MegaCity_Cab.dao.RiderDAO;
import com.MegaCity_Cab.dao.UserDAO;
import com.MegaCity_Cab.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getRole() != User.Role.ADMIN) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        try {
            // Initialize DAOs
            RideDAO rideDAO = new RideDAO();
            RiderDAO riderDAO = new RiderDAO();
            UserDAO userDAO = new UserDAO();
            
            // Get data once
            request.setAttribute("rides", rideDAO.getAllRides());
            request.setAttribute("riders", riderDAO.getAllRiders());
            request.setAttribute("allUsers", userDAO.getAllUsers());
            
        } catch (Exception e) {
            request.setAttribute("error", "Error loading data: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}