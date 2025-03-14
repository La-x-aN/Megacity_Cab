<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Passenger Dashboard - MegaCity Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
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
        <!-- Error Messages -->
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'missing_time'}">
                        <i class="bi bi-exclamation-circle-fill"></i> Please select a date and time
                    </c:when>
                    <c:when test="${param.error == 'invalid_time_format'}">
                        <i class="bi bi-exclamation-circle-fill"></i> Invalid date/time format
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-exclamation-circle-fill"></i> Error booking ride
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row g-4">
            <!-- Booking Section -->
            <div class="col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Book New Ride</h5>
                    </div>
                    <div class="card-body">
                        <form action="bookRide" method="post">
                            <div class="mb-3">
                                <label class="form-label">Pickup Location</label>
                                <input type="text" class="form-control" name="pickup" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Destination</label>
                                <input type="text" class="form-control" name="destination" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Date - Time</label>
                                <input type="datetime-local" 
                                       class="form-control"
                                       name="scheduledTime" 
                                       required
                                       pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Vehicle Type</label>
                                <select class="form-select" name="SelectedVehicle" required>
                                    <option value="">Select Vehicle</option>
                                    <option value="MOTOR_BIKE">Motor Bike (LKR 30/km)</option>
                                    <option value="THREE_WHEEL">Three Wheel (LKR 35/km)</option>
                                    <option value="CAR">Car (LKR 45/km)</option>
                                    <option value="VAN">Van (LKR 60/km)</option>
                                    <option value="TRUCK">Truck (LKR 80/km)</option>
                                </select>
                            </div>
                            <div class="mb-4">
                                <label class="form-label">Distance (km)</label>
                                <input type="number" class="form-control" name="distance" step="0.1" required>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-car-front"></i> Book Ride
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Ride History -->
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">Your Rides</h5>
                        <span class="badge bg-light text-primary fs-6">${fn:length(userRides)} upcoming</span>
                    </div>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Pickup</th>
                                        <th>Destination</th>
                                        <th>Vehicle</th>
                                        <th>Distance</th>
                                        <th>Fare</th>
                                        <th>Time</th>
                                        <th>Status</th>
                                        <th>Driver</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${userRides}" var="ride">
                                        <c:if test="${ride.status != 'COMPLETED'}">
                                            <tr>
                                                <td><c:out value="${ride.pickupLocation}"/></td>
                                                <td><c:out value="${ride.destination}"/></td>
                                                <td><c:out value="${ride.selectedVehicle}"/></td>
                                                <td><fmt:formatNumber value="${ride.distance}" pattern="#0.0"/> km</td>
                                                <td>LKR <fmt:formatNumber value="${ride.cost}" pattern="#,##0.00"/></td>
                                                <td>
                                                    <fmt:formatDate value="${ride.scheduledTimeAsDate}" 
                                                                  pattern="MMM dd, HH:mm" />
                                                </td>
                                                <td>
                                                    <span class="badge ${ride.status eq 'ASSIGNED' ? 'bg-warning' : 'bg-secondary'}">
                                                        <c:out value="${ride.status}"/>
                                                    </span>
                                                </td>
                                                <td>
                                                    <c:if test="${ride.status == 'ASSIGNED'}">
                                                        <c:set var="rider" value="${riderMap[ride.rideId]}"/>
                                                        <c:if test="${not empty rider}">
                                                            <button class="btn btn-sm btn-outline-primary show-rider-btn"
                                                                    data-bs-toggle="modal" 
                                                                    data-bs-target="#riderModal"
                                                                    data-vehicle-number="${rider.vehicleNumber}"
                                                                    data-vehicle-type="${rider.vehicleType}"
                                                                    data-phone="${rider.phone}">
                                                                <i class="bi bi-person-badge"></i> Details
                                                            </button>
                                                        </c:if>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${ride.editable}">
                                                        <form action="deleteRide" method="post">
                                                            <input type="hidden" name="rideId" value="${ride.rideId}">
                                                            <button type="submit" class="btn btn-sm btn-danger">
                                                                <i class="bi bi-x-circle"></i> Cancel
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Rider Details Modal -->
    <div class="modal fade" id="riderModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Driver Information</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <dl class="row">
                        <dt class="col-sm-4">Vehicle Number</dt>
                        <dd class="col-sm-8" id="modalVehicleNumber"></dd>
                        
                        <dt class="col-sm-4">Vehicle Type</dt>
                        <dd class="col-sm-8" id="modalVehicleType"></dd>
                        
                        <dt class="col-sm-4">Contact</dt>
                        <dd class="col-sm-8" id="modalPhone"></dd>
                    </dl>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>