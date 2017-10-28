package com.example.binhn.englishverstion1.Note;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

;

/**
 * Created by ST on 27/03/2017.
 */

public class Custom_Adapter_Note extends ArrayAdapter<Note> {
    private Context context;
    private int resource;
    private ArrayList<Note> arr;
    private ArrayAdapter<Note>adp;
    /**/
    private SparseBooleanArray selectedListItemsIds;

/*Khai báo contrustor*/
    public Custom_Adapter_Note(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Note> arr) {
        super(context, resource, arr);
        this.context=context;
        this.resource=resource;
        this.selectedListItemsIds=new SparseBooleanArray();
        this.arr=arr;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*bơm nạp giao diện vào view*/
        convertView= LayoutInflater.from(context).inflate(R.layout.item_gvghichu,parent,false);
        ImageView img=(ImageView)convertView.findViewById(R.id.img);
        TextView txtTile=(TextView)convertView.findViewById(R.id.txt_Tile);
        Note note=arr.get(position);
        txtTile.setText(note.getTile());
        GridView gv=(GridView)convertView.findViewById(R.id.Gv_GhiChu);


        return convertView;
    }

    /*xóa nhiều phần tử*/
    public void remove(Note note) {
        arr.remove(note);
        notifyDataSetChanged();
    }
    /*tổng số phần tử đã chọn*/
    public void toggleSelection(int position) {
        selectView(position, !selectedListItemsIds.get(position));
    }

    public void removeSelection() {
        this.selectedListItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            selectedListItemsIds.put(position, value);
        else
            selectedListItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return arr.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedListItemsIds;
    }
}
