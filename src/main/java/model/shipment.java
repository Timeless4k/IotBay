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

public class shipment {
    private String shipmentID; // Assuming you also need a shipmentID for the shipment
    private String shipmentAddress;
    // private String shipmentContactInfoEmail;
    // private String shipmentContactInfoPhoneNumber;
    private String shipmentDate;
    private String shipmentMethod;


    // public Shipment(String shipmentAddress, String shipmentContactInfoEmail, String shipmentContactInfoPhoneNumber, String shipmentDate, String shipmentMethod) {
    //     this.shipmentAddress = shipmentAddress;
    //     this.shipmentContactInfoEmail = shipmentContactInfoEmail;
    //     this.shipmentContactInfoPhoneNumber = shipmentContactInfoPhoneNumber;
    //     this.shipmentDate = shipmentDate;
    //     this.shipmentMethod = shipmentMethod;
    // }


    // public shipment(String shipmentID, String shipmentAddress, String shipmentDate, String shipmentMethod) {
    //     this.shipmentID = shipmentID;
    //     this.shipmentAddress = shipmentAddress;
    //     this.shipmentDate = shipmentDate;
    //     this.shipmentMethod = shipmentMethod;
    // }


<<<<<<< Updated upstream
    public shipment(String shipmentID, String shipmentAddress, String shipmentDate, String shipmentMethod) {
        this.shipmentID = shipmentID;
=======
    public shipment(String shipmentAddress, String shipmentDate, String shipmentMethod) {
>>>>>>> Stashed changes
        this.shipmentAddress = shipmentAddress;
        this.shipmentDate = shipmentDate;
        this.shipmentMethod = shipmentMethod;
    }


    // Getters and setters for all fields

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
    }

    public String getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    // public String getShipmentContactInfoEmail() {
    //     return shipmentContactInfoEmail;
    // }

    // public void setShipmentContactInfoEmail(String shipmentContactInfoEmail) {
    //     this.shipmentContactInfoEmail = shipmentContactInfoEmail;
    // }

    // public String getShipmentContactInfoPhoneNumber() {
    //     return shipmentContactInfoPhoneNumber;
    // }

    // public void setShipmentContactInfoPhoneNumber(String shipmentContactInfoPhoneNumber) {
    //     this.shipmentContactInfoPhoneNumber = shipmentContactInfoPhoneNumber;
    // }

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
}
