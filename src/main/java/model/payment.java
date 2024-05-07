package model;

public class payment {
    private long paymentID;
    private double amount;
    private String method; // e.g., Credit Card, PayPal
    private String date; // Consider using java.time.LocalDate
    private String status; // e.g., Pending, Completed

    // Constructors
    public payment() {}

    public payment(long paymentID, double amount, String method, String date, String status) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.method = method;
        this.date = date;
        this.status = status;
    }

    // Getters and setters
    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
