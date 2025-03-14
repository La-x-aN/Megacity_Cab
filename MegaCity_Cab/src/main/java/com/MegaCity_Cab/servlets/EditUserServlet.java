package com.MegaCity_Cab.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.MegaCity_Cab.dao.UserDAO;
import com.MegaCity_Cab.model.User;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("adminUsers?error=Missing+user+ID");
                return;
            }
            
            int userId = Integer.parseInt(idParam);
            User user = userDAO.getUserById(userId);
            
            if (user == null) {
                response.sendRedirect("adminUsers?error=User+not+found");
                return;
            }
            

            if (user.getRole() == null) {
                user.setRole(User.Role.USER);
            }
            
            request.setAttribute("user", user);
            request.getRequestDispatcher("/editUser.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("adminUsers?error=Invalid+user+ID+format");
        } catch (SQLException e) {
            response.sendRedirect("adminUsers?error=Database+error+loading+user");
        } catch (Exception e) {
            response.sendRedirect("adminUsers?error=Server+error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userId = 0;
        try {
            // Get parameters
            userId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String nic = request.getParameter("nic");
            String phone = request.getParameter("phone");
            String roleParam = request.getParameter("role");

            // Validate required fields
            if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                response.sendRedirect("EditUserServlet?id=" + userId + "&error=Name+and+Email+are+required");
                return;
            }

            // Convert role
            User.Role role;
            try {
                role = User.Role.valueOf(roleParam.toUpperCase());
            } catch (Exception e) {
                response.sendRedirect("EditUserServlet?id=" + userId + "&error=Invalid+role");
                return;
            }

            // Create user object
            User user = new User();
            user.setId(userId);
            user.setName(name.trim());
            user.setEmail(email.trim());
            user.setNic(nic != null ? nic.trim() : null);
            user.setPhone(phone != null ? phone.trim() : null);
            user.setRole(role);

            // Update user
            boolean success = userDAO.updateUser(user);
            
            if (success) {
                response.sendRedirect("adminUsers?updateSuccess=true");
            } else {
                response.sendRedirect("EditUserServlet?id=" + userId + "&error=Update+failed");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("adminUsers?error=Invalid+user+ID");
        } catch (SQLIntegrityConstraintViolationException e) {
            response.sendRedirect("EditUserServlet?id=" + userId + "&error=Email/NIC+already+exists");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("EditUserServlet?id=" + userId + "&error=Server+error");
        }
    }
}