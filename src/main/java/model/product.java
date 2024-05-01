package model;

public class product {
    public long pID;
    public String pName;
    public String pStatus;
    public String pReleaseDate;
    public long pStockLevel;
    public String pDescription;
    public String pType;
    public long pPrice;
    

    public boolean inStock() {
        if(pStockLevel >= 1) {
            return true;
        }
        return false;
    }

    public long getStockLevel() {
        return pStockLevel;
    }

    public String getStatus() {
        return pStatus;
    }

    public void setStatus(String stat) {
        this.pStatus = stat;
    }

    public void setpID(long var) {
        this.pID = var;
    }

    public long getpID() {
        return this.pID;
    }

    public void reduceStock(long quant) {
        this.pStockLevel = this.pStockLevel-quant;
    }

    public void increaseStock(long quant) {
        this.pStockLevel = this.pStockLevel+quant;
    }

    public void setStock(long quant) {
        this.pStockLevel = quant;
    }

    public void setPrice(long amount) {
        this.pPrice = amount;
    }

    public long getPrice() {
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