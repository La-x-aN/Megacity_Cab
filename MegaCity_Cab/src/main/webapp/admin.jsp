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
<c:if test="${not empty param.success}">
    <div class="alert success">
        <c:choose>
            <c:when test="${param.success == 'ride_assigned'}">
                Rider successfully assigned!
            </c:when>
        </c:choose>
    </div>
</c:if>

<c:if test="${not empty param.error}">
    <div class="alert error">
        <c:choose>
            <c:when test="${param.error == 'assignment_failed'}">
                Failed to assign rider. Please try again.
            </c:when>
            <c:when test="${param.error == 'server_error'}">
                Server error occurred. Please contact admin.
            </c:when>
        </c:choose>
    </div>
</c:if>
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
                        <th>Vehicle Type</th>
                        <th>Distance (km)</th>
        				<th>Cost (LKR)</th>
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
                    		<td><c:out value="${ride.selectedVehicle}"/></td>
		                    <td><fmt:formatNumber value="${ride.distance}" pattern="#0.0"/></td>
		            		<td>LKR <fmt:formatNumber value="${ride.cost}" pattern="#,##0.00"/></td>
                            <td><c:out value="${ride.status}"/></td>
							<td>
							    <c:choose>
							        <c:when test="${ride.status == 'REQUESTED'}">
							            <form action="AdminAssignRideServlet" method="post">
							                <input type="hidden" name="rideId" value="${ride.rideId}">
							                <select name="riderId" required>
							                    <option value="">Select Rider</option>
							                    <c:forEach items="${riders}" var="rider">
							                        <option value="${rider.riderId}">
							                            ${rider.vehicleNumber} (${rider.vehicleType})
							                        </option>
							                    </c:forEach>
							                </select>
							                <button type="submit">Assign</button>
							            </form>
							        </c:when>
							        <c:otherwise>
							            <!-- Handle NULL values from database -->
							            <c:choose>
							                <c:when test="${ride.assignedRiderId ne 0}">
							                    Assigned to Rider #${ride.assignedRiderId}
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