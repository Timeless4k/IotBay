package model;

public class payment {
    private long pID;
    private long pAmount;
    private String pMethod;
    private String pDate;
    private String pStatus;

    public long getpID() {
        return this.pID;
    }

    public long getpAmount() {
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

    public void setpID(long id) {
        this.pID = id;
    }

    public void setpAmount(long amount) {
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
