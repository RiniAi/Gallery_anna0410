
package com.example.gallery_anna0410;

public class Image {
    private int ImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    String name;

    public Image (String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}