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

        <div class="row">
            <div class="column">
                <div class="sidebar">
                    <ul>
                        <li><a href="#profile">Profile</a></li>
                        <li><a href="#orders">Orders</a></li>
                        <li><a href="#payments">Payments</a></li>
                        <li><a href="#settings">Settings</a></li>
                        <li><a href="#logout">Logout</a></li>
                    </ul>
                </div>
            </div>

            <div class="column">
                <div class="main-content">
                    <div id="profile" style="display: none;">
                        <h2>Profile</h2>
                        <!-- Profile content goes here -->
                    </div>
                    <div id="orders" style="display: none;">
                        <h2>Orders</h2>
                        <!-- Orders content goes here -->
                    </div>
                    <div id="payments" style="display: none;">
                        <h2>Payments</h2>
                        <!-- Payments content goes here -->
                    </div>
                    <div id="settings" style="display: none;">
                        <h2>Settings</h2>
                        <!-- Settings content goes here -->
                    </div>
                    <div id="logout" style="display: none;">
                        <h2>Logout</h2>
                        <!-- Logout content goes here -->
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <p>&copy; 2024 IoTBay. All rights reserved.</p>
        </footer>
    </div>

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