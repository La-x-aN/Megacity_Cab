package com.MegaCity_Cab.servlets;

import com.MegaCity_Cab.dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserServletTest {

    @Mock
    private UserDAO mockUserDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private DeleteUserServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new DeleteUserServlet();
        injectMockUserDAO();
    }

    private void injectMockUserDAO() throws Exception {
        Field userDaoField = DeleteUserServlet.class.getDeclaredField("userDAO");
        userDaoField.setAccessible(true);
        userDaoField.set(servlet, mockUserDAO);
    }

    @Test
    void doGet_SuccessfulDeletion_RedirectsWithSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(mockUserDAO.deleteUser(123)).thenReturn(true);

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?deleteSuccess=true");
    }

    @Test
    void doGet_UserNotFound_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(mockUserDAO.deleteUser(123)).thenReturn(false);

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=User+not+found");
    }

    @Test
    void doGet_InvalidIdFormat_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn("invalid");

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=Invalid+user+ID");
    }

    @Test
    void doGet_ForeignKeyViolation_RedirectsWithConstraintError() throws Exception {
        SQLException sqlEx = mock(SQLException.class);
        when(sqlEx.getSQLState()).thenReturn("23000");
        when(request.getParameter("id")).thenReturn("123");
        when(mockUserDAO.deleteUser(anyInt())).thenThrow(sqlEx);

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=Cannot+delete+user+with+associated+records");
    }

    @Test
    void doGet_OtherSQLException_RedirectsWithDatabaseError() throws Exception {
        SQLException sqlEx = mock(SQLException.class);
        when(sqlEx.getSQLState()).thenReturn("HY000");
        when(request.getParameter("id")).thenReturn("123");
        when(mockUserDAO.deleteUser(anyInt())).thenThrow(sqlEx);

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=Database+error");
    }

    @Test
    void doGet_GeneralException_RedirectsWithServerError() throws Exception {
        when(request.getParameter("id")).thenReturn("123");
        when(mockUserDAO.deleteUser(anyInt())).thenThrow(new RuntimeException());

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=Error+deleting+user");
    }

    @Test
    void doGet_MissingIdParameter_RedirectsWithError() throws Exception {
        when(request.getParameter("id")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect("adminUsers?error=Invalid+user+ID");
    }
}