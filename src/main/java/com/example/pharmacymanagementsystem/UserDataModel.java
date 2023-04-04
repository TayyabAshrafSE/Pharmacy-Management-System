package com.example.pharmacymanagementsystem;

import org.w3c.dom.ls.LSException;

public class UserDataModel {
    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String email;
    private String Password;
    private String Role;
    private String Address;

    public UserDataModel() {
    }

    public UserDataModel(Integer Id, String FirstName, String LastName, String Phone, String email, String Password, String Role, String Address) {
        this.Id = Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password = Password;
        this.Phone = Phone;
        this.Role = Role;
        this.Address = Address;
        this.email = email;
    }

    public Integer getId() {
        return Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return Role;
    }
}