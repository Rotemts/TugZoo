package com.tugzoo.types;

/**
 * Created by Rotem on 25/07/13.
 */
public class Story {

    private String name;
    private String imgUrl;
    private int id;

    public Story(String name, String imgUrl, int id) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
