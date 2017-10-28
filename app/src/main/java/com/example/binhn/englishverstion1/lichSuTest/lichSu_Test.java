package com.example.binhn.englishverstion1.lichSuTest;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.binhn.englishverstion1.DataBases;
import com.example.binhn.englishverstion1.Question.main_question;
import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

/**
 * Created by binhn on 4/4/2017.
 */

public class lichSu_Test extends AppCompatActivity {
    ArrayList<LichSu> arr = null;
    DataBases db = new DataBases(this);
    lich_su_Apdater adap;
    ListView lv;
    RadioButton rA, rB, rC, rD;
    main_question mainQuestion = new main_question();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lich_su_test);
        lv = (ListView) findViewById(R.id.lvHistory);
        arr = new ArrayList<LichSu>();
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("Lịch Sử Test");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adap = new lich_su_Apdater(this, R.layout.item_lichsu_test, arr);
        load();
        lv.setAdapter(adap);
        getView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(lichSu_Test.this, lich_su_hien.class);
                Bundle b = new Bundle();
                LichSu lichsu = (LichSu) adap.getItem(position);
                b.putString("ID", lichsu.getIdcauhoi());
                b.putString("Time", lichsu.getTimeThi());
                b.putString("IDCheck", lichsu.getIdtich());
                in.putExtras(b);
                startActivity(in);
            }
        });

    }

    public void getView() {
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//chọn nhiều itome trong ListView
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = lv.getCheckedItemCount();
                //hiện só lượng đã chọn
                mode.setTitle(checkedCount + " /" + adap.getSelectedCount());
                adap.toggleSelection(position);//xóa item hiện trên listView
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
                        SparseBooleanArray selected = adap.getSelectedIds();//lấy ra các vị trí đã check
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                LichSu lichsu = (LichSu) adap.getItem(selected.keyAt(i));
                                adap.remove(lichsu);//xóa trong mảng ArryList
                                delete(lichsu.getId());//xóa trong CSDL
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
        Cursor cu = db.getCursor("select * from LichSuTest");
        if (cu.moveToLast()) {
            do {
                LichSu lichsu = new LichSu();
                lichsu.setId(cu.getInt(0));
                lichsu.setNgay(cu.getString(1));
                lichsu.setIdcauhoi(cu.getString(2));
                lichsu.setIdtich(cu.getString(3));
                lichsu.setTimeThi(cu.getString(5));
                lichsu.setDiem(cu.getInt(4));
                arr.add(lichsu);
            } while (cu.moveToPrevious());

        }
    }
    //kiểm tra đấp án đúng  thì trả ra true ngược lại
    public boolean kiemtra(String a, String b) {
        if (a.contains(b.replaceAll("\\s+", ""))) {
            return true;
        } else {
            return false;
        }
    }
    public void delete(int id)
    {
        db.ExecuteSQL("delete from LichSuTest where id = "+id+"");
    }

}
