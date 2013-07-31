package com.tugzoo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.tugzoo.R;
import com.tugzoo.ServerRequests;
import com.tugzoo.adapters.ItemGridAdapter;
import com.tugzoo.types.Item;
import com.tugzoo.types.ItemOfUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rotem on 25/07/13.
 */
public class ItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        final int itemId = getIntent().getExtras().getInt("item_id");
            ServerRequests.getInstance().makeRequest("findproduct/" + itemId + "/30.32/55.32", null, new ServerRequests.ServerResponse() {
                @Override
                public void gotResponse(JSONObject response) {
                    String name = null;
                    try {
                        name = response.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String imgUrl = null;
                    try {
                        imgUrl = response.getString("pic");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    double price = 0;
                    try {
                        price = response.getDouble("price");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray itemsOfUsers = null;
                    try {
                        itemsOfUsers = (JSONArray) ((JSONArray)response.getJSONArray("items").get(2)).get(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Item item = new Item(name, imgUrl, itemId, price);
                    setItemData(item, itemsOfUsers);
                }

                @Override
                public void onError() {

                }
            });
//        }
    }

    private ItemGridAdapter adapter;

    private void setItemData(Item item, JSONArray itemsOfUsers) {
        ((TextView) findViewById(R.id.item_price)).setText("$" + item.getPrice() + "/day");
        ((TextView) findViewById(R.id.item_name)).setText(item.getName());
        new ImageLoader(this).displayImage(item.getImgUrl(), (ImageView) findViewById(R.id.item_img));

        GridView grid = (GridView) findViewById(R.id.items_grid);

        //create fake data
        ArrayList<ItemOfUser> items = new ArrayList<ItemOfUser>();
        if (itemsOfUsers != null) {
            for (int i=0 ; i<itemsOfUsers.length() ; i++) {
                JSONObject job = null;
                try {
                    job = itemsOfUsers.getJSONObject(i).getJSONObject("obj");
                } catch (JSONException e) {
                    job = new JSONObject(); //prevent null exception
                    e.printStackTrace();
                }
                String username = null;
                try {
                    username = job.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String imgUrl = null;
                try {
                    imgUrl = job.getString("itemPic");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String id = null;
                try {
                    id = job.getJSONObject("_id").getString("$oid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String address = null;
                try {
                    address = job.getString("address");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String userImg = null;
                try {
                    userImg = job.getString("userPic");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String desc = null;
                try {
                    desc = job.getString("desc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                items.add(new ItemOfUser(username, imgUrl, id, address, userImg, desc, item.getId()));
            }
        }
//        items.add(new ItemOfUser("Maxim Veksler", "http://img.thesun.co.uk/multimedia/archive/00488/drillmain_488592a.jpg", "0", "Bugrashov st. 106, Tel-Aviv",
//                "http://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/c28.28.356.356/s160x160/1045231_10151724101815049_35711224_n.jpg", "I don't use this drill much but it works really well :) Please rent for at least two days!", item.getId()));
//        items.add(new ItemOfUser("Artiom Dashinsky", "http://t3.gstatic.com/images?q=tbn:ANd9GcRF5G1NX2eWyNYBOXqWyT3JbcJEZQ5rM7E7gPQg8BG4uwyt7j24", "1", "Kerem Hatemanim 10, Tel-Aviv",
//                "http://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/c37.37.461.461/s160x160/156546_3445420905756_592296493_n.jpg", "This drill iz da best YO", item.getId()));
//        items.add(new ItemOfUser("Rotem Tsabary", "http://24.media.tumblr.com/tumblr_m9x9tn1NeP1r3la8so1_500.jpg", "2", "Frishman st. 63, Tel-Aviv",
//                "http://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/c42.42.521.521/s160x160/943295_4690155338860_1287573983_n.jpg", "Hey, my drill is GREATTTT! but I don't use it much so you can borrow it :)", item.getId()));
//        items.add(new ItemOfUser("Roni Superstar", "http://gingersnaptoit.files.wordpress.com/2013/02/me-and-my-drill-2013-smaller2.jpg", "3", "Bugrashov st. 106, Tel-Aviv",
//                "http://fbexternal-a.akamaihd.net/safe_image.php?d=AQAfp1vHq32Wv7M7&w=562&h=721&url=http%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F5%2F54%2FRoni_Duani.jpg", "I don't use this drill much...", item.getId()));
//        items.add(new ItemOfUser("Dudu Aharon", "http://www.federalreserve.gov/careers/new4hire/gifjpg/man_hold_drill.jpg", "4", "Kerem Hatemanim 10, Tel-Aviv",
//                "http://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/c45.45.559.559/s160x160/551415_536506746399536_1172069331_n.jpg", "This drill iz da best YO", item.getId()));
//        items.add(new ItemOfUser("Tuvia Tsafir", "http://generic.pixmac.com/4/royalty-free-images-man-with-drill-tool-white-46073227.jpg", "5", "Frishman st. 63, Tel-Aviv",
//                "http://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash4/c0.1.231.231/s160x160/404941_350901884939570_166508495_n.jpg", "Hey, my drill is GREATTTT! but I don't use it much so you can borrow it :)", item.getId()));
        adapter = new ItemGridAdapter(this, items);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //a bit ugly because our id is a String and not long (so we can't use the native getItemIdAtPosition method)
                startActivity(new Intent().setClass(ItemActivity.this, ItemOfUserActivity.class).putExtra("item_id", ((ItemGridAdapter) adapter).getIdAtPosition(position)));
            }
        });

    }
}
