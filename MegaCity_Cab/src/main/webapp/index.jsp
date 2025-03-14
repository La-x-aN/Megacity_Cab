<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab - Ride the City</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Base Styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        html {
            scroll-behavior: smooth;
        }

        body {
            background: #f8f9fa;
        }

        /* Navigation */
        .navbar {
            background: #002855f2;
            padding: 1rem 5%;
            position: fixed;
            width: 100%;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
            backdrop-filter: blur(10px);
        }

        .logo {
            width: 180px;
            transition: transform 0.3s;
        }

        .nav-links {
            display: flex;
            align-items: center;
            gap: 2rem;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s;
            position: relative;
        }

        .nav-links a:not(.cta-button1):hover {
            color: #b6c90a;
        }

        /* Hero Section */
        .hero {
            height: 100vh;
            position: relative;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            padding: 0 5%;
            padding-top: 80px;
        }

        .hero-video {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
            z-index: -1;
            object-fit: cover;
            opacity: 0.8;
        }

        .hero::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, rgba(0, 40, 85, 0.8) 30%, rgba(0, 40, 85, 0.5));
        }

        .hero-content {
            position: relative;
            z-index: 1;
            max-width: 1200px;
            margin: 0 auto;
        }

        .hero-content h1 {
            font-size: clamp(2.5rem, 5vw, 4rem);
            margin-bottom: 1.5rem;
            line-height: 1.2;
            animation: fadeInDown 1s ease-out both;
            animation-delay: 0.3s;
        }

        .hero-content p {
            font-size: clamp(1.2rem, 2.5vw, 1.5rem);
            margin-bottom: 2.5rem;
            opacity: 0.9;
            animation: fadeIn 1s ease-out both;
            animation-delay: 0.6s;
        }

        /* Features Section */
        .features {
            padding: 5rem 5%;
            background: #fff;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 3rem;
        }

        .feature-card {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            text-align: center;
            transition: all 0.3s;
            overflow: hidden;
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 40px rgba(0,0,0,0.1);
        }

        .feature-card img {
            width: 100%;
            max-width: 200px;
            height: auto;
            margin-bottom: 1.5rem;
            border-radius: 10px;
            transition: transform 0.3s;
            object-fit: cover;
            aspect-ratio: 1/1;
        }

        /* Buttons */
        .cta-button {
            background: #b6c90a;
            color: #002855;
            padding: 1rem 2.5rem;
            border: none;
            border-radius: 30px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: inline-block;
            text-decoration: none;
            animation: fadeInUp 1s ease-out both;
            animation-delay: 0.9s;
        }

        .cta-button:hover {
            transform: translateY(-3px) scale(1.05);
            box-shadow: 0 10px 20px rgba(182, 201, 10, 0.3);
        }

        .cta-button1 {
            background: #3e5063;
            color: white;
            padding: 0.7rem 2rem;
            border: 2px solid transparent;
            border-radius: 25px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }

        .cta-button1:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(182, 201, 10, 0.3);
        }

        /* Footer */
        footer {
            background: #002855;
            color: white;
            padding: 3rem 5%;
            text-align: center;
        }

        .social-links {
            margin: 1.5rem 0;
            display: flex;
            justify-content: center;
            gap: 1.5rem;
        }

        .social-links a {
            color: white;
            font-size: 1.5rem;
            transition: color 0.3s;
        }

        .social-links a:hover {
            color: #b6c90a;
        }

        /* Animations */
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .nav-links { gap: 1rem; }
            .logo { width: 140px; }
            .cta-button1 { padding: 0.5rem 1.5rem; }
            .features-grid { grid-template-columns: 1fr; }
            .hero-video { opacity: 0.7; }
            .hero::after { background: linear-gradient(45deg, rgba(0, 40, 85, 0.9) 30%, rgba(0, 40, 85, 0.6)); }
        }

        @media (max-width: 480px) {
            .navbar { flex-direction: column; gap: 1rem; padding: 1rem; }
            .nav-links { flex-wrap: wrap; justify-content: center; }
            .hero-content h1 { font-size: 2.2rem; }
            .hero-content p { font-size: 1.1rem; }
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar">
        <img src="images/MEGA_CITY.png" alt="Mega City Cab Logo" class="logo">
        <div class="nav-links">
            <a href="#home">Home</a>
            <a href="#services">Services</a>
            <a href="register.jsp">Register</a>
            <a href="login.jsp" class="cta-button1">Login</a>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero" id="home">
        <video autoplay muted loop  class="hero-video">
            <source src="images/7000174.mp4" type="video/mp4">
            <source src="images/7000174.webm" type="video/webm">
        </video>
        <div class="hero-content">
            <h1>Ride the City, Own the Journey!</h1>
            <p>Premium taxi services at your fingertips</p>
            <a href="login.jsp" class="cta-button">Book Now</a>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="services">
        <div class="features-grid">
            <div class="feature-card">
                <img src="images/new-york.jpg" alt="24/7 Service">
                <h3>24/7 Availability</h3>
                <p>Round-the-clock service for all your travel needs</p>
            </div>
            <div class="feature-card">
                <img src="images/young.jpg" alt="Professional Drivers">
                <h3>Professional Drivers</h3>
                <p>Licensed and experienced chauffeurs</p>
            </div>
            <div class="feature-card">
                <img src="images/full-length.jpg" alt="Easy Booking">
                <h3>Easy Booking</h3>
                <p>Instant booking through our mobile app</p>
            </div>
        </div>
    </section>

    <footer>
        <div class="social-links">
            <a href="#"><i class="fab fa-facebook"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-linkedin"></i></a>
        </div>
        <p>Contact us: info@megacitycab.com | +94 112 345 678</p>
        <p>© 2025 Mega City Cab. All rights reserved.</p>
    </footer>
</body>
</html>