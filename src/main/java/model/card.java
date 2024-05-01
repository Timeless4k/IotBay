package model;

public class card {
    private int cID;
    private int cNumber;
    private String CardHolderName;
    private String CardExpiry;
    private int CCV;
    private int UserID;

    public int getcID() {
        return this.cID;
    }

    public int getcNumber() {
        return this.cNumber;
    }

    public String getCardHolderName() {
        return this.CardHolderName;
    }

    public String getCardExpiry() {
        return this.CardExpiry;
    }

    public int getCCV() {
        return this.CCV;
    }

    public int getUserID() {
        return this.UserID;
    }

    public void setcID(int id) {
        this.cID = id;
    }

    public void setcNumber(int cNumber) {
        this.cNumber = cNumber;
    }

    public void setCardHolderName(String name) {
        this.CardHolderName = name;
    }

    public void setCardExpiry(String exp) {
        this.CardExpiry = exp;
    }

    public void setUserID(int uID) {
        this.UserID = uID;
    }
}
