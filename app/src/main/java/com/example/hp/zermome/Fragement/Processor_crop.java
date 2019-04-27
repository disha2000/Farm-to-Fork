package com.example.hp.zermome.Fragement;

import android.animation.LayoutTransition;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.example.hp.zermome.Batch_no;
import com.example.hp.zermome.QrCode_genrator;
import com.example.hp.zermome.R;
import com.example.hp.zermome.SplashScreeenActivity;
import com.example.hp.zermome.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import dmax.dialog.SpotsDialog;


public class Processor_crop extends Fragment {

    EditText e11, e22;
    android.app.AlertDialog waiting_dialog;
    Button post;
    StringBuilder token;
    LinearLayout container_linear;
    Button add;
    View view;
    String batch_id;
    String name,location;


    String Content;
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
    public Processor_crop() {

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "you cancelled scanning", Toast.LENGTH_LONG).show();
            } else {
                Content=result.getContents();
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                DatabaseReference current_user_db1=FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(Content);
                current_user_db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        name=dataSnapshot.child("name").getValue().toString();
                        location=dataSnapshot.child("location").getValue().toString();
                       e22.setText("name:-"+name+" Location:-"+location);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_processor_crop, container, false);

        e11 = view.findViewById(R.id.crop_name);
        e22 = view.findViewById(R.id.Buying_from);
        e22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
        add = view.findViewById(R.id.add_field_button);
        post = view.findViewById(R.id.button3);
        waiting_dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setMessage("Please wait")
                .setContext(getContext())
                .build();


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        container_linear = (LinearLayout) view.findViewById(R.id.container);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);




                Button delete = addView.findViewById(R.id.delete_button);
                final EditText textOut = (EditText) addView.findViewById(R.id.number_edit_text);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        container_linear.removeView((View) view.getParent());

                    }
                });
                container_linear.addView(addView, 0);

                // parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);


            }
        });
        LayoutTransition transition = new LayoutTransition();
        container_linear.setLayoutTransition(transition);
        return view;

    }

    public void submit() {

        if (e11.getText().toString().length() == 0) {
            e11.setError("Field can not be left blank");
            e11.requestFocus();
        }
        if (e22.getText().length() == 0) {
            e22.setError("Field can not be left blank");
            e22.requestFocus();
        }
        else {
            String product_name_text = e11.getText().toString();
            String Buying_from_text = e22.getText().toString();

            getEditTextList();
            // Toast.makeText(getContext(), list.get(0 ), Toast.LENGTH_LONG).show();
          //  getToken();
            Toast.makeText(getContext(),batch_id,Toast.LENGTH_LONG).show();
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseUser currentUser=mAuth.getCurrentUser();
            final String user_id=mAuth.getCurrentUser().getUid();
            //String seperate[]=batch_id.split(",");

            getToken();

            DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference().child("Final_prodcut").child(token.toString());

           current_user_db.child("numbers").push().setValue(1);
          current_user_db.child("numbers").push().setValue(53);
            Map remaining_info=new HashMap();
            remaining_info.put("product_name",product_name_text);
            remaining_info.put("Buying_from",Buying_from_text);
            remaining_info.put("Batch_id",batch_id);
            remaining_info.put("Retailer_id",user_id);

            current_user_db.setValue(remaining_info);


            waiting_dialog.show();





            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivity(),QrCode_genrator.class);
                    intent.putExtra("message", token.toString());
                    startActivity(intent);

                    waiting_dialog.dismiss();
                    Toast.makeText(getActivity(),"Sucessfully submitted",Toast.LENGTH_LONG).show();
                }
            }, 3000);


        }

    }







    void getEditTextList() {

        int childCount = container_linear.getChildCount();
        batch_id="";


        String childTextViewText = null;
        for(int c=0; c<childCount; c++){
            View childView = container_linear.getChildAt(c);
            TextView childTextView = (TextView)(childView.findViewById(R.id.number_edit_text));


            childTextViewText = (childTextView.getText().toString());
            batch_id=childTextViewText+" , "+batch_id;


        }

       batch_id = batch_id.substring(0, (batch_id.length() - 2));
    }
    public void getToken() {
        Random r = new Random();
        token = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            token.append(CHARS.charAt(r.nextInt(CHARS.length())));
        }

    }



}

