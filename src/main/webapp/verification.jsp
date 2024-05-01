<%@ page import="model.*"%>
<!DOCTYPE html>
<html>

<header>
    <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">
    <div class="login-button">
        <a href="index.jsp" class="button-link">Back to Page</a>
    </div>
    <link rel="stylesheet" type="text/css" href="register.css">
    <link rel="stylesheet" href="css/style.css">
</header>

<div class="center-screen">
    <div class="form-container">
        <div class="header">
            <h1>Enter Verification Code</h1>
        </div>
        
        <form action="verifyCode" method="post">
            <div>
                <label for="verificationCode">Verification Code:</label>
                <input type="text" id="verificationCode" name="verificationCode" required>
            </div>
            <div>
                <input type="submit" value="Verify Code">
            </div>
        </form>
        <hr>
        <div style="text-align: center; margin-top: 20px;">
            <p>If you didn't receive a code? <a href="resendCode.jsp">Resend code.</a></p>
        </div>
    </div>
</div>

</html>