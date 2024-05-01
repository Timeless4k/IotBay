package model;
public class shipment{
    private int sID;
    private String sAddr;
    private String sType;
    private String sTrackNumber;

    public int getsID() {
        return this.sID;
    }

    public String getsAddr() {
        return this.sAddr;
    }

    public String getsType() {
        return this.sType;
    }

    public String getsTrackNumber() {
        return this.sTrackNumber;
    }

    public void setsID(int id) {
        this.sID = id;
    }

    public void setsAddr(String addr) {
        this.sAddr = addr;
    }

    public void setsType(String type) {
        this.sType = type;
    }

    public void setsTrackNumber(String tracknumber) {
        this.sTrackNumber = tracknumber;
    }
}
