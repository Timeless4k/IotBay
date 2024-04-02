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
                    <a href="#profile">Profile</a>
                    <hr>
                    <a href="#orders">Orders</a>
                    <hr>
                    <a href="#payments">Payments</a>
                    <hr>
                    <a href="#settings">Settings</a>
                    <hr>
                    <a href="#logout">Logout</a>
                </div>
            </div>

            <div class="column-right">
                <div class="main-content">
                    <div id="profile">
                        <u><h1>Profile</h1></u>
                        <br>
                        <p><b>First Name:</b><br>${user.firstName}</p>
                        <p><b>Middle Name:</b><br>${user.middleName}</p>
                        <p><b>Last Name:</b><br>${user.lastName}</p>
                        <p><b>Email:</b><br>${user.email}</p>
                        <p><b>Password:</b><br>
                            <span id="passwordField">${user.password.replaceAll(".", "*")}</span>
                            <button onclick="togglePassword()">Show Password</button>
                        </p>                                                                 
                        <p><b>Birth Date:</b><br>${user.birthDate}</p>
                        <p><b>Mobile Phone:</b><br>${user.mobilePhone}</p>

                        <!-- Profile content goes here -->
                    </div>
                    <div id="orders" style="display: none;">
                        <u><h1>Orders</h1></u>
                        <br><p>Orders content goes here...</p>

                        <!-- Orders content goes here -->
                    </div>
                    <div id="payments" style="display: none;">
                        <u><h1>Payments</h1></u>
                        <br><p>Payments content goes here...</p>

                        <!-- Payments content goes here -->
                    </div>
                    <div id="settings" style="display: none;">
                        <u><h1>Settings</h1></u>
                        <br><p>Settings content goes here...</p>

                        <!-- Settings content goes here -->
                    </div>
                    <div id="logout" style="display: none;">
                        <u><h1>Logout</h1></u>
                        <br><p>Logout content goes here...</p>
                        
                        <!-- Logout content goes here -->
                    </div>
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

            if (passwordField.textContent === "${user.password}") {
                passwordField.textContent = "*".repeat("${user.password}".length); // Replace text with dots
                button.textContent = "Show Password";
            } else {
                passwordField.textContent = "${user.password}"; // Show the actual password
                button.textContent = "Hide Password";
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
