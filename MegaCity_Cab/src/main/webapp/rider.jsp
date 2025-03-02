<%@ page import="com.MegaCity_Cab.dao.RiderDAO" %>
<%@ page import="com.MegaCity_Cab.model.Rider" %>
<%@ page import="com.MegaCity_Cab.model.User" %>
<%@ page import="com.MegaCity_Cab.dao.RideDAO" %>
<%@ page import="com.MegaCity_Cab.model.Ride" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.MegaCity_Cab.model.User.Role" %>


<%
User user = (User) session.getAttribute("user");
if(user == null || user.getRole() != Role.RIDER) {
    response.sendRedirect("login.jsp");
    return;
}

RiderDAO riderDAO = new RiderDAO();
Rider rider = riderDAO.findByUserId(user.getId());

if(rider == null) {
    response.sendRedirect("login.jsp?error=no_rider_profile");
    return;
}

RideDAO rideDAO = new RideDAO();
List<Ride> assignedRides = rideDAO.getRidesByRider(rider.getRiderId());


request.setAttribute("assignedRides", assignedRides);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Rider_Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<h1>Welcome Rider: <%= user.getName() %></h1>
<div>
    <% if (rider != null) { %>
        <table id="vehicleDetails">
            <tr>
                <th>Vehicle Type</th>
                <th>Vehicle Model</th>
                <th>Vehicle Number</th>
            </tr>
            <tr>
                <td><%= rider.getVehicleType() %></td>
                <td><%= rider.getVehicleModel() %></td>
                <td><%= rider.getVehicleNumber() %></td>
            </tr>
        </table>
    <% }%>
 </div>   

<div class="rider-rides">
    <h2>Your Assigned Rides</h2>
    <table>
        <tr>
            <th>Ride ID</th>
            <th>Pickup</th>
            <th>Destination</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <% for(Ride ride : assignedRides) { %>
        <tr>
            <td><%= ride.getRideId() %></td>
            <td><%= ride.getPickupLocation() %></td>
            <td><%= ride.getDestination() %></td>
            <td><%= ride.getStatus() %></td>
            <td>
                <% if(ride.getStatus().equals("assigned")) { %>
                <form action="rider/completeRide" method="post">
                    <input type="hidden" name="rideId" value="<%= ride.getRideId() %>">
                    <button type="submit">Mark Complete</button>
                </form>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</div>
   
    <a href="login.jsp">Logout</a>
</body>
</html>