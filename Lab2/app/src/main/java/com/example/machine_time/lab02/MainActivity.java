package com.example.machine_time.lab02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button authBtn, regBtn;

    SharedPreferences logAndPass;

    RegistrationPage.DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authBtn = (Button)findViewById(R.id.authBtn);
        regBtn = (Button)findViewById(R.id.regBtn);

        authBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);


        dbHelper = new RegistrationPage.DBHelper(this);

        db = dbHelper.getWritableDatabase();

        logAndPass = getSharedPreferences("savedData", 0);
        String login = logAndPass.getString("login", "");
        String password = logAndPass.getString("password", "");


        Cursor cursor = db.query("users", null, "login = ?", new String[]{login}, null, null, null);

        if(cursor.moveToFirst()){

            int passCol = cursor.getColumnIndex("password");

            if(password.equals(cursor.getString(passCol))){
                Intent intent = new Intent(this, MainPage.class);
                startActivity(intent);
            }


        }else Log.d("warning", "0 rows, user don't found");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.authBtn:
                Intent auth = new Intent(this, AuthPage.class);
                startActivity(auth);
                break;
            case R.id.regBtn:
                Intent reg = new Intent(this, RegistrationPage.class);
                startActivity(reg);
                break;
        }
    }
}
