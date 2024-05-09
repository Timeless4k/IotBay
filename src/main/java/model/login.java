package model;


public class login {
    private String email;
    private String password;

    // Constructor
    public login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}