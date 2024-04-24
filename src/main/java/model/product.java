package model;

public class product {
    public int pID;
    public String pName;
    public String pStatus;
    public String pReleaseDate;
    public int pStockLevel;
    public String pDescription;
    public int pPrice;

    public static boolean inStock() {
        return true;
    };
    
}