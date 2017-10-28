package com.example.binhn.englishverstion1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.binhn.englishverstion1.NguPhap.Main_NguPhap;
import com.example.binhn.englishverstion1.Note.Main_Note;
import com.example.binhn.englishverstion1.Question.main_question;
import com.example.binhn.englishverstion1.TraTu.Search_tran;
import com.example.binhn.englishverstion1.lichSuTest.lichSu_Test;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    DataBases db = new DataBases(this);
    ArrayList<String> ar = null;
    String[] item = {"Tra Từ Điển", "Luyện Tập", "Ghi Chú", "Ngữ Pháp", "Lịch sử Luyện Tập", "Cài Đặt"};
    Integer[] icon = {R.drawable.timkiem, R.drawable.tets, R.drawable.ghi_chu, R.drawable.book2, R.drawable.ghi_chu, R.drawable.icon1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            db.CopyDB2SDCard();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ar = new ArrayList<String>();
        for (String a : item) {
            ar.add(a);
        }
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("EngList Learning Support");
        Custom_listView adapter = new Custom_listView(this, ar, icon);
        lv = (ListView) findViewById(R.id.Listitem);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stubin
                if (position == 0) {
                    Intent in = new Intent(getApplication(), Search_tran.class);
                    startActivity(in);
                }
                if (position == 1) {
                    Intent in = new Intent(getApplication(), main_question.class);
                    startActivity(in);
                }
                if (position == 2) {
                    Intent in = new Intent(getApplication(), Main_Note.class);
                    startActivity(in);
                }
                if (position == 3) {
                    Intent in = new Intent(getApplication(), Main_NguPhap.class);
                    startActivity(in);
                }
                if (position == 4) {
                    Intent in = new Intent(getApplication(), lichSu_Test.class);
                    startActivity(in);
                }
                if (position == 5) {
                    Intent in = new Intent(getApplication(), Setting.class);
                    startActivity(in);
                }
            }
        });
    }
}

