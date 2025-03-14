<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - MegaCity Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">MegaCity Cab</a>
        </div>
    </nav>

    <main class="container flex-grow-1">
        <div class="card auth-card">
            <div class="card-header auth-header">
                <h3 class="text-center mb-0">Create Account</h3>
            </div>
            <div class="card-body p-4">
                <form action="register" method="post" class="needs-validation" novalidate>
                    <div class="row g-3">
                        <!-- Personal Information -->
                        <div class="col-md-6">
                            <label for="name" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="col-md-6">
                            <label for="nic" class="form-label">NIC Number</label>
                            <input type="text" class="form-control" id="nic" name="nic" required>
                        </div>
                        <div class="col-md-6">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="col-md-6">
                            <label for="phone" class="form-label">Phone Number</label>
                            <input type="tel" class="form-control" id="phone" name="tpnum" required>
                        </div>
                        <div class="col-md-6">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>

                        <!-- Role Selection -->
                        <div class="col-12">
                            <div class="card border-0 bg-light">
                                <div class="card-body">
                                    <h5 class="mb-3">Register As:</h5>
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="role" 
                                                    id="user" value="User" checked>
                                                <label class="form-check-label" for="user">
                                                    Passenger
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="role" 
                                                    id="rider" value="Rider">
                                                <label class="form-check-label" for="rider">
                                                    Driver
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Vehicle Information (Hidden by default) -->
                        <div id="vehicleFields" class="d-none">
                            <div class="col-12 mt-4">
                                <h5 class="mb-3">Vehicle Information</h5>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="vehicleType" class="form-label">Vehicle Type</label>
                                        <select class="form-select" id="vehicleType" name="vehicleSelect" required>
                                            <option value="motorcycle">Motor Bike</option>
                                            <option value="threewheel">Three Wheel</option>
                                            <option value="car">Car</option>
                                            <option value="van">Van</option>
                                            <option value="truck">Truck</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="vehicleModel" class="form-label">Vehicle Model</label>
                                        <input type="text" class="form-control" id="vehicleModel" name="vehicleModel" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="vehicleNumber" class="form-label">Vehicle Number</label>
                                        <input type="text" class="form-control" id="vehicleNumber" name="vehicleNumber" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <div class="col-12 mt-4">
                            <button type="submit" class="btn btn-primary w-100 py-2">Create Account</button>
                        </div>
                        <div class="text-center mt-4">
                    		<p class="mb-0">current user? <a href="login.jsp" class="text-decoration-none">login here</a></p>
                		</div>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>