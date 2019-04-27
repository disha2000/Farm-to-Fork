package com.example.hp.zermome;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Arrays;

public class QrCode_genrator extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    String batch_id,buying_from,Retailer_id,product_name,Retailer_name,Fertilizer_name,Fertilizer_quantity,Farmer_id,Date_time,chemical_used;
    String crop_name,crop_quantity,sold_to,chemical_quantity,Farmer_name,location,area,duration,Market_ID;
    String message;
    String name_concat,market_name,market_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_genrator);
        imageView = (ImageView) findViewById(R.id.imageView3);
        textView = (TextView) findViewById(R.id.textView2);
        generateQR();
    }
    public void generateQR() {

        Bundle bundle = getIntent().getExtras();
       message = bundle.getString("message");
        DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference().child("Final_prodcut").child(message);
        current_user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(),"hooo",Toast.LENGTH_LONG).show();
                batch_id=dataSnapshot.child("Batch_id").getValue().toString();
                buying_from=dataSnapshot.child("Buying_from").getValue().toString();
                Retailer_id=dataSnapshot.child("Retailer_id").getValue().toString();
                //Toast.makeText(getApplicationContext(),Retailer_id,Toast.LENGTH_LONG).show();
                product_name=dataSnapshot.child("product_name").getValue().toString();
                DatabaseReference  current_user_db1=FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(Retailer_id);
                current_user_db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Retailer_name=dataSnapshot.child("name").getValue().toString();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference current_user_db2=FirebaseDatabase.getInstance().getReference().child("Product_Info").child(batch_id.trim());
                current_user_db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),batch_id,Toast.LENGTH_LONG).show();
                        Date_time=dataSnapshot.child("Date_time").getValue().toString();
                        Fertilizer_name=dataSnapshot.child("Fertilizer_name").getValue().toString();
                        Fertilizer_quantity=dataSnapshot.child("Fertilizer_quantity").getValue().toString();
                        Farmer_id=dataSnapshot.child("Farmer_ID").getValue().toString();
                        Market_ID=dataSnapshot.child("Market_ID").getValue().toString();
                        chemical_used=dataSnapshot.child("chemical_used ").getValue().toString();
                        crop_name=dataSnapshot.child("crop_name").getValue().toString();
                        sold_to=dataSnapshot.child("sold_to").getValue().toString();
                        chemical_quantity=dataSnapshot.child("chemical_quantity").getValue().toString();
                        area=dataSnapshot.child("area").getValue().toString();
                        duration=dataSnapshot.child("Duration").getValue().toString();
                        String sub_market=Market_ID.substring(5);
                        String []name_market=sub_market.split(",");
                        name_concat="";
                        for(int i=0;i<name_market.length;i++)
                        {
                           // Toast.makeText(getApplicationContext(),"HEllo"+name_market[i],Toast.LENGTH_LONG).show();
                            DatabaseReference current_user_db1=FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(name_market[i].trim());
                            final int finalI = i;
                            current_user_db1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    market_name=dataSnapshot.child("name").getValue().toString();
                                    market_location=dataSnapshot.child("location").getValue().toString();
                                   name_concat="\nmarket "+Integer.toString(finalI+1)+"\nNAME ="+market_name+"\nLocation:-"+market_location+"\n\n------------------------------\n";



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        textView.setText(Farmer_id);

                        DatabaseReference  current_user_db3=FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(Farmer_id);
                        current_user_db3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Farmer_name=dataSnapshot.child("name").getValue().toString();
                                location=dataSnapshot.child("location").getValue().toString();

                                String[] sperated_Fertilizer=Fertilizer_name.split(",");
                                String[] seprated_name=Fertilizer_quantity.split(",");
                                String[] seprated_chemical_name=chemical_used.split(",");
                                String[] seprated_chemical_quantity=chemical_quantity.split(",");
                                chemical_used="";
                                chemical_quantity="";
                                String name_quantity="";
                                for(int i=0;i<seprated_chemical_name.length;i++)
                                {
                                    String n=seprated_chemical_name[i];
                                    String q=seprated_chemical_quantity[i];
                                    chemical_used= chemical_used+n+" ="+q+"\n";


                                }

                                for(int i=0;i<sperated_Fertilizer.length;i++)
                                {
                                    String n=sperated_Fertilizer[i];
                                    String q=seprated_name[i];
                                    name_quantity=name_quantity+n+" ="+q+"\n";


                                }
                                String format="Farmer Name ="+Farmer_name+"\nCrop Name ="+crop_name+"\nField Location ="+location+"\nDuration of plantation ="+duration+"\nArea of plantation ="+area+"\nChemical name and qunatity\n"+chemical_used+"\nDate and Time ="+Date_time+"\n------------------------------\n"+"Fertilizer Used\n"+name_quantity+"\n------------------------------\n"+"Sold To= "+name_concat+"\n\nRetailer Information\n"+"------------------------------\n"+"Retailer name="+Retailer_name;
                                Toast.makeText(getApplicationContext(),format,Toast.LENGTH_LONG).show();
                                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();


                                try
                                {
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                    Bitmap bitmap = barcodeEncoder.encodeBitmap(format, BarcodeFormat.QR_CODE, 400, 400);
                                    imageView.setImageBitmap(bitmap);
                                }
                                catch (WriterException e)
                                {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
