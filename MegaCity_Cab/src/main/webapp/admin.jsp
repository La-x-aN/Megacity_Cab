<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin-Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>

<jsp:include page="adminNav.jsp" />

    <div class="container">
  
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


</body>
</html>