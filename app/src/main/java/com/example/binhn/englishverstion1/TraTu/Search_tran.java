package com.example.binhn.englishverstion1.TraTu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.binhn.englishverstion1.DataBases;
import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

/**
 * Created by binhn on 3/15/2017.
 */

public class Search_tran extends AppCompatActivity implements TextView.OnEditorActionListener {
    ArrayList<String> ar = null;
    ArrayAdapter adap = null;
    EditText edt;
    ListView lv;
    String[] item = null;
    Integer[] icon = {R.drawable.ic_history_black_24dp, R.drawable.ic_search_black_24dp};
    DataBases db = new DataBases(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_tran);
        edt = (EditText) findViewById(R.id.edtSearch);
        final ImageView a = (ImageView) findViewById(R.id.itemSearch);
        lv = (ListView) findViewById(R.id.lv);
        //
        ar = new ArrayList<String>();
        //
        edt.setOnEditorActionListener(this);// sét sự kiện phím ok
        getHistory();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(edt.getText()+"" !=  "") {//hiện dũ liệu tìm kiếm được
                    String a = ((TextView) view.findViewById(R.id.itemSearch)).getText().toString();// lay ra từ
                    SearchTu(a);
                }
                else
                {//hiện dữ liệu trong lịch sử
                    String a = ((TextView) view.findViewById(R.id.itemSearch1)).getText().toString();// lay ra từ
                    SearchTu(a);
                }
            }
        });
        //hiện từ gợi ý
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                String search = edt.getText().toString();
                if (edt.getText() + "" == "") {
                    getHistory();
                } else {
                    copy2ListView(search);
                    Search_tran.this.adap.getFilter().filter(s);//lọc thông minh
                }
            }
        });

    }

    public void copy2ListView(String s) {
        ar.clear();
        if (s != "") {
            Cursor cu = db.getCursor("select * From anhviet where tu like \"" + s + "%\" limit 0, 50");
            if (cu.moveToFirst()) {
                do {
                    ar.add(cu.getString(0));
                } while (cu.moveToNext());
                adap = new ArrayAdapter<String>(this, R.layout.item_search, R.id.itemSearch, ar);
                lv.setAdapter(adap);
            }
        }

    }

    //tìm kiếm từ
    public void SearchTu(String s) {
        /*Nhập từ cần tra*/
        Cursor cu = db.getCursor("select * From anhviet where tu like \"" + s + "\"");
        if (cu.moveToFirst()) {
            /*đóng gói dữ liệu và truyền đi activity tra_Tu*/
            Intent iDetail = new Intent(this, Tra_Tu.class);
            Bundle b = new Bundle();
            b.putString("key_Word", cu.getString(0));
            b.putString("key_Mean", cu.getString(1));
            iDetail.putExtras(b);
            finish();
            startActivity(iDetail);
        }
    }

    // sự kiện ấn k trên phím ảo
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_GO) {
            SearchTu(edt.getText().toString());
            handled = true;
        }
        return handled;
    }

    //lấy lịch sử lên listView
    public void getHistory() {
        ar.clear();
        Cursor c = db.getCursor("select * from LichSuTraTu");
        if (c.moveToLast()) {
            do {
                ar.add(c.getString(1));
            } while (c.moveToPrevious());
            adap = new ArrayAdapter<String>(this, R.layout.history, R.id.itemSearch1, ar);
            lv.setAdapter(adap);
        }
    }
    public void DeleleHistory(String s)
    {
        db.ExecuteSQL("delete from LichSuTraTu where work = \""+s+"\" ");
    }
}
