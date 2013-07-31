package com.tugzoo.types;

import com.tugzoo.DataHolder;

/**
 * Created by Rotem on 25/07/13.
 */
public class Item {

        private String name;
        private String imgUrl;
        private int id;
        private double price;

        public Item(String name, String imgUrl, int id, double price) {
            this.name = name;
            this.imgUrl = imgUrl;
            this.id = id;
            this.price = price;
            DataHolder.parentItems.put(id, this);
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

        public double getPrice() {
            return price;
        }
}