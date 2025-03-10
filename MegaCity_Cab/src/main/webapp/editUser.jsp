<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit User</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="adminNav.jsp" />
    <div class="admin-section">
        <h2>Edit User</h2>
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>
        <form action="EditUserServlet" method="post">
            <input type="hidden" name="id" value="${user.id}">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" name="name" value="${user.name}" required>
            </div>
            <div class="form-group">
                <label>NIC:</label>
                <input type="text" name="nic" value="${user.nic}" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="${user.email}" required>
            </div>
            <div class="form-group">
                <label>Phone:</label>
                <input type="text" name="phone" value="${user.phone}" required>
            </div>
            <div class="form-group">
                <label>Role:</label>
                <select name="role">
                    <option value="user" ${user.role == 'user' ? 'selected' : ''}>User</option>
                    <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Admin</option>
                    <option value="rider" ${user.role == 'rider' ? 'selected' : ''}>Rider</option>
                </select>
            </div>
            <div class="form-group">
                <input type="submit" value="Update User" class="btn-submit">
            </div>
        </form>
    </div>
</body>
</html>