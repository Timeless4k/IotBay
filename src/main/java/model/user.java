package model;

import java.io.Serializable;
import java.util.List;

public class user implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String mobilePhone;
    private String gender;
    private String creationDate;
    private String uType;
    private long uID;
    private boolean activationStatus; // New field for activation status

    public user() {}

    public user(long uid, String emai, String pass, String fName, String mname, String lName, String bday, String mPhone, String g, String cd, String Type, boolean activationStatus) {
        this.uID = uid;
        this.email = emai;
        this.password = pass;
        this.firstName = fName;
        this.middleName = mname;
        this.lastName = lName;
        this.birthDate = bday;
        this.mobilePhone = mPhone;
        this.gender = g;
        this.creationDate = cd;
        this.uType = Type;
        this.activationStatus = activationStatus;
    }
    public boolean getActivationStatus() {
        return this.activationStatus;
    }

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public void setuType(String a) {
        this.uType = a;
    }

    public void setEmail(String a) {
        this.email = a;
    }

    public void setPassword(String a) {
        this.password = a;
    }

    public void setFirstName(String a) {
        this.firstName = a;
    }

    public void setMiddleName(String a) {
        this.middleName = a;
    }

    public void setLastName(String a) {
        this.lastName = a;
    }

    public void setBirthDate(String a) {
        this.birthDate = a;
    }
    public void setMobilePhone(String a) {
        this.mobilePhone = a;
    }

    public void setGender(String a) {
        this.gender = a;
    }

    public void setCreationDate(String a) {
        this.creationDate = a;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBirthDate() {
        return this.birthDate;
    }
 
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public String getGender() {
        return this.gender;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public String getuType() {
        return this.uType;
    }

    public long getuID() {
        return this.uID;
    }

    public void setuID(long id) {
        this.uID = id;
    }
    public List<payment> getPayments() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPayments'");
    }
}
