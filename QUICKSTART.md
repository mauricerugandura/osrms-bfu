# 🚀 OSRMS Quick Start Guide

## Step 1: Verify Prerequisites

Ensure you have the following installed:
```bash
java -version          # Should show Java 17 or higher
mvn -version          # Should show Maven 3.6 or higher
mysql --version       # Should show MySQL 8.0 or higher
```

If not installed, download from:
- **Java 17+**: https://www.oracle.com/java/technologies/downloads/
- **Maven**: https://maven.apache.org/download.cgi
- **MySQL**: https://www.mysql.com/downloads/

---

## Step 2: Setup MySQL Database

Open MySQL and create the database:

```sql
CREATE DATABASE osrms_db;
```

That's it! The application will auto-create tables using JPA's `ddl-auto=update` setting.

---

## Step 3: Navigate to Project Directory

```bash
cd c:\xampp\htdocs\best pr final
```

---

## Step 4: Build the Project

```bash
mvn clean package
```

This will download all dependencies and compile the project. First run may take 2-5 minutes.

---

## Step 5: Run the Application

```bash
mvn spring-boot:run
```

Or directly:
```bash
java -jar target/osrms-1.0.0.jar
```

**Wait for startup messages:**
```
========== DATABASE SEEDED SUCCESSFULLY ==========

📝 Test Credentials:
  Admin: admin@bfu.ac.rw / admin123
  Lecturer: lecturer1@bfu.ac.rw / pass123
  Student: student1@bfu.ac.rw / pass123
  Registrar: registrar@bfu.ac.rw / pass123
```

---

## Step 6: Access the Application

Open your browser and go to:
```
http://localhost:8080
```

You'll be redirected to the login page.

---

## Test the Application

### Test as Admin
1. Login: `admin@bfu.ac.rw` / `admin123`
2. Visit Admin Dashboard
3. Manage Users, Courses, and Results

### Test as Lecturer
1. Login: `lecturer1@bfu.ac.rw` / `pass123`
2. Visit Lecturer Dashboard
3. Enter/Update Grades for students

### Test as Student
1. Login: `student1@bfu.ac.rw` / `pass123`
2. View Dashboard with course statistics
3. Check Results and Transcript

### Test as Registrar
1. Login: `registrar@bfu.ac.rw` / `pass123`
2. Visit Registrar Dashboard
3. Review, Approve, and Publish student results

---

## 🔧 Configuration

If you need to customize settings, edit `application.properties`:

```properties
# Change port
server.port=8080

# Change database
spring.datasource.url=jdbc:mysql://localhost:3306/osrms_db
spring.datasource.username=root
spring.datasource.password=root

# Change SQL logging
spring.jpa.show-sql=true
```

---

## ⚠️ Common Issues & Solutions

### Issue: "Cannot get a connection, pool error"
**Solution:** 
1. Ensure MySQL is running
2. Check credentials in `application.properties`
3. Verify database `osrms_db` exists

**Check MySQL:**
```bash
mysql -u root -p
mysql> SHOW DATABASES;
```

### Issue: "Address already in use :8080"
**Solution:** 
Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Maven takes too long to download dependencies
**Solution:** First run downloads all dependencies. Wait patiently or check internet connection.

### Issue: Can't login after registration
**Solution:**
- New registrations default to STUDENT role
- Contact admin to change roles
- Verify email is correct

### Issue: Grades not showing up for student
**Solution:**
- Lecturer must submit grades (not just enter)
- Registrar must approve grades
- Admin/Registrar must publish results
- Check Status: PUBLISHED for visibility

---

## 📊 Project Structure Summary

```
osrms/
├── pom.xml                          # Maven configuration
├── README.md                        # Full documentation
├── QUICKSTART.md                    # This file
├── .gitignore                       # Git ignore rules
└── src/
    └── main/
        ├── java/com/bfu/osrms/
        │   ├── OsrmsApplication.java        # Main application class
        │   ├── DataSeeder.java              # Sample data initializer
        │   ├── controller/                  # HTTP handlers
        │   ├── model/                       # JPA entities
        │   ├── repository/                  # Data access
        │   ├── service/                     # Business logic
        │   └── security/                    # Spring Security config
        └── resources/
            ├── application.properties       # Configuration
            └── templates/                   # HTML templates
                ├── login.html
                ├── register.html
                ├── student/
                ├── lecturer/
                ├── admin/
                └── registrar/
```

---

## 📚 Learning Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **Spring Data JPA**: https://spring.io/projects/spring-data-jpa
- **Thymeleaf**: https://www.thymeleaf.org/
- **Bootstrap 5**: https://getbootstrap.com/docs/5.0/

---

## ✅ Verification Checklist

Before submitting your final project:

- [ ] Application starts without errors
- [ ] Login page loads correctly
- [ ] Can login with test credentials
- [ ] Admin can manage users and courses
- [ ] Lecturer can enter grades
- [ ] Registrar can review and approve results
- [ ] Student can view results and transcript
- [ ] Database auto-seeds sample data
- [ ] All role-based access controls work
- [ ] Logout functionality works
- [ ] UI displays properly with Bootstrap styling

---

## 🎓 Final Exam Project Checklist

- [x] Complete Spring Boot application with all required classes
- [x] MVC Architecture properly implemented
- [x] Role-based security (ADMIN, LECTURER, STUDENT, REGISTRAR)
- [x] Database with all required tables and relationships
- [x] All CRUD operations working
- [x] Thymeleaf templates for all pages
- [x] Bootstrap UI styling throughout
- [x] Sample data seeding on startup
- [x] Grade calculation (70-100=A, 60-69=B, etc.)
- [x] Result workflow (PENDING → APPROVED → PUBLISHED)
- [x] GPA calculation for transcripts
- [x] Session management and user authentication
- [x] Complete documentation and README
- [x] Clean, well-commented code

---

## 📞 Support

If you encounter any issues:

1. Check the console output for error messages
2. Review logs in `target/` directory if present
3. Verify all prerequisites are installed correctly
4. Ensure MySQL database exists and is running
5. Check `application.properties` for correct credentials

---

