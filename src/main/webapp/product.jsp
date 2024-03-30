<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
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
                        <a href="settings.jsp">Settings</a>
                        <a href="logout.jsp">Logout</a>
                    </div>
                </div>
            </div>
        </header>
       
        <div class="product-container">
            <div class="product">
                <img src="images/nodeMCU.png" alt="nodeMCU">
                <h1> Node MCU </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/raspberryPi.png">
                <h1> Node MCU </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/thermostats.png">
                <h1> Smart Thermostat </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/smartWatch.png">
                <h1> Smart Watch </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/smartBulb.png">
                <h1> SmartBulb </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/adxl345.jpg">
                <h1> ADXL345 </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/lora-e5.jpg">
                <h1> LoRa Module </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/nucleo-f446re.jpg">
                <h1> STM32 Development Board </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/arduino-uno-r3.jpg">
                <h1> Arduino Uno R3 </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>
            <div class="product">
                <img src="images/Lipo-3.7v-1100mAh.jpg">
                <h1> Lithium Battery </h1>
                <p> $58.20 </p>
                <p> 50 in stock </p>
            </div>

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