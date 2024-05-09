<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="css/account.css">
</head>
<body>  
    <div class="container">
        <h1>User Management</h1>
        <a href="UserServlet?action=displayAll">Refresh User List</a>




        <!-- User Creation Form -->
        <h2>Add New User</h2>
        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="create">
            First Name: <input type="text" name="firstName" required><br>
            Middle Name: <input type="text" name="middleName"><br>
            Last Name: <input type="text" name="lastName" required><br>
            Email: <input type="email" name="email" required><br>
            Password: <input type="password" name="password" required><br>
            Phone: <input type="text" name="phone" required><br>
            Gender:
            <select name="gender" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
            </select><br>
            User Type:
            <select name="userType" required>
                <option value="Employee">Employee</option>
                <option value="Customer">Customer</option>
                <option value="Admin">Admin</option>
            </select><br>
            <button type="submit">Add User</button>
        </form>


        <h2>User List</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Middle Name</th>
                    <th>Last Name</th>
                    <th>User Type</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>Creation Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.uID}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.middleName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.uType}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.mobilePhone}"/></td>
                        <td><c:out value="${user.gender}"/></td>
                        <td><c:out value="${user.creationDate}"/></td>
                        <td>
                            <a href="UserServlet?action=delete&email=${user.email}">Delete</a>
                            <a href="UserServlet?action=update&email=${user.email}">update</a> <!-- Add link for edit -->
                        </td>
                    </tr>      
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
<script>
    // Check if the users are loaded, if not redirect to load them
    if (!${not empty users}) {
        window.location.href = "UserServlet?action=displayAll";
    }
</script>
</html>





