package edu.huflit.shopDT;

import java.io.Serializable;

public class Phones implements Serializable {

    String name;
    String price;
    String de;
    String imgURL;
    String id;

    Boolean fav;

    public Phones(String name, String id, String price, String de, String imgURL, Boolean fav) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.de = de;
        this.imgURL = imgURL;
        this.fav = fav;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public Phones(String name, String price, String de, String imgURL) {
        this.name = name;
        this.price = price;
        this.de = de;
        this.imgURL = imgURL;
    }

    public Phones(String id, String name, String price,String de,String imgURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.de = de;
        this.imgURL = imgURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Phones( ) {}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }


}
