<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>
        <form action="register" method="post">
            <div class="role-selection">
                <label>
                    <input type="radio" name="role" value="USER" id="user" checked> User
                </label>
                <label>
                    <input type="radio" name="role" value="Rider" id="rider"> Rider
                </label>
            </div>

            <div class="form-group">
                <label>Name:</label>
                <input type="text" name="name" required>
            </div>

            <div class="form-group">
                <label>NIC:</label>
                <input type="text" name="nic" required>
            </div>

            <div class="form-group">
                <label>Phone:</label>
                <input type="tel" name="tpnum" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>

            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>

            <div id="vehicleFields" class="hidden">
                <div class="form-group">
                    <label>Vehicle Type:</label>
                    <select name="vehicleSelect" required>
                        <option value="motorcycle">Motor Bike</option>
                        <option value="threewheel">Three Wheel</option>
                        <option value="car">Car</option>
                        <option value="van">Van</option>
                        <option value="truck">Truck</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Vehicle Model:</label>
                    <input type="text" name="vehicleModel" required>
                </div>

                <div class="form-group">
                    <label>Vehicle Number:</label>
                    <input type="text" name="vehicleNumber" required>
                </div>
            </div>

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>