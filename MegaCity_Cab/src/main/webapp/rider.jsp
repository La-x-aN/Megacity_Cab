<%@ page import="com.MegaCity_Cab.dao.RiderDAO" %>
<%@ page import="com.MegaCity_Cab.model.Rider" %>
<%@ page import="com.MegaCity_Cab.model.User" %>
<%@ page import="com.MegaCity_Cab.dao.RideDAO" %>
<%@ page import="com.MegaCity_Cab.model.Ride" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.MegaCity_Cab.model.User.Role" %>


<!DOCTYPE html>
<html>
<head>
    <title>Rider-Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <c:if test="${empty sessionScope.user || sessionScope.user.role ne 'RIDER'}">
        <c:redirect url="login.jsp"/>
    </c:if>
    
     <c:if test="${not empty param.success}">
    <div class="alert success">
        <c:choose>
            <c:when test="${param.success == 'ride_completed'}">
                Ride marked as completed!
            </c:when>
        </c:choose>
    </div>
</c:if>

<c:if test="${not empty param.error}">
    <div class="alert error">
        <c:choose>
            <c:when test="${param.error == 'completion_failed'}">
                Failed to complete ride. Please try again.
            </c:when>
            <c:when test="${param.error == 'invalid_ride_id'}">
                Invalid ride selection
            </c:when>
            <c:otherwise>
                Error processing request
            </c:otherwise>
        </c:choose>
    </div>
</c:if>

    <h1>Welcome Rider: <c:out value="${sessionScope.user.name}"/></h1>
     <a href="${pageContext.request.contextPath}/logout">Logout</a>
    
    <div>
        <c:if test="${not empty rider}">
            <table id="vehicleDetails">
                <tr>
                    <th>Vehicle Type</th>
                    <th>Vehicle Model</th>
                    <th>Vehicle Number</th>
                    <th>Phone Number</th>
                </tr>
                <tr>
                    <td><c:out value="${rider.vehicleType}"/></td>
                    <td><c:out value="${rider.vehicleModel}"/></td>
                    <td><c:out value="${rider.vehicleNumber}"/></td>
                    <td><c:out value="${rider.phone}"/></td>
                </tr>
            </table>
        </c:if>
    </div>   

    <div class="rider-rides">
        <h2>Your Assigned Rides</h2>
        <table>
            <tr>
                <th>Ride ID</th>
                <th>Pickup</th>
                <th>Destination</th>
                <th>Vehicle Type</th>
                <th>Distance (km)</th>
        		<th>Cost (LKR)</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${assignedRides}" var="ride">
                <tr>
                    <td><c:out value="${ride.rideId}"/></td>
                    <td><c:out value="${ride.pickupLocation}"/></td>
                    <td><c:out value="${ride.destination}"/></td>
                    <td><c:out value="${ride.selectedVehicle}"/></td>
                    <td><fmt:formatNumber value="${ride.distance}" pattern="#0.0"/></td>
            		<td>LKR <fmt:formatNumber value="${ride.cost}" pattern="#,##0.00"/></td>
                    <td><c:out value="${ride.status}"/></td>
                    <td>
                        <c:if test="${ride.status eq 'ASSIGNED'}">
                            <form action="${pageContext.request.contextPath}/completeRide" method="post">
                                <input type="hidden" name="rideId" value="${ride.rideId}">
                                <button type="submit">Mark Complete</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    
   
</body>
</html>