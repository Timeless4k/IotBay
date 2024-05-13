<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register your account</title>
    <link rel="stylesheet" href="register.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">
    <div class="login-button">
        <a href="index.jsp" class="button-link">Back to Page</a>
    </div>
</header>

<div class="center-screen">
    <div class="form-container">
        <div class="header">
            <h1>Register Your Account</h1>
        </div>
        
        <form action="/RegisterServlet" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div>
                <label for="firstName">First name:</label>
                <input type="text" id="firstName" name="firstName" oninput="validateNameInput(this)" required>
            </div>
            <div>
                <label for="middleName">Middle name (optional):</label>
                <input type="text" id="middleName" name="middleName" oninput="validateNameInput(this)">
            </div>
            <div>
                <label for="lastName">Last name:</label>
                <input type="text" id="lastName" name="lastName" oninput="validateNameInput(this)" required>
            </div>
            <div>
                <label>Gender:</label>
                <input type="radio" id="male" name="gender" value="Male">
                <label for="male">Male</label>
                <input type="radio" id="female" name="gender" value="Female">
                <label for="female">Female</label>
                <input type="radio" id="other" name="gender" value="Other">
                <label for="other">Other</label>
            </div>
            <div>
                <label for="mobilePhone">Mobile phone:</label>
                <input type="tel" id="mobilePhone" name="mobilePhone" onkeypress="return isNumberKey(event)">
            </div>
            <div>
                <input type="submit" value="Register">
            </div>
        </form>
    </div>
</div>
<script>
    function validateNameInput(input) {
        input.value = input.value.replace(/[^a-zA-Z\s'-]/g, '');
    }

    function isNumberKey(evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        return !(charCode > 31 && (charCode < 48 || charCode > 57));
    }

    function goBack() {
        window.history.back();
    }
</script>
<!-- Additional footer elements here -->

</body>
</html>
