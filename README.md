# 🚖 MegaCity_Cab

MegaCity_Cab is a Java EE-based web application designed to manage cab services, including ride booking, user management, and authentication.

## 📌 Features

- **User Management**: Registration, login, and dashboard for users and riders.
- **Admin Panel**: Manage users, assign rides, and oversee operations.
- **Ride Management**: Book, complete, and delete rides.
- **Authentication & Security**: Secure login system with role-based access.
- **Database Integration**: Uses JDBC for data handling.

## 🏗️ Project Structure

```
MegaCity_Cab/  
├── Deployment Descriptor: MegaCity_Cab  
├── src/  
│   ├── main/  
│   │   ├── java/  
│   │   │   ├── com.MegaCity_Cab.dao/          # Data Access Object classes  
│   │   │   ├── com.MegaCity_Cab.filters/      # Servlet filters  
│   │   │   ├── com.MegaCity_Cab.model/        # Data/model classes 
│   │   │   ├── com.MegaCity_Cab.servlets/     # Servlet controllers  
│   │   │   └── com.MegaCity_Cab.utils/        # Utility/helper classes  
│   │   └── webapp/  
│   │       ├── css/                           # CSS stylesheets  
│   │       ├── images/                        # Image assets  
│   │       ├── js/                            # JavaScript files  
│   │       ├── META-INF/                      # Metadata 
│   │       ├── WEB-INF/                       # Web configuration files 
│   │       ├── admin.jsp                      # Admin dashboard view  
│   │       ├── adminNav.jsp                   # Admin navigation partial  
│   │       ├── adminRiders.jsp                # Admin riders management view  
│   │       ├── adminUserMng.jsp               # Admin user management view  
│   │       ├── editUser.jsp                   # Edit user form  
│   │       ├── index.jsp                      # Homepage  
│   │       ├── login.jsp                      # Login page  
│   │       ├── register.jsp                   # User registration page  
│   │       ├── rider.jsp                      # Rider-specific view  
│   │       └── user.jsp                       # General user profile view  
│   └── test/  
│       └── java/                              # Test classes 
├── build/                                     # Compiled classes and build artifacts  
└── Libraries/                                 # External dependencies   
```

## 🔧 Tech Stack

- **Backend**: Java EE (Servlets, JDBC)
- **Frontend**: JSP, HTML, CSS, JavaScript
- **Database**: MySQL (mysql)
- **Build Tool**: eclipse dynamic web app
- **Deployment**: Apache Tomcat

## 🚀 Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/MegaCity_Cab.git
   cd MegaCity_Cab
   ```

2. Set up the database:
   - Import the provided `.sql` file into MySQL.
   - Configure `DBUtil.java` with your database credentials.

3. Run on Tomcat:
   - Deploy the project on Apache Tomcat.
   - Access the application via `http://localhost:8080/MegaCity_Cab`.

## 📌 Contribution

Feel free to fork this repository, create a new branch, and submit a pull request with improvements.

## 📄 License

This project is open-source and available under the MIT License.

---

Let me know if you want any modifications! 🚀
