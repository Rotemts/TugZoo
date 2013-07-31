package com.tugzoo.types;

import com.tugzoo.DataHolder;

/**
 * Created by Rotem on 25/07/13.
 */
public class ItemOfUser {

        private String userName;
        private String imgUrl;
        private String id;
        private String address;
        private String userImgUrl;
        private String desc;
        private int parentItemId;

        public ItemOfUser(String userName, String imgUrl, String id, String address, String userImgUrl, String desc, int parentItemId) {
            this.userName = userName;
            this.imgUrl = imgUrl;
            this.id = id;
            this.parentItemId = parentItemId;
            this.address = address;
            this.userImgUrl = userImgUrl;
            this.desc = desc;
            DataHolder.itemsOfUsers.put(id, this);
        }

        public String getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getAddress() {
            return address;
        }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public int getParentItemId() {
        return parentItemId;
    }
}