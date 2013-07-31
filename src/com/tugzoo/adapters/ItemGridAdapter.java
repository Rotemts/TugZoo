package com.tugzoo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.tugzoo.R;
import com.tugzoo.types.ItemOfUser;

import java.util.ArrayList;

/**
 * Created by Rotem on 25/07/13.
 */
public class ItemGridAdapter extends BaseAdapter {
    private Context c;
    private ArrayList<ItemOfUser> items;
    private ImageLoader imageLoader;
    private LayoutParams params;

    public ItemGridAdapter(Context c, ArrayList<ItemOfUser> items) {
        this.c = c;
        this.items = items;
        imageLoader = new ImageLoader(c);
        params = new LayoutParams(LayoutParams.MATCH_PARENT, 220);
    }

    @Override
    public int getCount() {
        if (items != null)
            return items.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (items != null)
            return items.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getIdAtPosition(int position) {
        if (items != null)
            return items.get(position).getId();
        return "";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataHolder dh;
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(c);
            convertView = li.inflate(R.layout.item_in_grid, null);
            convertView.setLayoutParams(params);
            dh = new DataHolder();
            dh.image = (ImageView) convertView.findViewById(R.id.story_img);
            dh.name = (TextView) convertView.findViewById(R.id.story_name);
            convertView.setTag(dh);
        }
        else
            dh = (DataHolder) convertView.getTag();

        ItemOfUser item = (ItemOfUser) getItem(position);
        dh.name.setText(item.getUserName());
        imageLoader.displayImage(item.getImgUrl(), dh.image);

        return convertView;
    }

    private class DataHolder {
        public TextView name;
        public ImageView image;
    }
}
