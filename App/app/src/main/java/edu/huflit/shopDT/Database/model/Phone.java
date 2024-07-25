package edu.huflit.shopDT.Database.model;

import java.io.Serializable;

public class Phone implements Serializable {

    public Phone () {
    }
    private String price;

    public String getPrice () {
        return price;
    }

    public void setPrice ( String price ) {
        this.price = price;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getDetails () {
        return details;
    }

    public void setDetails ( String details ) {
        this.details = details;
    }

    public String getImgURL () {
        return imgURL;
    }

    public void setImgURL ( String imgURL ) {
        this.imgURL = imgURL;
    }

    private String name;
    private String details;
    private String imgURL;

    public Phone  (String name, String price ,String details ,String imgURL ) {
        this.price = price;
        this.name = name;
        this.details = details;
        this.imgURL = imgURL;
    }
}
