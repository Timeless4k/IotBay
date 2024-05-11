<%@ page import="model.user" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Payment</title>
</head>
<body>

    <%-- Check if a user is logged in --%>
    <%
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser != null) {
    %>
    <div>User ID: <%= loggedInUser.getuID() %></div>
    <div>Email Address: <%= loggedInUser.getEmail() %></div>
    <%-- Additional debugging information can be added here --%>
    <%
        }
    %>
    <h1>Manage Payment</h1>
    <h2>Add New Payment Method</h2>
    <form action="CardServlet" method="post">
        Card Number: <input type="text" name="cardNumber" required><br>
        Card Holder Name: <input type="text" name="cardHolderName" required><br>
        Expiry Date: <input type="text" name="cardExpiry" required><br>
        CVV: <input type="text" name="cardCVV" required><br>
        <input type="submit" value="Add Payment Method">
    </form>

    <h2>Existing Payment Methods</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Card Number</th>
                <th>Card Holder Name</th>
                <th>Expiry Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Display existing payment methods here -->
            <%
                List<model.card> cardList = (List<model.card>) request.getAttribute("cardList");
                if (cardList != null) {
                    for (model.card card : cardList) { %>
                        <tr>
                            <td><%= card.getCardNumber() %></td>
                            <td><%= card.getCardHolderName() %></td>
                            <td><%= card.getCardExpiry() %></td>
                            <td>Edit | Delete</td>
                        </tr>
                    <% }
                }
            %>
        </tbody>
    </table>
</body>
</html>
