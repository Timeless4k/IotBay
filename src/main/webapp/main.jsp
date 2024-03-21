<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Main</title>
    <link rel="stylesheet" href="css/style.css">
    <!-- Add any other scripts or stylesheets you need -->
</head>
<body>

    <header>
        <h1><a href="index.jsp">IoTBay</a></h1>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="product.jsp">Products</a></li>
                <li><a href="main.jsp">Account</a></li>
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <h2>Welcome, [User Name]</h2>
        <!-- Main content for the user's account/dashboard page -->
        <section>
            <h3>Your Details</h3>
            <p>[User specific details here]</p>
            <!-- Additional user details or actions -->
        </section>

        <section>
            <h3>Your Orders</h3>
            <!-- A list of the user's past orders -->
        </section>

        <!-- Any additional sections for user account management -->
        <section>
            <h3>Account Settings</h3>
            <!-- Links or forms for updating user profile, changing password, etc. -->
        </section>
    </main>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>

</body>
</html>
