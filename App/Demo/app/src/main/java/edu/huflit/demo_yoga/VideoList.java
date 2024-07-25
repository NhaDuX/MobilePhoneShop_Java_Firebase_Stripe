package com.android.shopdt;

public class VideoList {
    int vid;
    String name;
    String de1;
    String de2;

    public VideoList(int vid,String name, String de1, String de2, String imageurl, String id, String details) {
        this.name = name;
        this.de1 = de1;
        this.de2 = de2;
        this.imageurl = imageurl;
        this.id = id;
        this.details = details;
        this.vid = vid;
    }
    public VideoList(String id){
        this.id  = id;
    }

    String imageurl;
    String id;
    String details;
//    public VideoList( ) {
//
//    }


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDe1() {
        return de1;
    }

    public void setDe1(String de1) {
        this.de1 = de1;
    }

    public String getDe2() {
        return de2;
    }

    public void setDe2(String de2) {
        this.de2 = de2;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
