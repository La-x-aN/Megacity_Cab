package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.UserDAO;
import com.MegaCity_Cab.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditUserServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserDAO userDAO;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @InjectMocks
    private EditUserServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new EditUserServlet() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void init() throws ServletException {
                this.userDAO = EditUserServletTest.this.userDAO;
            }
        };
        servlet.init();
    }

    @Test
    void doGet_MissingId_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn(null);
        
        servlet.doGet(request, response);
        
        verify(response).sendRedirect("adminUsers?error=Missing+user+ID");
    }

    @Test
    void doGet_InvalidIdFormat_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("invalid");
        
        servlet.doGet(request, response);
        
        verify(response).sendRedirect("adminUsers?error=Invalid+user+ID+format");
    }

    @Test
    void doGet_UserNotFound_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(userDAO.getUserById(123)).thenReturn(null);
        
        servlet.doGet(request, response);
        
        verify(response).sendRedirect("adminUsers?error=User+not+found");
    }

    @Test
    void doGet_DatabaseError_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(userDAO.getUserById(123)).thenThrow(new SQLException());
        
        servlet.doGet(request, response);
        
        verify(response).sendRedirect("adminUsers?error=Database+error+loading+user");
    }

    @Test
    void doGet_ValidUser_ForwardsToEditPage() throws Exception {
        User user = new User();
        user.setId(123);
        user.setRole(User.Role.ADMIN);
        
        when(request.getParameter("id")).thenReturn("123");
        when(userDAO.getUserById(123)).thenReturn(user);
        when(request.getRequestDispatcher("/editUser.jsp")).thenReturn(requestDispatcher);
        
        servlet.doGet(request, response);
        
        verify(request).setAttribute("user", user);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_MissingRequiredFields_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("email")).thenReturn("");
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("EditUserServlet?id=123&error=Name+and+Email+are+required");
    }

    @Test
    void doPost_InvalidRole_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("role")).thenReturn("INVALID");
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("EditUserServlet?id=123&error=Invalid+role");
    }

    @Test
    void doPost_ValidUpdate_RedirectsWithSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Updated Name");
        when(request.getParameter("email")).thenReturn("updated@example.com");
        when(request.getParameter("role")).thenReturn("ADMIN");
        when(userDAO.updateUser(any(User.class))).thenReturn(true);
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("adminUsers?updateSuccess=true");
    }

    @Test
    void doPost_UpdateFails_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("role")).thenReturn("USER");
        when(userDAO.updateUser(any(User.class))).thenReturn(false);
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("EditUserServlet?id=123&error=Update+failed");
    }

    @Test
    void doPost_DuplicateEntry_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("role")).thenReturn("USER");
        when(userDAO.updateUser(any(User.class)))
            .thenThrow(new SQLIntegrityConstraintViolationException());
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("EditUserServlet?id=123&error=Email/NIC+already+exists");
    }

    @Test
    void doPost_ServerError_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("role")).thenReturn("USER");
        when(userDAO.updateUser(any(User.class))).thenThrow(new RuntimeException());
        
        servlet.doPost(request, response);
        
        verify(response).sendRedirect("EditUserServlet?id=123&error=Server+error");
    }
}