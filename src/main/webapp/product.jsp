<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css\style.css" type="text/css">
        <link rel="stylesheet" href="css\product.css" type="text/css">
        <link rel="icon" type="image/png" href="images/Logo.webp">
        <title>IoTBay - Products</title>
    </head>

    <body>
        <div class="top-banner">
            Free Shipping Over $49 (AUD)
        </div>
    
        <header>
            <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">
            
            <nav class="navbar">
                <ul>
                    <li><a href="#about_us">About Us</a></li>
                    <li><a href="#FAQs">FAQs</a></li>
                    <li><a href="product.jsp">Catalog</a></li>
                </ul>
            </nav>
    
            <div class="nav-icons">
                <a href="cart.jsp">
                    <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
                </a>
                <div class="account-menu">
                    <img src="images/user.png" alt="Account" class="account-icon" onmouseover="showWelcomeMessage()" onmouseout="hideWelcomeMessage()">
                    <div class="dropdown-content">
                        <div >
                            ${user.firstName} ${user.lastName}
                        </div>
                        <a href="account.jsp">Account</a>
                        <a href="logout.jsp">Logout</a>
                    </div>
                </div>
            </div>
        </header>

        <div class="prodsearchbar">
            <form method="get" action="ProductNameSearchServlet">
                <select name="SearchType" id="SearchType">
                    <option value="ProductName">Name</option>
                    <option value="ProductType">Type</option>
                </select>
                <input id="SearchQuery" type="search">
                <button type="submit"> <img src="images/search.svg"> </button>
            </form>

        </div>


        <% 
        if(productlist != null) {
            
        } else {
            %>  <%
        }
        %>
<!--        
        <div class="product-container">
            <div>
                <img src="images/nodeMCU.png" alt="nodeMCU">
                <h1> Node MCU </h1>
                <p> <span>from</span> $58.20 </p>
                <p> 50 in stock </p>
            </div>

        </div> -->

        <footer>
            <p>&copy; 2024 IoTBay. All rights reserved.</p>
            <p class="attribution">
                Icons created by <a href="https://www.flaticon.com/free-icons/user" title="user icons" target="_blank">Freepik - Flaticon</a>
                <span>|</span>
                <a href="https://www.flaticon.com/free-icons/basket" title="basket icons" target="_blank">Basket icons created by Karacis - Flaticon</a>
            </p>
        </footer>

    </body>
</html>