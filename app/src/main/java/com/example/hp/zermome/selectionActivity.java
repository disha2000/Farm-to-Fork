package com.example.hp.zermome;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;




public class selectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);


    }

    public void farmerfun(View view) {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
       startActivity(intent);
    }

    public void procesorFun(View view) {
        Intent intent=new Intent(getApplicationContext(),RetailerLogin.class);
        startActivity(intent);
    }

    public void marketFun(View view) {
        Intent intent=new Intent(getApplicationContext(),marketlogin.class);
        startActivity(intent);
    }

    public void customerFun(View view) {
        Intent intent=new Intent(getApplicationContext(),ScannerActivity.class);
        startActivity(intent);
    }
}