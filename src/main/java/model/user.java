// 'User.java' for User object class
package model;
import java.io.Serializable;

public class user implements Serializable{
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String mobilePhone;

    //Setters
    public user() {}

    public user(String emai, String pass, String fName, String mname, String lName, String bday, String mPhone) {
        this.email = emai;
        this.password = pass;
        this.firstName = fName;
        this.middleName = mname;
        this.lastName = lName;
        this.birthDate = bday;
        this.mobilePhone = mPhone;
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
}
