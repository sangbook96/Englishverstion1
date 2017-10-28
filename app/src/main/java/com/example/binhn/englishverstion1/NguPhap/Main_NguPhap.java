package com.example.binhn.englishverstion1.NguPhap;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.binhn.englishverstion1.DataBases;
import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

/**
 * Created by ST on 28/03/2017.
 */

public class Main_NguPhap extends AppCompatActivity implements SearchView.OnQueryTextListener {
    DataBases db=new DataBases(this);
    ArrayList<NguPhap>arr;
    Custom_Adapter_NguPhap adp;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_nguphap);
        //hiện tiêu đề;
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("Ngữ Pháp");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv=(ListView)findViewById(R.id.lvnguphap);
        arr=new ArrayList<NguPhap>();
        adp=new Custom_Adapter_NguPhap(this,R.layout.item_nguphap,arr);
        lv.setAdapter(adp);
        load();
        adp.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timkiem,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    public void load() {
        Cursor cur = db.getCursor("Select * from Gramar");
        int count =cur.getCount();
        if (cur.moveToFirst()) {
            do {
                arr.add(new NguPhap(count++, cur.getString(1), cur.getString(2),
                        cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));
            } while (cur.moveToNext());
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Cursor cur=db.getCursor("Select * from Gramar where Ten like '%"+newText+"%'");
        int count =cur.getCount();
        /*Làm mới lại adp*/
        adp.clear();

        if(cur.moveToFirst()){

            do{
                arr.add(new NguPhap(count++, cur.getString(1), cur.getString(2),
                        cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6)));
            }while (cur.moveToNext());

        }
        lv.setAdapter(adp);
        adp.notifyDataSetChanged();
        return true;

    }
}
