package com.prospec.dynamic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseStudent mHelper = new DatabaseStudent(this);
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        mHelper.close();
        mDb.close();

        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                startActivity(intent);
            }
        });

        Button buttonEdit = (Button)findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditStudent.class);
                startActivity(intent);
            }
        });

        Button buttonView = (Button)findViewById(R.id.buttonView);
        buttonView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewStudent.class);
                startActivity(intent);
            }
        });
    }
}