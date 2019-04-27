package com.example.hp.zermome.Fragement;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class Fruit_crop extends Fragment {

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

    public Fruit_crop() {
        // Required empty public constructor
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
                        solt_to_editText.setText("name:-"+name+" Location:-"+location);



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


    EditText duration_editText,area_editText,solt_to_editText;
    String text,text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view= inflater.inflate(R.layout.fragment_fruit_crop, container, false);
        duration_editText=view.findViewById(R.id.duration);
        area_editText=view.findViewById(R.id.area);
        solt_to_editText=view.findViewById(R.id.e7);
        spinner1=view.findViewById(R.id.spinner1);
        String list_of_fruits[]={"Select fruit","mango","Apple"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_of_fruits);
        spinner1.setAdapter(adapter);
        spinner2=view.findViewById(R.id.spinner2);

        String list_of_mango[]={"Select species","Alphonsos","Badami","Chaunsa","Dasheri","Kesar","Langra"};
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_of_mango);
        spinner2.setAdapter(adapter2);

        solt_to_editText.setOnClickListener(new View.OnClickListener() {
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

        if (solt_to_editText.getText().toString().length() == 0) {
            solt_to_editText.setError("Field can not be left blank");
            solt_to_editText.requestFocus();
        }
        if (duration_editText.getText().length() == 0) {
            duration_editText.setError("Field can not be left blank");
            duration_editText.requestFocus();
        }

        if (area_editText.getText().length() == 0) {
            area_editText.setError("Field can not be left blank");
            area_editText.requestFocus();
        }
        else {
          //  final String crop_name_text = e11.getText().toString();
            //String crop_quantity_text = e22.getText().toString();
            text = spinner1.getSelectedItem().toString();
            text2=spinner2.getSelectedItem().toString();
            String sold_to = solt_to_editText.getText().toString();
            String area = area_editText.getText().toString();
            String duration = duration_editText.getText().toString();
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
            remaining_info.put("crop_name",text);
            remaining_info.put("crop_quantity",text2);
            remaining_info.put("chemical_used ",list_chemical);
            remaining_info.put("chemical_quantity",list_chemical);
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
                    intent.putExtra("message", token.toString()+"/"+text);
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

           // String text = ((Spinner)childView.findViewById(R.id.type_spinner)).getSelectedItem().toString();
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

          //  String text = ((Spinner)childView.findViewById(R.id.type_spinner)).getSelectedItem().toString();
            childTextViewText = (childTextView.getText().toString());
            list_names=childTextViewText+","+list_names;
            list_quantity=text+","+list_quantity;

            //  Toast.makeText(getContext(),childTextViewText+text,Toast.LENGTH_LONG).show();


        }

    }


}


