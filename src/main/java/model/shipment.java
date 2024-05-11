// package model;

// public class shipment{
//     private long sID;
//     private String sAddr;
//     private String sType;
//     private String sTrackNumber;

//     public long getsID() {
//         return this.sID;
//     }

//     public String getsAddr() {
//         return this.sAddr;
//     }

//     public String getsType() {
//         return this.sType;
//     }

//     public String getsTrackNumber() {
//         return this.sTrackNumber;
//     }

//     public void setsID(long id) {
//         this.sID = id;
//     }

//     public void setsAddr(String addr) {
//         this.sAddr = addr;
//     }

//     public void setsType(String type) {
//         this.sType = type;
//     }

//     public void setsTrackNumber(String tracknumber) {
//         this.sTrackNumber = tracknumber;
//     }
// }





package model;

public class Shipment {
    private String shipmentAddress;
    private String shipmentContactInfoEmail;
    private String shipmentContactInfoPhoneNumber;
    private String shipmentDate;
    private String shipmentMethod;
    // private String userID; // Assuming you also need a userID for the shipment


    public Shipment(String shipmentAddress, String shipmentContactInfoEmail, String shipmentContactInfoPhoneNumber, String shipmentDate, String shipmentMethod) {
        this.shipmentAddress = shipmentAddress;
        this.shipmentContactInfoEmail = shipmentContactInfoEmail;
        this.shipmentContactInfoPhoneNumber = shipmentContactInfoPhoneNumber;
        this.shipmentDate = shipmentDate;
        this.shipmentMethod = shipmentMethod;
    }


    // Getters and setters for all fields

    public String getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getShipmentContactInfoEmail() {
        return shipmentContactInfoEmail;
    }

    public void setShipmentContactInfoEmail(String shipmentContactInfoEmail) {
        this.shipmentContactInfoEmail = shipmentContactInfoEmail;
    }

    public String getShipmentContactInfoPhoneNumber() {
        return shipmentContactInfoPhoneNumber;
    }

    public void setShipmentContactInfoPhoneNumber(String shipmentContactInfoPhoneNumber) {
        this.shipmentContactInfoPhoneNumber = shipmentContactInfoPhoneNumber;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    // public String getUserID() {
    //     return userID;
    // }

    // public void setUserID(String userID) {
    //     this.userID = userID;
    // }
}
