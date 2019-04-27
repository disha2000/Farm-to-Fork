package com.example.hp.zermome.Fragement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.zermome.R;
import com.example.hp.zermome.show_product_info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class Forward_product extends Fragment {


    TextView sold_to,batch_id;
    String Content,name,location,batch_text;
    Button submit;
    String market_ID;


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
                        sold_to.setText("name:-"+name+" Location:-"+location);



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

        View view= inflater.inflate(R.layout.fragment_forward_product, container, false);
        batch_id=view.findViewById(R.id.Batch_id);

        sold_to=view.findViewById(R.id.Buying_from);
        sold_to.setOnClickListener(new View.OnClickListener() {
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
        submit=view.findViewById(R.id.button3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batch_text=batch_id.getText().toString();
               callme();
               final String finalvalue=market_ID+","+Content;


                Toast.makeText(getContext(),market_ID,Toast.LENGTH_LONG).show();
                final DatabaseReference current_user_db1=FirebaseDatabase.getInstance().getReference().child("Product_Info").child(batch_text.trim());
                current_user_db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        current_user_db1.child("Market_ID").setValue(finalvalue);
                        batch_id.setText("");
                        sold_to.setText("");
                        Toast.makeText(getContext(),"Successfully Added",Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        return  view;
    }

    private void callme() {
        final DatabaseReference current_user_db1=FirebaseDatabase.getInstance().getReference().child("Product_Info").child(batch_text.trim());
        current_user_db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 market_ID=dataSnapshot.child("Market_ID").getValue().toString();
             //   current_user_db1.child("Market_ID").setValue(market_ID+","+Content);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
