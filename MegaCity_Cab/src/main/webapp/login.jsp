<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        
        <c:if test="${not empty param.error}">
            <div class="alert error">Invalid credentials</div>
        </c:if>
        
        <c:if test="${not empty param.registered}">
            <div class="alert success">Registration successful! Please login.</div>
        </c:if>
        
        <form action="login" method="post">
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <p>New user? <a href="register.jsp">Register here</a></p>
    </div>
</body>
</html>