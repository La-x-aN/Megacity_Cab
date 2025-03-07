<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.MegaCity_Cab.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>User-Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Welcome User: <c:out value="${sessionScope.user.name}"/></h1>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
    
    <div class="booking-section">
        <h2>Book a Ride</h2>
        <form action="bookRide" method="post">
            <div class="form-group">
                <label>Pickup Location:</label>
                <input type="text" name="pickup" required>
            </div>
            <div class="form-group">
                <label>Destination:</label>
                <input type="text" name="destination" required>
            </div>
            <div class="form-group">
                <label>Date and Time:</label>
                <input type="datetime-local" 
                       name="scheduledTime" 
                       required
                       pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}">
            </div>
            <div class="form-group">
    			<label>Distance (km):</label>
    			<input type="number" name="distance" step="0.1" required>
			</div>

            <c:if test="${not empty param.error}">
                <div class="alert error">
                    <c:choose>
                        <c:when test="${param.error == 'missing_time'}">
                            Please select a date and time
                        </c:when>
                        <c:when test="${param.error == 'invalid_time_format'}">
                            Invalid date/time format
                        </c:when>
                        <c:otherwise>
                            Error booking ride
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <button type="submit">Book Ride</button>
        </form>

        <h2>Your Upcoming Rides</h2>
        <table class="rides-table">
            <tr>
                <th>Pickup</th>
                <th>Destination</th>
                <th>Distance (km)</th>
        		<th>Cost</th>
                <th>Scheduled Time</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${userRides}" var="ride">
                <tr>
                    <td><c:out value="${ride.pickupLocation}"/></td>
                    <td><c:out value="${ride.destination}"/></td>
                    <td><fmt:formatNumber value="${ride.distance}" pattern="#0.0"/></td>
            		<td>$<fmt:formatNumber value="${ride.cost}" pattern="#0.00"/></td>
                    <td>
                        <fmt:formatDate value="${ride.scheduledTimeAsDate}" 
                                      pattern="yyyy-MM-dd HH:mm" />
                    </td>
                    <td><c:out value="${ride.status}"/></td>
                    <td>
                        <c:if test="${ride.editable}">
                            <button class="delete-btn" 
                                    data-ride-id="${ride.rideId}">
                                Cancel your ride
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>