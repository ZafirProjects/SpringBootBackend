package com.reply.streaming.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Users {

    @Id
    private String Username;
    private String Password;
    private String Email;
    private String DoB;
    private String CreditCardNumber;

    public Users() {
    }

    public Users(String username, String password, String email, String doB, String creditCardNumber) {
        Username = username;
        Password = password;
        Email = email;
        DoB = doB;
        CreditCardNumber = creditCardNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }
}
