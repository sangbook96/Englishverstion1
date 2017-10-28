package com.example.binhn.englishverstion1.Note;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.binhn.englishverstion1.DataBases;
import com.example.binhn.englishverstion1.R;

import java.util.ArrayList;

/**
 * Created by ST on 15/03/2017.
 */

public class edtNote extends AppCompatActivity {
    DataBases db = new DataBases(this);
    ArrayList<String> arr;
    EditText edtTenGhiChu, edtNoiDungGhiChu;
    String tenGhiChu, NoiDungGhiChu;
    String tile=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        edtTenGhiChu = (EditText) findViewById(R.id.edt_Tile);
        edtNoiDungGhiChu = (EditText) findViewById(R.id.edt_Note);
        Bul();
    }
    public void Bul(){
        //
        Bundle b=getIntent().getExtras();
        tile=b.getString("Tile");
        if(tile!=null) {
            Cursor cursor = db.getCursor("select * from GhiChu where TenGhiChu =\"" + tile + "\"");
//        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            edtTenGhiChu.setText(cursor.getString(1));
            edtNoiDungGhiChu.setText(cursor.getString(2));
//        }while(cursor.moveToNext());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*menu insert, update, delete*/
        getMenuInflater().inflate(R.menu.menutickok, menu);
        return true;
    }
        /*item trong menu, xử lí sự kiện xác nhận insert, update, delete*/
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tickok) {
            Insert();
        }
        if(id==R.id.item_edit){
            Update();
            Toast.makeText(this, "Update thành công!", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(edtNote.this, Main_Note.class);
            startActivity(in);
            finish();

        }
        if(id==R.id.item_delete){
            Delete();
            Intent in = new Intent(edtNote.this, Main_Note.class);
            startActivity(in);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

/*Thêm bản ghi vào cơ sở dữ liệu*/

    public void Insert() {
        tenGhiChu = edtTenGhiChu.getText().toString();
        NoiDungGhiChu = edtNoiDungGhiChu.getText().toString();
/*Kiểm tra bản ghi đã tồn tại hay chưa?*/

        int sbg = db.GetCount("Select * from GhiChu where TenGhiChu=\"" + tenGhiChu + "\"");
        if (sbg == 1) {
            AlertDialog.Builder al = new AlertDialog.Builder(this);
            al.setTitle("Thông báo");
            al.setMessage("Tên ghi chú đã tồn tại. ");
            al.create().show();
        } else if (sbg == 0) {
            db.ExecuteSQL("insert into GhiChu values(" + (getId()+1) + ",\"" + tenGhiChu + "\",\"" + NoiDungGhiChu + "\")");
            Intent in = new Intent(edtNote.this, Main_Note.class);
            startActivity(in);
        }
    }
    //get ra id cua phân tử cuỗi cùng trong ghi chú
    public int getId()
    {
        int id = 0;
        Cursor cu = db.getCursor("select id from GhiChu");
        if (cu.moveToLast())
        {
            id = cu.getInt(0);
        }
        return id;
    }

    public void Update(){
        tenGhiChu = edtTenGhiChu.getText().toString();
        NoiDungGhiChu = edtNoiDungGhiChu.getText().toString();
        db.ExecuteSQL("update GhiChu set NDGhiChu=\""+NoiDungGhiChu+"\"where TenGhiChu=\""+tenGhiChu+"\"");

    }
    public void Delete(){
        tenGhiChu = edtTenGhiChu.getText().toString();
        db.ExecuteSQL("delete from GhiChu where TenGhiChu=\""+tenGhiChu+"\"");
    }
}

