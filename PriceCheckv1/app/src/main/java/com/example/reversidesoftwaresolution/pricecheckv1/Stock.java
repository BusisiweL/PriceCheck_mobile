package com.example.reversidesoftwaresolution.pricecheckv1;

import java.util.Arrays;

/**
 * Created by reversidesoftwaresolution on 2017/09/14.
 */

public class Stock {


    private long id;
    private String name;
    private String description;
    private byte[] image;
    private String category;
    private double price;

    public Stock() {
    }

    public Stock(String name, String description, byte[] image, String category, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
    }

    public Stock(long id, String name, String description, byte[] image, String category, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\"," +
                "\"description\":\""+description+"\"," +
                "\"category\":\""+category+"\"," +
                "\"price\":\""+price+"\"}";
    }
}
