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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.example.hp.zermome.Batch_no;
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


public class FarmerCrop extends Fragment {

    EditText e11, e22, e33, e44, e55, e66, e77,e88,e99;
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";
    android.app.AlertDialog waiting_dialog;
    Button post;
    StringBuilder token;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    LinearLayout container_linear,container_chemical;
    Button add,add_chemical;
    View view;
    String list_names,list_quantity,list_chemical,list_chemical_qunatity;
    String Content;
    String name,location;
    Spinner spinner1,spinner2;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String value;



    public FarmerCrop() {

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
                       e77.setText("name:-"+name+" Location:-"+location);



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
        view = inflater.inflate(R.layout.fragment_farmer_crop, container, false);

        e11 = view.findViewById(R.id.error1);
        e22 = view.findViewById(R.id.e2);
       // e55 = view.findViewById(R.id.e5);
      //  e66 = view.findViewById(R.id.e6);

        radioGroup = (RadioGroup) view.findViewById(R.id.radio);

        e77 = view.findViewById(R.id.e7);
        e77.setOnClickListener(new View.OnClickListener() {
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

        e88=view.findViewById(R.id.duration);
        e99=view.findViewById(R.id.area);
        add = view.findViewById(R.id.add_field_button);
        add_chemical=view.findViewById(R.id.add_field_button_chemical);
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
        container_chemical=(LinearLayout) view.findViewById(R.id.container_chemical);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.field, null);



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
        add_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.field, null);




                Button delete = addView.findViewById(R.id.delete_button);
                final EditText textOut = (EditText) addView.findViewById(R.id.number_edit_text);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        container_chemical.removeView((View) view.getParent());

                    }
                });
               container_chemical.addView(addView, 0);

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

        if (e77.getText().length() == 0) {
            e77.setError("Field can not be left blank");
            e77.requestFocus();
        }
        if (e88.getText().length() == 0) {
            e88.setError("Field can not be left blank");
            e88.requestFocus();
        }
        if (e99.getText().length() == 0) {
            e99.setError("Field can not be left blank");
            e99.requestFocus();
        }
        else {
            final String crop_name_text = e11.getText().toString();
            String crop_quantity_text = e22.getText().toString();

            String sold_to = e77.getText().toString();
            String area = e88.getText().toString();
            String duration = e88.getText().toString();
            getEditTextList();
            getChemicalList();
           // Toast.makeText(getContext(), list.get(0 ), Toast.LENGTH_LONG).show();
            getToken();
           FirebaseAuth mAuth=FirebaseAuth.getInstance();
           FirebaseUser currentUser=mAuth.getCurrentUser();
            final String user_id=mAuth.getCurrentUser().getUid();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String currentDateandTime = sdf.format(new Date());
            DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference().child("Product_Info").child(token.toString());
            Map remaining_info=new HashMap();
            remaining_info.put("crop_name",crop_name_text);
            remaining_info.put("crop_quantity",crop_quantity_text);
           remaining_info.put("chemical_used ",list_chemical);
           remaining_info.put("chemical_quantity",list_chemical_qunatity);
            remaining_info.put("sold_to",sold_to);
            remaining_info.put("Fertilizer_name",list_names);
            remaining_info.put("Fertilizer_quantity",list_quantity);
            remaining_info.put("Farmer_ID",user_id);
            remaining_info.put("Date_time",currentDateandTime);
            remaining_info.put("Duration",duration);
            remaining_info.put("area",area);
            remaining_info.put("Market_ID"," ");

            current_user_db.setValue(remaining_info);

            waiting_dialog.show();




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), token.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Batch_no.class);
                    intent.putExtra("message", token.toString()+"/"+crop_name_text);
                    startActivity(intent);
                    waiting_dialog.dismiss();
                    getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                }
            }, 3000);


        }

    }

    private void getChemicalList() {

        int childCount =container_chemical.getChildCount();
        list_chemical=list_chemical_qunatity="";


        String childTextViewText = null;
        for(int c=0; c<childCount; c++){
            View childView = container_chemical.getChildAt(c);
            TextView childTextView = (TextView)(childView.findViewById(R.id.number_edit_text));

            String text = ((Spinner)childView.findViewById(R.id.type_spinner)).getSelectedItem().toString();
            childTextViewText = (childTextView.getText().toString());
            list_chemical=childTextViewText+","+list_chemical;
            list_chemical_qunatity=text+","+list_chemical_qunatity;

            //  Toast.makeText(getContext(),childTextViewText+text,Toast.LENGTH_LONG).show();


        }

    }




    public void getToken() {
        Random r = new Random();
        token = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            token.append(CHARS.charAt(r.nextInt(CHARS.length())));
        }

    }


    public void getimages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selectl" +
                " Picture"), 1);
    }

    void getEditTextList() {

        int childCount = container_linear.getChildCount();
        list_quantity=list_names="";


        String childTextViewText = null;
        for(int c=0; c<childCount; c++){
            View childView = container_linear.getChildAt(c);
            TextView childTextView = (TextView)(childView.findViewById(R.id.number_edit_text));

            String text = ((Spinner)childView.findViewById(R.id.type_spinner)).getSelectedItem().toString();
            childTextViewText = (childTextView.getText().toString());
            list_names=childTextViewText+","+list_names;
            list_quantity=text+","+list_quantity;

          //  Toast.makeText(getContext(),childTextViewText+text,Toast.LENGTH_LONG).show();


        }

        }


    }

