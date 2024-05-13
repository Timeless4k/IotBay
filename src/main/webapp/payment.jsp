<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.user, model.card" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Payment</title>
    <link rel="stylesheet" href="css/payment.css">
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <% user loggedInUser = (user) session.getAttribute("user"); %>
    <div class="container">
        <h1>Manage Payment</h1>
        
        <h2>Select a Payment Method</h2>
        <form action="PaymentServlet" method="post">
            <input type="hidden" name="action" value="processPayment">
            <select name="cardID" required>
                <c:forEach var="card" items="${cardList}">
                    <option value="${card.cardID}">${card.cardHolderName} - ${card.cardNumber} (Exp: ${card.cardExpiry})</option>
                </c:forEach>
            </select>
            <button type="submit">Pay Now</button>
        </form>

        <h2>Add New Payment Method</h2>
        <form action="CardServlet" method="post">
            <input type="hidden" name="action" value="create">
            Card Number: <input type="text" name="cardNumber" required><br>
            Card Holder Name: <input type="text" name="cardHolderName" required><br>
            Expiry Date: <input type="text" name="cardExpiry" required placeholder="MM/YYYY" pattern="\d{2}/\d{4}" title="Format MM/YYYY"><br>
            CVV: <input type="text" name="cardCVV" required pattern="\d{3}" title="3 digits"><br>
            <button type="submit">Add Payment Method</button>
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
                <c:forEach var="card" items="${cardList}">
                    <tr>
                        <td>${card.cardNumber}</td>
                        <td>${fn:escapeXml(card.cardHolderName)}</td>
                        <td>${card.cardExpiry}</td>
                        <td class="action-buttons">
                            <button onclick="openModal('${card.cardID}', '${card.cardNumber}', '${fn:escapeXml(card.cardHolderName)}', '${card.cardExpiry}', '${card.cardCVV}')">Edit</button>
                            <form method="post" action="CardServlet" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="cardId" value="${card.cardID}">
                                <input type="submit" value="Delete" onclick="return confirm('Are you sure?');">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="editCardModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <form id="updateCardForm" action="CardServlet" method="post">
                Card Number: <input type="text" id="modalCardNumber" name="cardNumber" required><br>
                Card Holder Name: <input type="text" id="modalCardHolderName" name="cardHolderName" required><br>
                Expiry Date: <input type="text" id="modalCardExpiry" name="cardExpiry" required><br>
                CVV: <input type="text" id="modalCardCVV" name="cardCVV" required><br>
                <input type="hidden" id="modalCardID" name="cardID">
                <input type="hidden" name="action" value="update">
                <button type="submit">Save Changes</button>
            </form>
        </div>
    </div>

    <script>
        function openModal(cardID, cardNumber, cardHolderName, cardExpiry, cardCVV) {
            // Fill the form with the current card data
            document.getElementById('modalCardNumber').value = cardNumber;
            document.getElementById('modalCardHolderName').value = cardHolderName;
            document.getElementById('modalCardExpiry').value = cardExpiry;
            document.getElementById('modalCardCVV').value = cardCVV;
            document.getElementById('modalCardID').value = cardID;
            document.getElementById('editCardModal').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('editCardModal').style.display = 'none';
        }

        document.getElementById('updateCardForm').addEventListener('submit', function(event) {
            event.preventDefault();

            var formData = new FormData(this);
            console.log("Form Data Prepared:", Object.fromEntries(formData)); // Debugging line

            fetch('CardServlet', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(html => {
                console.log("Server Response:", html); // Debugging line
                closeModal();
                location.reload(); // Reload the page to see the updates
            })
            .catch(err => {
                console.error('Error updating card:', err);
                alert('Error updating card. Please try again.');
            });
        });

    </script>

    <% if (loggedInUser != null) { %>
        <div>User ID: <%= loggedInUser.getuID() %></div>
        <div>Email Address: <%= loggedInUser.getEmail() %></div>
        <div>Debugging Info: User is logged in</div>
    <% } else { %>
        <div>User is not logged in</div>
    <% } %>
</body>
</html>
