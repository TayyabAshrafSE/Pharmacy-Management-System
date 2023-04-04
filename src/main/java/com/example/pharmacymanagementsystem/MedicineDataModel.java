package com.example.pharmacymanagementsystem;

import java.time.LocalDate;
import java.util.Date;

public class MedicineDataModel {
    private Integer Id;
    private Double Price;
    private Integer Quantity;
    private String Name;
    private Date Expiry;

    public MedicineDataModel() {
    }

    public MedicineDataModel(Integer Id, Double Price, Integer Quantity, String Name, Date Expiry) {
        this.Id = Id;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Name = Name;
        this.Expiry = Expiry;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public String getName() {
        return Name;
    }

    public Double getPrice() {
        return Price;
    }

    public Date getExpiry() {
        return Expiry;
    }
}
