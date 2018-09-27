package com.darknight.simplegeometry;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DarKnight on 29.03.2018.
 */

public class DataGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Level> levels;
    private LayoutInflater inflater;

    public DataGridAdapter(Context context, List<Level> list) {
        mContext = context;
        levels = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return levels.size();
    }

    public Object getItem(int position) {
        return levels.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Level lvl = levels.get(position);
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.level_number_item, parent, false);
        }
        ImageView img = view.findViewById(R.id.img_lvl_status);
        switch (lvl.getStatus()) {
            case 1:
                img.setImageResource(R.drawable.ic_complete24);
                break;
            case 0:
                img.setImageResource(R.drawable.ic_uncomplete24);
                break;
            default:
                img.setImageResource(R.drawable.ic_uncomplete24);
        }
        TextView textView = view.findViewById(R.id.txt_lvl_number);
        String text;
        if(lvl.getId() < 10){
            text = " " + String.valueOf(lvl.getId()) + " ";
        } else{
            text = String.valueOf(lvl.getId());
        }
        textView.setText(text);
        return view;
    }
}
