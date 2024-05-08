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
            First Name: <input type="text" name="firstName"><br>
            Middle Name: <input type="text" name="middleName"><br>
            Last Name: <input type="text" name="lastName"><br>
            Email: <input type="email" name="email"><br>
            Phone: <input type="text" name="phone"><br>
            Gender: <input type="text" name="gender"><br>
            User Type: <input type="text" name="userType"><br>
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
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
