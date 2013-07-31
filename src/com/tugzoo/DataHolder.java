package com.tugzoo;

import com.tugzoo.types.Item;
import com.tugzoo.types.ItemOfUser;

import java.util.HashMap;

/**
 * Created by Rotem on 25/07/13.
 */
public class DataHolder {

    public static HashMap<String, ItemOfUser> itemsOfUsers = new HashMap<String, ItemOfUser>();
    public static HashMap<Integer, Item> parentItems = new HashMap<Integer, Item>();
}
