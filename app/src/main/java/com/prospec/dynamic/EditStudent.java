package com.prospec.dynamic;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EditStudent extends Activity {
    DatabaseStudent mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    ListView listStudent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
    }

    public void onResume() {
        super.onResume();

        mHelper = new DatabaseStudent(this);
        mDb = mHelper.getWritableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseStudent.TABLE_NAME, null);

        listStudent = (ListView)findViewById(R.id.listStudent);
        listStudent.setAdapter(updateListView());
        listStudent.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);

                String name = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_NAME));
                String lastname = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_LASTNAME));
                String school = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_SCHOOL));

                Intent intent = new Intent(getApplicationContext(), UpdateStudent.class);
                intent.putExtra("NAME", name);
                intent.putExtra("LASTNAME", lastname);
                intent.putExtra("SCHOOL", school);
                startActivity(intent);
            }
        });

        listStudent.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditStudent.this);
                builder.setTitle("ลบข้อมูลนักเรียน");
                builder.setMessage("คุณต้องการลบข้อมูลนักเรียนคนนี้ใช่หรือไม่?");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_NAME));
                        String lastname = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_LASTNAME));
                        String school = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_SCHOOL));

                        mDb.execSQL("DELETE FROM " + DatabaseStudent.TABLE_NAME
                                + " WHERE " + DatabaseStudent.COL_NAME + "='" + name + "'"
                                + " AND " + DatabaseStudent.COL_LASTNAME + "='" + lastname + "'"
                                + " AND " + DatabaseStudent.COL_SCHOOL + "='" + school + "';");

                        mCursor.requery();

                        listStudent.setAdapter(updateListView());

                        Toast.makeText(getApplicationContext(),"ลบข้อมูลนักเรียนเรียบร้อย"
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }

    public ArrayAdapter<String> updateListView() {
        ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()){
            arr_list.add("ชื่อ : " + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_NAME)) + "\t\t"
                    + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_LASTNAME)) + "\n"
                    + "โรงเรียน : " + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_SCHOOL)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext()
                , R.layout.my_listview, arr_list);
        return adapterDir;
    }
}