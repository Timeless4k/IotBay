<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.user, model.payment"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">


    <!-- Modal Styles -->
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #007bff;
            width: 80%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>  
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="OrderHistoryServlet">Order History</a></li>
                <li><a href="/account.jsp#access">Access Logs</a></li>
                <c:if test="${user.uType == 'Admin'}">
                    <li><a href=usermanagement.jsp>User Management</a></li>
                </c:if>
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>
    <div class="container">
        <h1>User Management</h1>
        <!-- Search Form -->
        <form id="searchForm" action="UserSearchServlet" method="post">
            <input type="text" name="fullName" placeholder="Enter full name">
            <input type="text" name="phoneNumber" placeholder="Enter phone number">
            <button type="submit">Search</button>
            <button type="button" onclick="clearForm()">Clear</button> <!-- Use type="button" to prevent form submission -->
        </form>
    </div>
   
    <div class="container">
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
    </div>
    <div class="container">
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
                            <button onclick="openModal('${user.uID}', '${user.firstName}', '${user.middleName}', '${user.lastName}', '${user.uType}', '${user.email}', '${user.mobilePhone}', '${user.gender}', '${user.creationDate}')">Edit</button>
                            <a href="UserServlet?action=delete&email=${user.email}">Delete</a>
                        </td>
                    </tr>      
                </c:forEach>
            </tbody>
        </table>
    </div>


    <!-- Modal for editing user -->
    <div id="editUserModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <form id="editUserForm" action="UserServlet" method="post">
                <input type="hidden" id="userId" name="userId">
                First Name: <input type="text" id="firstName" name="firstName" required><br>
                Middle Name: <input type="text" id="middleName" name="middleName"><br>
                Last Name: <input type="text" id="lastName" name="lastName" required><br>
                Email: <input type="email" id="email" name="email" required><br>
                Phone: <input type="text" id="phone" name="phone" required><br>
                Gender:
                <select id="gender" name="gender" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select><br>
                Creation Date: <input type="text" id="creationDate" name="creationDate" readonly><br> <!-- Display creation date here -->
                User Type:
                <select id="userType" name="userType" required>
                    <option value="Employee">Employee</option>
                    <option value="Customer">Customer</option>
                    <option value="Admin">Admin</option>
                </select><br>
                <input type="hidden" name="action" value="update">
                <button type="submit">Save Changes</button>
            </form>
        </div>
    </div>


    <!-- Script to handle modal functionality -->
    <script>
        function openModal(userId, firstName, middleName, lastName, userType, email, phone, gender, creationDate) {
            document.getElementById('userId').value = userId;
            document.getElementById('firstName').value = firstName;
            document.getElementById('middleName').value = middleName;
            document.getElementById('lastName').value = lastName;
            document.getElementById('email').value = email;
            document.getElementById('phone').value = phone;
            document.getElementById('gender').value = gender;
            document.getElementById('creationDate').value = creationDate;
            document.getElementById('editUserModal').style.display = 'block';
        }


        function closeModal() {
            document.getElementById('editUserModal').style.display = 'none';
        }


        function clearForm() {
            // Reset the form
            document.getElementById('searchForm').reset();
            // Redirect to UserServlet?action=displayAll
            window.location.href = "UserServlet?action=displayAll";
        }
    </script>


    <script>
        // Check if the users are loaded, if not redirect to load them
        if (!${not empty users}) {
            window.location.href = "UserServlet?action=displayAll";
        }
    </script>

</body>
</html>
