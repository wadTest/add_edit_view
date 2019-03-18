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

public class ViewStudent extends Activity {
    DatabaseStudent mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    ListView listStudent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        mHelper = new DatabaseStudent(this);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseStudent.TABLE_NAME, null);

        ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast() ){
            arr_list.add("ชื่อ : " + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_NAME))
                    + "\t\t" + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_LASTNAME))
                    + "\nโรงเรียน : " + mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_SCHOOL)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview, arr_list);

        listStudent = (ListView)findViewById(R.id.listStudent);
        listStudent.setAdapter(adapterDir);
        listStudent.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);

                String name = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_NAME));
                String lastname = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_LASTNAME));
                String school = mCursor.getString(mCursor.getColumnIndex(DatabaseStudent.COL_SCHOOL));

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudent.this);
                builder.setTitle("ข้อมูลนักเรียน");
                builder.setMessage("ชื่อ : " + name + "\n\nนามสกุล : " + lastname + "\n\nโรงเรียน : " + school);
                builder.setNeutralButton("OK", null);
                builder.show();
            }
        });
    }

    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }
}