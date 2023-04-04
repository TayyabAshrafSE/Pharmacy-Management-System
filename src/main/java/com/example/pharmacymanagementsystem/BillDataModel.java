package com.example.pharmacymanagementsystem;

import java.lang.reflect.GenericArrayType;
import java.util.Date;

public class BillDataModel {
    private Integer Id;
    private Date Date;
    private Double TotalPaid;
    private String GeneratedBy;

    public BillDataModel() {
    }

    public BillDataModel(Integer Id, Date Date, Double TotalPaid, String GeneratedBy) {
        this.Id = Id;
        this.Date = Date;
        this.TotalPaid = TotalPaid;
        this.GeneratedBy = GeneratedBy;
    }

    public Integer getId() {
        return Id;
    }

    public Date getDate() {
        return Date;
    }

    public Double getTotalPaid() {
        return TotalPaid;
    }

    public String getGeneratedBy() {
        return GeneratedBy;
    }

}
