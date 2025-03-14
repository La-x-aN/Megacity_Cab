package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.UserDAO;
import com.MegaCity_Cab.model.User.Role;
import com.MegaCity_Cab.model.User;
import com.MegaCity_Cab.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class LoginServletTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginServlet loginServlet;

    @Test
    void doPost_AdminLogin_Success() throws Exception {
        // Arrange
        User adminUser = new User();
        adminUser.setRole(Role.ADMIN);
        adminUser.setEmail("admin@test.com");
        adminUser.setPasswordHash("hashedPassword");

        when(userDAO.findByEmail("admin@test.com")).thenReturn(adminUser);
        when(request.getParameter("email")).thenReturn("admin@test.com");
        when(request.getParameter("password")).thenReturn("correctPassword");
        when(request.getSession()).thenReturn(session);

        try (var securityMock = mockStatic(SecurityUtil.class)) {
            securityMock.when(() -> SecurityUtil.checkPassword("correctPassword", "hashedPassword"))
                        .thenReturn(true);

            // Act
            loginServlet.doPost(request, response);
        }

        // Assert
        verify(response).sendRedirect("adminDashboard");
        verify(session).setAttribute("user", adminUser);
    }

    @Test
    void doPost_InvalidCredentials_RedirectWithError() throws Exception {
        // Arrange
        when(userDAO.findByEmail("unknown@test.com")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("unknown@test.com");
        when(request.getParameter("password")).thenReturn("anyPassword");

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect("login.jsp?error=invalid_credentials");
    }

    @Test
    void doPost_DatabaseError_RedirectWithServerError() throws Exception {
        // Arrange
        when(userDAO.findByEmail("error@test.com")).thenThrow(new RuntimeException("DB Error"));
        when(request.getParameter("email")).thenReturn("error@test.com");
        when(request.getParameter("password")).thenReturn("anyPassword");

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect("login.jsp?error=server_error");
    }
}