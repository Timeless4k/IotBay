package model;

public class accesslog {
    private int logID;
    private int userID;
    private String loginTime;
    private String logoutTime;

    public int getlogID() {
        return this.logID;
    }

    public int getuserID() {
        return this.userID;
    }

    public String getloginTime() {
        return this.loginTime;
    }

    public String getLogoutTime() {
        return this.logoutTime;
    }

    public void setlogID(int id) {
        this.logID = id;
    }

    public void setuserID(int id) {
        this.userID = id;
    }

    public void setloginTime(String time) {
        this.loginTime = time;
    }

    public void setlogoutTime(String time) {
        this.logoutTime = time;
    }
}
