<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.user || sessionScope.user.role ne 'RIDER'}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Driver Dashboard - MegaCity Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">MegaCity Cab</a>
            <div class="d-flex align-items-center">
                <span class="text-white me-3">Welcome, ${sessionScope.user.name}</span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-light">Logout</a>
            </div>
        </div>
    </nav>

    <main class="container flex-grow-1 py-4">
        <!-- Alerts Section -->
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.success == 'ride_completed'}">
                        <i class="bi bi-check-circle-fill"></i> Ride marked as completed successfully!
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'completion_failed'}">
                        <i class="bi bi-exclamation-circle-fill"></i> Failed to complete ride. Please try again.
                    </c:when>
                    <c:when test="${param.error == 'invalid_ride_id'}">
                        <i class="bi bi-exclamation-circle-fill"></i> Invalid ride selection
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-exclamation-circle-fill"></i> Error processing request
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row g-4">
            <!-- Vehicle Details Card -->
            <div class="col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Your Vehicle Details</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty rider}">
                            <dl class="row">
                                <dt class="col-sm-5">Vehicle Type</dt>
                                <dd class="col-sm-7"><c:out value="${rider.vehicleType}"/></dd>

                                <dt class="col-sm-5">Vehicle Model</dt>
                                <dd class="col-sm-7"><c:out value="${rider.vehicleModel}"/></dd>

                                <dt class="col-sm-5">Vehicle Number</dt>
                                <dd class="col-sm-7"><c:out value="${rider.vehicleNumber}"/></dd>

                                <dt class="col-sm-5">Contact Number</dt>
                                <dd class="col-sm-7"><c:out value="${rider.phone}"/></dd>
                            </dl>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- Assigned Rides Card -->
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Assigned Rides</h5>
                        <span class="badge bg-light text-primary fs-6">${fn:length(assignedRides)} active</span>
                    </div>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Ride ID</th>
                                        <th>Pickup</th>
                                        <th>Destination</th>
                                        <th>Distance</th>
                                        <th>Fare</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${assignedRides}" var="ride">
                                        <tr>
                                            <td>#<c:out value="${ride.rideId}"/></td>
                                            <td><c:out value="${ride.pickupLocation}"/></td>
                                            <td><c:out value="${ride.destination}"/></td>
                                            <td><fmt:formatNumber value="${ride.distance}" pattern="#0.0"/> km</td>
                                            <td>LKR <fmt:formatNumber value="${ride.cost}" pattern="#,##0.00"/></td>
                                            <td>
                                                <span class="status-badge 
                                                    ${ride.status eq 'ASSIGNED' ? 'bg-warning text-dark' : 'bg-success text-white'}">
                                                    <c:out value="${ride.status}"/>
                                                </span>
                                            </td>
                                            <td>
                                                <c:if test="${ride.status eq 'ASSIGNED'}">
                                                    <form action="${pageContext.request.contextPath}/completeRide" method="post">
                                                        <input type="hidden" name="rideId" value="${ride.rideId}">
                                                        <button type="submit" class="btn btn-sm btn-success">
                                                            <i class="bi bi-check-circle"></i> Complete
                                                        </button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Bootstrap Icons -->
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>