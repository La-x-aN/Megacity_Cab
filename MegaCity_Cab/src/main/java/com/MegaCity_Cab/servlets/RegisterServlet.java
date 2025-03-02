package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.*;
import com.MegaCity_Cab.model.*;
import com.MegaCity_Cab.utils.*;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String phone = request.getParameter("tpnum");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String vehicleType = request.getParameter("vehicleSelect");
        String vehicleModel = request.getParameter("vehicleModel");
        String vehicleNumber = request.getParameter("vehicleNumber");

        // Create and save user
        User user = new User();
        user.setName(name);
        user.setNic(nic);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPasswordHash(SecurityUtil.hashPassword(password));
        user.setRole(role != null ? role : "user");

        UserDAO userDAO = new UserDAO();
        try {
            if(userDAO.createUser(user)) {
                // Save rider details if applicable
                if("rider".equalsIgnoreCase(role)) {
                    User createdUser = userDAO.findByEmail(email);
                    Rider rider = new Rider();
                    rider.setUserId(createdUser.getId());
                    rider.setVehicleType(vehicleType);
                    rider.setVehicleModel(vehicleModel);
                    rider.setVehicleNumber(vehicleNumber);
                    new RiderDAO().createRider(rider);
                }
                response.sendRedirect("login.jsp?registered=true");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}