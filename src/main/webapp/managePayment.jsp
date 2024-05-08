<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Payments</title>
</head>
<body>
    <h1>Manage Payments</h1>

    <!-- Form for Creating and Updating Payments -->
    <form action="PaymentServlet" method="post">
        <input type="hidden" name="action" value="create"> <!-- Change to 'update' as needed -->
        <div>
            <label>Payment ID:</label>
            <input type="text" name="paymentID" required>
        </div>
        <div>
            <label>Amount:</label>
            <input type="text" name="amount" required>
        </div>
        <div>
            <label>Payment Method:</label>
            <select name="method">
                <option value="Credit Card">Credit Card</option>
                <option value="Debit Card">Debit Card</option>
                <option value="Payment on Delivery">Payment on Delivery</option>
            </select>
        </div>
        <div>
            <label>Date (YYYY-MM-DD):</label>
            <input type="date" name="date" required>
        </div>
        <div>
            <label>Status:</label>
            <select name="status">
                <option value="Approved">Approved</option>
                <option value="Failed">Failed</option>
                <option value="Pending">Pending</option>
            </select>
        </div>
        <button type="submit">Submit</button>
    </form>

    <!-- Form for Reading and Deleting Payments -->
    <form action="PaymentServlet" method="post">
        <input type="hidden" name="action" value="read"> <!-- Change to 'delete' as needed -->
        <div>
            <label>Payment ID:</label>
            <input type="text" name="paymentID" required>
        </div>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
