<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Page</title>
    <link rel="stylesheet" href="css/account.css">
</head>
<body>    
    <div>
        <button class="back-button" onclick="goBack()"> &lt; Back</button>

        <h1>My Account</h1>

        <br> <br> 

        <div class="row">
            <div class="column-left">
                <div class="sidebar">
                    <a href="#profile">
                        <img src="images/profile.png" alt="Profile Icon" width="25" height="25" class="profile-icon">
                        <p class="profile-option">Profile</p>   
                    </a>                          
                    <hr>
                    <a href="#orders">
                        <img src="images/cart.png" alt="Cart Icon" width="35" height="35">
                        <p class="order-option">Orders</p>
                    </a>  
                    <hr>
                    <a href="#payments">
                        <img src="images/payment.png" alt="Payment Icon" width="56" height="56" class="payment-icon">
                        <p class="payment-option">Payments</p>
                    </a>  
                    <hr>
                    <a href="#settings">
                        <img src="images/settings.png" alt="Settings Icon" width="29" height="29" class="settings-icon">
                        <p class="settings-option">Settings</p>
                    </a>  
                    <hr>
                    <a href="#logout">
                        <img src="images/logout.png" alt="Logout Icon" width="45" height="45" class="logout-icon">
                        <p class="logout-option">Logout</p>
                    </a>  
                </div>
            </div>

            <div class="column-right">
                <div class="main-content">
                    <form action="UpdateProfileServlet" method="POST">
                        <div id="profile">
                            <u><h1>Profile</h1></u>
                            <p><b>First Name:</b><br><input type="text" name="firstName" value="${user.firstName}"></p>
                            <p><b>Middle Name:</b><br><input type="text" name="middleName" value="${user.middleName}"></p>
                            <p><b>Last Name:</b><br><input type="text" name="lastName" value="${user.lastName}"></p>
                            <p><b>Email:</b><br><input type="email" name="email" value="${user.email}" readonly></p>
                            <p><b>Password:</b><br><input type="password" name="password" value="${user.password}"></p>
                            <p><b>Birth Date:</b><br><input type="date" name="birthDate" value="${user.birthDate}"></p>
                            <p><b>Mobile Phone:</b><br><input type="text" name="mobilePhone" value="${user.mobilePhone}"></p>
                            <button type="submit">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <br> <br> <br> <br> <br> <br>

        <footer>
            <p>&copy; 2024 IoTBay. All rights reserved.</p>
        </footer>
    </div>

    <script>
        function goBack() {
            window.location.href = "main.jsp"; // Redirect to index.jsp
        }
    </script>

    <script>
        function togglePassword() {
            var passwordField = document.getElementById("passwordField");
            var button = event.target;
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                button.textContent = 'Hide Password';
            } else {
                passwordField.type = 'password';
                button.textContent = 'Show Password';
            }
        }
    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Get all sidebar links
            const links = document.querySelectorAll('.sidebar a');

            // Add click event listener to each link
            links.forEach(function(link) {
                link.addEventListener('click', function(event) {
                    // Prevent default link behavior
                    event.preventDefault();
                    
                    // Get the target section ID from the href attribute
                    const targetId = this.getAttribute('href').substring(1);
                    
                    // Hide all sections
                    document.querySelectorAll('.main-content > div').forEach(function(section) {
                        section.style.display = 'none';
                    });
                    
                    // Display the target section
                    document.getElementById(targetId).style.display = 'block';
                });
            });
        });
    </script>
</body>
</html>
