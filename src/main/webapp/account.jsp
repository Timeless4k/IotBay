<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.user, model.payment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>General Settings</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="OrderHistoryServlet">Order History</a></li>
                <li><a href="shipmentDetails.jsp">Shipment Details</a></li>
                <li><a href="/account.jsp#access">Access Logs</a></li>
                <c:if test="${user.uType == 'Admin'}">
                    <li><a href=UserServlet?action=displayAll>User Management</a></li>
                </c:if>
                <c:if test="${user.uType == 'Employee'}">
                    <li><a href=productmanagement.jsp>Product Management</a></li>
                </c:if>
                <form action="logout" method="post">
                    <input type="submit" value="Logout">
                </form>
            </ul>
        </nav>
    </header>

    <div class="container">
        <div id="profile" class="content-section" style="display:none;">
            <h1>Profile</h1>
            <form action="UpdateProfileServlet" method="POST">
                <p><b>First Name:</b><br><input type="text" name="firstName" value="${user.firstName}" required></p>
                <p><b>Middle Name:</b><br><input type="text" name="middleName" value="${user.middleName}" required></p>
                <p><b>Last Name:</b><br><input type="text" name="lastName" value="${user.lastName}" required></p>
                <p><b>Gender:</b><br>
                    <select name="gender" required>
                        <option value="">Select</option>
                        <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${user.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </p>
                <p><b>Email:</b><br><input type="email" name="email" value="${user.email}" readonly></p>
                <p><b>Password:</b><br><input type="password" name="password" value="${user.password}"></p>
                <p><b>Mobile Phone:</b><br><input type="text" name="mobilePhone" value="${user.mobilePhone}" required></p>
                <div class="form-actions">
                    <button type="submit">Save Changes</button>
                    <button type="submit" formaction="DeleteProfileServlet">Delete Account</button>
                </div>
            </form>
        </div>

        <div id="access" class="content-section" style="display:none;">
            <h2>Access Logs</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>UserID</th>
                        <th>LogID</th>
                        <th>LoginTime</th>
                        <th>LogoutTime</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td><c:out value="${log.userID}"/></td>
                            <td><c:out value="${log.logID}"/></td>
                            <td><c:out value="${log.loginTime}"/></td>
                            <td><c:out value="${log.logoutTime}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4">Total Logs: ${logs.size()}</td>
                    </tr>
                </tfoot>
            </table>
            </div>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>

    <script>
        function showSection(sectionId) {
            document.querySelectorAll('.content-section').forEach(function(section) {
                section.style.display = 'none';
            });
            document.getElementById(sectionId).style.display = 'block';
        }
    </script>
</body>

<script>
    // Function to control the visibility of sections based on the hash
    function showSectionFromHash() {
        const hash = window.location.hash.replace('#', ''); // Remove the '#' from the hash
        if (hash) {
            // Hide all sections first
            document.querySelectorAll('.content-section').forEach(section => {
                section.style.display = 'none';
            });
            // Show the selected section based on the hash
            const sectionToShow = document.getElementById(hash);
            if (sectionToShow) {
                sectionToShow.style.display = 'block';
            } else {
                // Default to the first section if the hash is not recognized
                document.getElementById('profile').style.display = 'block';
            }
        }
    }

    // Listen for hash changes to update the section visibility
    window.addEventListener('hashchange', showSectionFromHash);

    // Also call the function on page load to check the initial hash
    window.addEventListener('load', showSectionFromHash);
</script>

</html>
