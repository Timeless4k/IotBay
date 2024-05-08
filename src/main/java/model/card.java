package model;

public class card {
    private long cID;
    private long cNumber;
    private String CardHolderName;
    private String CardExpiry;
    private int CCV;
    private long UserID;

    public long getcID() {
        return this.cID;
    }

    public long getcNumber() {
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

    public long getUserID() {
        return this.UserID;
    }

    public void setcID(long id) {
        this.cID = id;
    }

    public void setcNumber(long cNumber) {
        this.cNumber = cNumber;
    }

    public void setCardHolderName(String name) {
        this.CardHolderName = name;
    }

    public void setCardExpiry(String exp) {
        this.CardExpiry = exp;
    }

    public void setUserID(long uID) {
        this.UserID = uID;
    }

    public void setCCV(int ccv) {
        this.CCV = ccv;
    }
}
