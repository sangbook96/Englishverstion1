package com.example.binhn.englishverstion1.Note;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.binhn.englishverstion1.DataBases;
import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

/**
 * Created by ST on 27/03/2017.
 */

public class Main_Note extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<Note> arr = new ArrayList<Note>();
    Custom_Adapter_Note adp=null;
    DataBases db = new DataBases(this);
    FloatingActionButton fabAdd;

    GridView gvHienThi;
    EditText edt_Tile, edt_Note;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_note);
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("Ghi Chú");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fabAdd = (FloatingActionButton) findViewById(R.id.Fab);
        edt_Tile = (EditText) findViewById(R.id.edt_Tile);
        edt_Note = (EditText) findViewById(R.id.edt_Note);
        gvHienThi = (GridView) findViewById(R.id.Gv_GhiChu);
        arr = new ArrayList<Note>();
        adp = new Custom_Adapter_Note(this, R.layout.item_gvghichu, arr);

        gvHienThi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Mở 1 activity edtNote để truyền dữ liệu qua */
                Intent in = new Intent(Main_Note.this, edtNote.class);
                /*tạo 1 bundle để đóng gói dữ liệu*/
                Bundle b = new Bundle();
                Note note = (Note) adp.getItem(position);
                /*put dùng để gửi dữ liệu qua edtNote*/
                b.putString("ID", String.valueOf(note.getId()));
                b.putString("Tile", note.getTile());

                b.putString("Note", note.getNote());
                /*đưa bundle vào Intent để chuyển đi*/
                in.putExtras(b);
                startActivity(in);
            }
        });
        load();
        getView();
        gvHienThi.setAdapter(adp);

        Add();
        //


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //thêm search vào ation bar
        getMenuInflater().inflate(R.menu.timkiem,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        //set OnQuertyTextListener cho search view để thực hiện search theo text
        searchView.setOnQueryTextListener(this);
        return true;

    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }*/
    /*Chọn nhiều item trong 1 listview or 1 gridview*/
    public void getView() {
        gvHienThi.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);//chọn nhiều itome trong ListView
        gvHienThi.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = gvHienThi.getCheckedItemCount();
                //hiện só lượng đã chọn
                mode.setTitle(checkedCount + " /" + adp.getSelectedCount());
                adp.toggleSelection(position);//xóa item hiện trên listView
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_opption, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        SparseBooleanArray selected = adp.getSelectedIds();//lấy ra các vị trí đã check
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Note note = (Note) adp.getItem(selected.keyAt(i));
                                adp.remove(note);//xóa trong mảng ArryList
                                delete(note.getId());//xóa trong CSDL
                            }
                        }
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
            }
        });

    }

    public void load() {
        Cursor cur = db.getCursor("select * from GhiChu");
        if (cur.moveToFirst()) {
            do {
                arr.add(new Note(cur.getInt(0), cur.getString(1), cur.getString(2)));
            } while (cur.moveToNext());
        }

    }

    public void Add() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setContentView(R.layout.item_Note);

                Intent intent = new Intent(v.getContext(), edtNote.class);
                Bundle bundle=new Bundle();
                bundle.putString("Tile", null);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void delete(int id)
    {
        db.ExecuteSQL("delete from GhiChu where id = \""+id+"\"");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
    /*Phương thức lọc khi search*/
    @Override
    public boolean onQueryTextChange(String newText) {
        Cursor cursor=db.getCursor("Select * from GhiChu where TenGhiChu like \"%"+newText+"%\"");
        /*Làm mới lại adp*/
        adp.clear();

        if(cursor.moveToFirst()){

            do{
                arr.add(new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());

        }
        gvHienThi.setAdapter(adp);
        adp.notifyDataSetChanged();
        return true;

    }
}

