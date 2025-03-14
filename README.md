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
src/main/java/com.MegaCity_Cab
│── dao/         # Data Access Objects (DAO)
│── filters/     # Authentication filters
│── model/       # Entity classes
│── servlets/    # Servlets for handling HTTP requests
│── utils/       # Utility classes (DB connections, security)
│
src/main/webapp/
│── WEB-INF/     # Configuration files
│── admin/       # Admin pages
│── css, js, images/ # Static assets
│── login.jsp, register.jsp, index.jsp  # User-facing pages
```

## 🔧 Tech Stack

- **Backend**: Java EE (Servlets, JDBC)
- **Frontend**: JSP, HTML, CSS, JavaScript
- **Database**: MySQL (or any RDBMS)
- **Build Tool**: Maven
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
