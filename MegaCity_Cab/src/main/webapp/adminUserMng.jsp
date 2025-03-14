<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Management Section</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="adminNav.jsp" />
        <!-- User Management Section -->
        <div id="users" class="admin-section">
            <h2>Manage Users</h2>
            
<c:if test="${not empty param.updateSuccess}">
    <p class="success-message">User updated successfully!</p>
</c:if>
<c:if test="${not empty param.deleteSuccess}">
    <p class="success-message">User deleted successfully!</p>
</c:if>
<c:if test="${not empty param.error}">
    <p class="error-message">Error: ${param.error}</p>
</c:if>
            
            <div class="search-box">
                <input type="text" placeholder="Search users..." id="userSearch">
            </div>

            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>NIC</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.allUsers}" var="user">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.nic}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.phone}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td>
                                <a href="EditUserServlet?id=${user.id}">Edit</a> | 
                                <a href="DeleteUserServlet?id=${user.id}" 
                                   onclick="return confirm('Delete this user?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    <script>
        // Client-side search functionality
        document.getElementById('userSearch').addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            document.querySelectorAll('#users tbody tr').forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(searchTerm) ? '' : 'none';
            });
        });
    </script>
</body>
</html>