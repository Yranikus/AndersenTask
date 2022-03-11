package com.example.Andersen.entity;

import java.util.Date;

public class DateEntity {

    private int id;
    private Date date;


    public DateEntity() {
    }

    public DateEntity(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
