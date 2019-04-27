package com.example.hp.zermome.Fragement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.zermome.Batch_no;
import com.example.hp.zermome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dmax.dialog.SpotsDialog;


public class requirement extends Fragment {


    EditText crop_name, Quantity, price;
    Button submit;
    String name, contact_no, Occupation, crop_name_txt, Quantity_txt, price_txt;
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
    android.app.AlertDialog waiting_dialog;
    StringBuilder token;

    public requirement() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requirement, container, false);
        crop_name = view.findViewById(R.id.crop_name);
        Quantity = view.findViewById(R.id.Quantity);
        price = view.findViewById(R.id.Price);
        submit = view.findViewById(R.id.submit);
        waiting_dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setMessage("Please wait")
                .setContext(getContext())
                .build();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();
            }
        });
        return view;
    }

    private void getDetails() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String user_id = mAuth.getCurrentUser().getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(user_id);
        current_user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
                Occupation = dataSnapshot.child("Occupation").getValue().toString();
                contact_no = dataSnapshot.child("contact_no").getValue().toString();
                putDetails();

                // location=dataSnapshot.child("location").getValue().toString();
                // gender=dataSnapshot.child("gender").getValue().toString();
                //DOB=dataSnapshot.child("DOB").getValue().toString();
                //txtlocation.setText(location);
                //nametxt.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void putDetails() {
        if (crop_name.getText().toString().length() == 0) {
            crop_name.setError("Field can not be left blank");
            crop_name.requestFocus();
        }
        if (Quantity.getText().length() == 0) {
            Quantity.setError("Field can not be left blank");
            Quantity.requestFocus();
        }

        if (price.getText().length() == 0) {
            price.setError("Field can not be left blank");
            price.requestFocus();
        } else {
            getToken();
            crop_name_txt=crop_name.getText().toString();
            price_txt=price.getText().toString();
            Quantity_txt=Quantity.getText().toString();
            DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference().child("Offer").child(token.toString());
            Map remaining_info=new HashMap();
            remaining_info.put("crop_name",crop_name_txt);
            remaining_info.put("crop_quantity",Quantity_txt);
            remaining_info.put("name",name);
            remaining_info.put("contact_no",contact_no);
            remaining_info.put("price",price_txt);
            remaining_info.put("Occupation",Occupation);
            current_user_db.setValue(remaining_info);
            waiting_dialog.show();




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"Successfully submited", Toast.LENGTH_LONG).show();
                    waiting_dialog.dismiss();
                    Quantity.setText("");
                    price.setText("");
                    crop_name.setText("");

                }
            }, 3000);

        }

    }

    public void getToken() {
        Random r = new Random();
        token = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            token.append(CHARS.charAt(r.nextInt(CHARS.length())));
        }


    }
}
