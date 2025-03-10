<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Riders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<jsp:include page="adminNav.jsp" />
    
    <div class="container">
        <h2>All Registered Riders</h2>
        
        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <table class="data-table">
            <tr>
                <th>Rider ID</th>
                <th>User ID</th>
                <th>Vehicle Type</th>
                <th>Vehicle Model</th>
                <th>Vehicle Number</th>
                <th>Phone</th>
            </tr>
            <c:forEach items="${riders}" var="rider">
                <tr>
                    <td><c:out value="${rider.riderId}"/></td>
                    <td><c:out value="${rider.userId}"/></td>
                    <td><c:out value="${rider.vehicleType}"/></td>
                    <td><c:out value="${rider.vehicleModel}"/></td>
                    <td><c:out value="${rider.vehicleNumber}"/></td>
                    <td><c:out value="${rider.phone}"/></td>
                </tr>
            </c:forEach>
        </table>
        
        <c:if test="${empty riders}">
            <div class="info">No riders found.</div>
        </c:if>
    </div>
</body>
</html>