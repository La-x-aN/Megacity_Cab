<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin-Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .nav-bar { background: #333; padding: 1rem; color: white; }
        .nav-bar a { color: white; margin-right: 1rem; text-decoration: none; }
        .search-box { margin: 1rem 0; }
        .data-table { width: 100%; border-collapse: collapse; margin: 1rem 0; }
        .data-table th, .data-table td { padding: 0.75rem; border: 1px solid #ddd; }
        .data-table tr:nth-child(even) { background-color: #f9f9f9; }
        .error { color: red; padding: 1rem; border: 1px solid red; margin: 1rem 0; }
    </style>
</head>
<body>
    <div class="nav-bar">
        <a href="#users">Manage Users</a>
        <a href="#rides">Manage Rides</a>
        <a href="login.jsp" style="float: right;">Logout</a>
    </div>

    <div class="container">
        <h1>Welcome-Admin: <c:out value="${sessionScope.user.name}"/></h1>

        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <!-- User Management Section -->
        <div id="users" class="admin-section">
            <h2>Manage Users</h2>
            
            <div class="search-box">
                <input type="text" placeholder="Search users..." id="userSearch">
            </div>

            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.allUsers}" var="user">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.email}"/></td>
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

        <!-- Ride Management Section -->
        <div id="rides" class="admin-section">
            <h2>Manage Rides</h2>
            
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Ride ID</th>
                        <th>User</th>
                        <th>Pickup</th>
                        <th>Destination</th>
                        <th>Status</th>
                        <th>Assign Rider</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.rides}" var="ride">
                        <tr>
                            <td><c:out value="${ride.rideId}"/></td>
                            <td><c:out value="${ride.userId}"/></td>
                            <td><c:out value="${ride.pickupLocation}"/></td>
                            <td><c:out value="${ride.destination}"/></td>
                            <td><c:out value="${ride.status}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${ride.status == 'REQUESTED'}">
                                        <form action="AdminAssignRideServlet" method="post">
                                            <input type="hidden" name="rideId" value="${ride.rideId}">
                                            <select name="riderId" required>
                                                <c:forEach items="${requestScope.riders}" var="rider">
                                                    <option value="${rider.riderId}">
                                                        <c:out value="${rider.vehicleNumber}"/> 
                                                        (<c:out value="${rider.vehicleType}"/>)
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <button type="submit">Assign</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="assignedRider" value="${ride.assignedRiderId}"/>
                                        <c:choose>
                                            <c:when test="${assignedRider != 0}">
                                                Assigned to Rider #<c:out value="${assignedRider}"/>
                                            </c:when>
                                            <c:otherwise>
                                                N/A
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
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