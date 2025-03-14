package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.RiderDAO;
import com.MegaCity_Cab.model.Rider;
import com.MegaCity_Cab.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminRiders")
public class AdminRidersServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Authorization check
        if (user == null || user.getRole() != User.Role.ADMIN) {
            response.sendRedirect("login.jsp");
            return;
        }

        RiderDAO riderDAO = new RiderDAO();
        try {
            List<Rider> riders = riderDAO.getAllRiders();
            request.setAttribute("riders", riders);
        } catch (Exception e) {
            request.setAttribute("error", "Error fetching riders: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/adminRiders.jsp").forward(request, response);
    }
}