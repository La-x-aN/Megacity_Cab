package com.MegaCity_Cab.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.MegaCity_Cab.dao.UserDAO;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            boolean success = userDAO.deleteUser(userId);
            
            if (success) {
                response.sendRedirect("adminUsers?deleteSuccess=true");
            } else {
                response.sendRedirect("adminUsers?error=User+not+found");
            }
        } catch (SQLException e) {
            // Handle specific SQL state for foreign key violation
            if (e.getSQLState().equals("23000")) { // MySQL foreign key violation
                response.sendRedirect("adminUsers?error=Cannot+delete+user+with+associated+records");
            } else {
                response.sendRedirect("adminUsers?error=Database+error");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("adminUsers?error=Invalid+user+ID");
        } catch (Exception e) {
            response.sendRedirect("adminUsers?error=Error+deleting+user");
        }
    }
}