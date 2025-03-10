<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<title>Insert title here</title>

    <style>
      .nav-bar {
        background: #2c3e50;
        padding: 1rem 2rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .nav-links {
        display: flex;
        gap: 2rem;
    }

    .nav-link {
        color: #ecf0f1;
        text-decoration: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        transition: all 0.3s ease;
    }

    .nav-link:hover {
        background-color: #34495e;
        transform: translateY(-2px);
    }

    .profile-section {
        display: flex;
        align-items: center;
        gap: 1.5rem;
        color: #ecf0f1;
    }

    .admin-name {
        font-weight: 500;
        font-size: 0.9rem;
    }

    .logout-link {
        color: #ecf0f1;
        text-decoration: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        background-color: #e74c3c;
        transition: all 0.3s ease;
    }

    .logout-link:hover {
        background-color: #c0392b;
        transform: translateY(-2px);
    }

    .error {
        color: #e74c3c;
        background-color: #f8d7da;
        padding: 0.75rem;
        border-radius: 4px;
        margin: 1rem 0;
        border: 1px solid #f5c6cb;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .nav-bar {
            flex-direction: column;
            gap: 1rem;
            padding: 1rem;
        }
        
        .nav-links {
            flex-wrap: wrap;
            justify-content: center;
        }
        
        .profile-section {
            flex-direction: column;
            gap: 0.5rem;
        }
    }
        .data-table { width: 100%; border-collapse: collapse; margin: 1rem 0;  }
		.data-table th, .data-table td { padding: 0.75rem; border: 1px solid #ddd; }
		.data-table tr:nth-child(even) { background-color: #f9f9f9; }
		.error { color: red; padding: 1rem; border: 1px solid red; margin: 1rem 0; }
		.search-box { margin: 1rem 0; }
    </style>
</head>
<body>
	  <div class="nav-bar">
        <div class="nav-links">
            <a class="nav-link" href="adminDashboard">Manage Rides</a>
            <a class="nav-link" href="adminUsers">Manage Users</a>
            <a class="nav-link" href="adminRiders">View Riders</a>
        </div>

        <div class="profile-section">
            <div class="admin-name">
                ADMIN: <c:out value="${sessionScope.user.name}"/>
            </div>
            <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="error"><c:out value="${error}"/></div>
    </c:if>
</body>
</html>