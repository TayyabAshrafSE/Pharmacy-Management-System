package com.example.pharmacymanagementsystem;

import java.util.Set;

public class SellMedicineDataModel {

    private Integer Id;
    private String Name;
    private Integer Quantity;
    private Double Price;
    private Integer NoOfUnits;
    private Double TotalPrice;

    public SellMedicineDataModel() {
    }

    public SellMedicineDataModel(Integer Id, String Name, Integer Quantity, Double Price, Integer NoOfUnits, Double TotalPrice) {
        this.Id = Id;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;
        this.NoOfUnits = NoOfUnits;
        this.TotalPrice = TotalPrice;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public Integer getNoOfUnits() {
        return NoOfUnits;
    }
}
