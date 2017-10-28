package com.example.binhn.englishverstion1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by binhn on 4/4/2017.
 */

public class Custom_listView extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> itemname = new ArrayList<String>();
    private final Integer[] imgid;
    /*constructor dùng để khởi tạo các gia trị*/
    public Custom_listView(Activity context, ArrayList itemname, Integer[] imgid) {
        super(context, R.layout.menu, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }
    /*View là 1 object tham chiếu tới các đối tượng*/
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname.get(position));
        /*dùng để set icon cho listview*/
        if (imgid.length > 1) {
            imageView.setImageResource(imgid[position]);
            return rowView;
        } else {
            imageView.setImageResource(imgid[0]);
            return rowView;
        }

    }
}

