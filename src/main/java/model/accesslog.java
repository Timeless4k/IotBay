package model;

public class accesslog {
    private long logID;
    private long userID;
    private String loginTime;
    private String logoutTime;

    public long getlogID() {
        return this.logID;
    }

    public long getuserID() {
        return this.userID;
    }

    public String getloginTime() {
        return this.loginTime;
    }

    public String getLogoutTime() {
        return this.logoutTime;
    }

    public void setlogID(long id) {
        this.logID = id;
    }

    public void setuserID(long id) {
        this.userID = id;
    }

    public void setloginTime(String time) {
        this.loginTime = time;
    }

    public void setlogoutTime(String time) {
        this.logoutTime = time;
    }
}
