package com.example.hp.zermome.Fragement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.zermome.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static android.Manifest.permission.CALL_PHONE;
import static android.support.v4.app.ActivityCompat.requestPermissions;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> phoneno,name_array,crop_name,crop_quantity,price,Occupation;
    private int[] images;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;

        public TextView name,Quantity,price,crop_name_textview,occupation_textview,phoneno_text;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);

           // mCardView = (CardView) v.findViewById(R.id.card_view);
           name = (TextView) v.findViewById(R.id.textViewTitle);
           Quantity=(TextView)v.findViewById(R.id.valueQuantity);
           price=(TextView) v.findViewById(R.id.textViewPrice);
           crop_name_textview=(TextView)v.findViewById(R.id.crop_name);
           occupation_textview=(TextView)v.findViewById(R.id.occupation);
           phoneno_text=(TextView)v.findViewById(R.id.phoneno_text);
           imageView=(ImageView)v.findViewById(R.id.phoneno);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context,ArrayList<String> phoneno, ArrayList<String> name_array, ArrayList<String> crop_name,ArrayList<String> crop_Quantity,ArrayList<String> price,ArrayList<String> Occupation) {
        this.name_array = name_array;
        this.crop_name=crop_name;
        this.price=price;
        this.phoneno=phoneno;
        this.crop_quantity=crop_Quantity;
        this.Occupation=Occupation;
        this.context=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        final MyViewHolder vh = new MyViewHolder(v);
        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_string=vh.phoneno_text.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_string));//change the number
                Toast.makeText(context,phone_string,Toast.LENGTH_LONG).show();
                context.startActivity(callIntent);



            }
        });


        return vh;
    }






    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText("Name:-"+name_array.get(position));
        holder.Quantity.setText(crop_quantity.get(position));
        holder.price.setText("INR "+price.get(position));
        holder.crop_name_textview.setText("Crop Name:-"+crop_name.get(position));
        holder.occupation_textview.setText(Occupation.get(position));
        holder.phoneno_text.setText(phoneno.get(position));
    }

    @Override
    public int getItemCount() {
        return name_array.size();
    }
}