package model;

public class product {
    public int pID;
    public String pName;
    public String pStatus;
    public String pReleaseDate;
    public int pStockLevel;
    public String pDescription;
    public String pType;
    public int pPrice;
    

    public boolean inStock() {
        if(pStockLevel >= 1) {
            return true;
        }
        return false;
    }

    public int getStockLevel() {
        return pStockLevel;
    }

    public String getStatus() {
        return pStatus;
    }

    public void setStatus(String stat) {
        this.pStatus = stat;
    }

    public void setpID(int var) {
        this.pID = var;
    }

    public int getpID() {
        return this.pID;
    }

    public void reduceStock(int quant) {
        this.pStockLevel = this.pStockLevel-quant;
    }

    public void increaseStock(int quant) {
        this.pStockLevel = this.pStockLevel+quant;
    }

    public void setStock(int quant) {
        this.pStockLevel = quant;
    }

    public void setPrice(int amount) {
        this.pPrice = amount;
    }

    public int getPrice() {
        return this.pPrice;
    }

    public String getName() {
        return this.pName;
    }

    public void setName(String name) {
        this.pName = name;
    }

    public String getReleaseDate() {
        return this.pReleaseDate;
    }

    public void setReleaseDate(String Date) {
        this.pReleaseDate = Date;
    }

    public String getDescription() {
        return this.pReleaseDate;
    }

    public void setDescription(String Desc) {
        this.pDescription = Desc;
    }

    public String getType() {
        return this.pType;
    }

    public void setType(String type) {
        this.pDescription = type;
    }
}