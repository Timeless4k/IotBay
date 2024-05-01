package model;

public class payment {
    private int pID;
    private int pAmount;
    private String pMethod;
    private String pDate;
    private String pStatus;

    public int getpID() {
        return this.pID;
    }

    public int getpAmount() {
        return this.pAmount;
    }

    public String getpMethod() {
        return this.pMethod;
    }

    public String getpDate() {
        return this.pDate;
    }

    public String getpStatus() {
        return this.pStatus;
    }

    public void setpID(int id) {
        this.pID = id;
    }

    public void setpAmount(int amount) {
        this.pAmount = amount;
    }

    public void setpMethod(String method) {
        this.pMethod = method;
    }

    public void setpDate(String date) {
        this.pDate = date;
    }

    public void setpStatus(String status) {
        this.pStatus = status;
    }


}
