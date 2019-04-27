package com.example.hp.zermome;

import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class show_product_info extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<product> product_list;
    TextToSpeech t;
    String message;
    String batch_id,buying_from,Retailer_id,product_name,Retailer_name,Fertilizer_name,Fertilizer_quantity,Farmer_id,Date_time,chemical_used;
    String crop_name,crop_quantity,sold_to,chemical_quantity,Farmer_name,location,area,duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_info);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        product_list = new ArrayList<>();
        product_list.add(new product(message));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ProductAdapter(product_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(show_product_info.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        t=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR)
                {
                    t.setLanguage(Locale.UK);
                }
            }
        });

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("CLICK TO READ THIS");
        alertDialogBuilder.setPositiveButton("READ",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       message= message.replaceAll("------------------------------","  ");
                        t.speak(message,TextToSpeech.QUEUE_FLUSH,null);
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();




      //  Toast.makeText(this,Retailer_id+"hello"+message,Toast.LENGTH_LONG).show();
    }
    public void onDestroy()
    {
        if(t!=null)
        {
            t.stop();
            t.shutdown();
        }
        super.onDestroy();
    };
}