<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.product" %>
<!DOCTYPE html>
<html lang="en">
    <head>
    
        <link rel="stylesheet" href="css/product.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="icon" type="image/png" href="images/Logo.webp">
        <title>IoTBay - Products</title>
    </head>

    <body>
    <% ArrayList<product> productlist = (ArrayList<product>) session.getAttribute("productList"); %>
        <div class="top-banner">
            Free Shipping Over $49 (AUD)
        </div>
    
        <header>
            <img src="images/Logo.png" alt="IoTBay Logo" class="logo">
            
            <nav class="navbar">
                <ul>
                    <li><a href="#about_us">About Us</a></li>
                    <li><a href="#FAQs">FAQs</a></li>
                    <li><a href="product.jsp">Catalog</a></li>
                </ul>
            </nav>
    
            <div class="nav-icons">
                <a href="orders.jsp">
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
                <input name="SearchQuery" id="SearchQuery" type="search">
                <button type="submit"> <img src="images/search.svg"> </button>
            </form>
        </div>

        <div class="product-container">
            <% 
            if(productlist != null) {
                for(product i : productlist) {
                    %> 
                        <div>
                            <img src="images/<%=i.getpID() %>.png" alt="<%=i.getName() %>">
                            <h1> <%= i.getName() %> </h1>
                            <p> <span>from</span> <%= i.getPrice() %> </p>
                            <p> <%= i.getStockLevel() %> in stock </p>
                        </div>
                    <%
                }
            } else {
                %> 
                <h2> No Products Found For Search </h2>
                <%
            }
            %>

        </div>

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
