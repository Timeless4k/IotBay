package model;
import java.util.ArrayList;
import model.product;

public class order {
    // contains array list of products
    private long oID;
    private String oDate;
    private String oStatus;
    private String oDeliveryStatus;
    // private ArrayList<product> orderitems;

    public long getID() {
        return this.oID;
    }

    public String getDate() {
        return this.oDate;
    }

    public String getStatus() {
        return this.oStatus;
    }

    public String getDeliveryStatus() {
        return this.oDeliveryStatus;
    }

    public void setID(long id) {
        this.oID = id;
    }

    public void setDate(String date) {
        this.oDate = date;
    }

    public void setStatus(String status) {
        this.oStatus = status;
    }

    public void setDeliveryStatus(String dstatus) {
        this.oStatus = dstatus;
    }

}
