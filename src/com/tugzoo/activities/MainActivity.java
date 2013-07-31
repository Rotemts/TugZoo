package com.tugzoo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.tugzoo.adapters.HomeGridAdapter;
import com.tugzoo.R;
import com.tugzoo.types.Story;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GridView grid = (GridView) findViewById(R.id.home_grid);
//        //create fake data
//        ArrayList<Story> items = new ArrayList<Story>();
//        items.add(new Story("Travel","http://img.dailymail.co.uk/i/pix/2007/04_03/tents_468x301.jpg",0));
//        items.add(new Story("Party at Home","http://t0.gstatic.com/images?q=tbn:ANd9GcQO1X48YSwUAAodR5r3UKVlcvJyRSBDTjVSC5l4xA4WzIU4hhmJXg",1));
//        items.add(new Story("Work Out","http://www.puma.com/system/articles/images/1765/body/gym.jpg?1356133776",2));
//        items.add(new Story("Fix Your House","http://t2.gstatic.com/images?q=tbn:ANd9GcSRxCvrz8u0hDPKg0hBci-wWrkMUtdVmWWHRoFEGAbdwmg5LB31",3));
//        items.add(new Story("Hack Something","http://t2.gstatic.com/images?q=tbn:ANd9GcTI_OcwOdSaBdH1nzuAf5imnDjmR00AgjykMLv0QsYQRmSeh2ba",4));
//        items.add(new Story("Get Fancy","http://t1.gstatic.com/images?q=tbn:ANd9GcR_8vCQ3yFKTfK_cbe6b3P_R0UBK7ErUtVCDQqX9zuZBk5JS-_h",5));
//        grid.setAdapter(new HomeGridAdapter(this, items));

        EditText search = (EditText) findViewById(R.id.search_field);
        search.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    startItemPage();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemPage();
            }
        });
    }

    private void startItemPage() {
        //fake item id
        startActivity(new Intent().setClass(getApplicationContext(), ItemActivity.class).putExtra("item_id",1));
    }


}
