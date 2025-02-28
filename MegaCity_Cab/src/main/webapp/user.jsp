<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.megacityCab.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>User_Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Welcome User: <%= user.getName() %></h1>
    <a href="login.jsp">Logout</a>
    
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
	    <input 
	        type="datetime-local" 
	        name="scheduledTime" 
	        required
	        pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
	    >
   </div>
<% if(request.getParameter("error") != null) { %>
    <div class="alert error">
        <% 
        String error = request.getParameter("error");
        switch(error) {
            case "missing_time": %>Please select a date and time<% break;
            case "invalid_time_format": %>Invalid date/time format<% break;
            default: %>Error booking ride<% break;
        } 
        %>
    </div>
<% } %>
        <button type="submit">Book Ride</button>
    </form>

    <h2>Your Upcoming Rides</h2>
    <table class="rides-table">
        <tr>
            <th>Pickup</th>
            <th>Destination</th>
            <th>Scheduled Time</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${userRides}" var="ride">
            <tr>
                <td>${ride.pickupLocation}</td>
                <td>${ride.destination}</td>
               	<td><fmt:formatDate value="${ride.scheduledTimeAsDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>${ride.status}</td>
                <td>
                    <c:if test="${ride.editable}">
                        <button class="delete-btn" data-ride-id="${ride.rideId}">Cancel your ride</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>