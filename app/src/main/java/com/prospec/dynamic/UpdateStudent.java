package com.prospec.dynamic;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateStudent extends Activity {
    DatabaseStudent mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    String name, lastname, school;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        name = getIntent().getExtras().getString("NAME");
        lastname = getIntent().getExtras().getString("LASTNAME");
        school = getIntent().getExtras().getString("SCHOOL");

        mHelper = new DatabaseStudent(this);
        mDb = mHelper.getWritableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseStudent.TABLE_NAME
                + " WHERE " + DatabaseStudent.COL_NAME + "='" + name + "'"
                + " AND " + DatabaseStudent.COL_LASTNAME  + "='" + lastname + "'"
                + " AND " + DatabaseStudent.COL_SCHOOL + "='" + school  + "'", null);

        final EditText editName = (EditText)findViewById(R.id.editName);
        editName.setText(name);
        final EditText editLastName = (EditText)findViewById(R.id.editLastName);
        editLastName.setText(lastname);
        final EditText editSchool = (EditText)findViewById(R.id.editSchool);
        editSchool.setText(school);

        Button buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String nameUpdate = editName.getText().toString();
                String lastnameUpdate = editLastName.getText().toString();
                String schoolUpdate = editSchool.getText().toString();

                if(name.length() != 0 && lastname.length() != 0 && school.length() != 0 ) {
                    mDb.execSQL("UPDATE " + DatabaseStudent.TABLE_NAME  + " SET "
                            + DatabaseStudent.COL_NAME + "='" + nameUpdate + "', "
                            + DatabaseStudent.COL_LASTNAME + "='" + lastnameUpdate + "', "
                            + DatabaseStudent.COL_SCHOOL + "='" + schoolUpdate
                            + "' WHERE " + DatabaseStudent.COL_NAME + "='" + name + "'"
                            + " AND " + DatabaseStudent.COL_LASTNAME + "='" + lastname + "'"
                            + " AND " + DatabaseStudent.COL_SCHOOL + "='" + school + "';");

                    Toast.makeText(getApplicationContext(), "แก้ไขข้อมูลนักเรียนเรียบร้อยแล้ว"
                            , Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูลนักเรียนให้ครบทุกช่อง"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonNameClear = (Button)findViewById(R.id.buttonNameClear);
        buttonNameClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                editName.setText("");
            }
        });

        Button buttonLastNameClear = (Button)findViewById(R.id.buttonLastNameClear);
        buttonLastNameClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                editLastName.setText("");
            }
        });

        Button buttonSchoolClear = (Button)findViewById(R.id.buttonSchoolClear);
        buttonSchoolClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                editSchool.setText("");
            }
        });
    }

    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }
}