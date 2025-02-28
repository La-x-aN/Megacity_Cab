<%@page import="com.megacityCab.model.User.Role"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.megacityCab.model.User" %>
<%@ page import="com.megacityCab.dao.RideDAO" %>
<%@ page import="com.megacityCab.dao.RiderDAO" %>
<%@ page import="com.megacityCab.model.Ride" %>
<%@ page import="com.megacityCab.model.Rider" %>
<%@ page import="java.util.List" %>


<%
RideDAO rideDAO = new RideDAO();
RiderDAO riderDAO = new RiderDAO();
List<Ride> rides = rideDAO.getAllRides();
List<Rider> riders = riderDAO.getAllRiders();

User user = (User) session.getAttribute("user");
if(user == null || user.getRole() != Role.ADMIN) {
    response.sendRedirect("login.jsp");
    return;
}

%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Welcome Admin: <%= user.getName() %></h1>
    <a href="login.jsp">Logout</a>
    
<div class="admin-rides">
    <h2>Manage Rides</h2>
    <table>
        <tr>
            <th>Ride ID</th>
            <th>User</th>
            <th>Pickup</th>
            <th>Destination</th>
            <th>Status</th>
            <th>Assign Rider</th>
        </tr>
        <% for(Ride ride : rides) { %>
        <tr>
            <td><%= ride.getRideId() %></td>
            <td><%= ride.getUserId() %></td>
            <td><%= ride.getPickupLocation() %></td>
            <td><%= ride.getDestination() %></td>
            <td><%= ride.getStatus() %></td>
            <td>
  				<% if(ride.getStatus().equals("requested")) { %>
    			<!-- Assign form -->
  				<% } else { 
       				 Rider assignedRider = riders.stream()
           			.filter(r -> r.getRiderId() == ride.getAssignedRiderId())
           			.findFirst()
          			.orElse(null);
  				 %>
 			  	 <%= assignedRider != null ? 
      				  assignedRider.getVehicleNumber() + " (" + assignedRider.getVehicleType() + ")" 
       						 : "N/A" %>
 				 <% } %>
			</td>

        </tr>
        <% } %>
    </table>
</div>
</body>
</html>