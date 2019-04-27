package com.example.hp.zermome;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AndroidListAdapter extends ArrayAdapter {
    String[] androidListViewStrings;
    Integer[] imagesId;
    Context context;

    public AndroidListAdapter(Activity context, Integer[] imagesId, String[] textListView) {
        super(context, R.layout.custom_list_item_layout, textListView);
        this.androidListViewStrings = textListView;
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.custom_list_item_layout, null,
                true);
        final TextView mtextView = (TextView) viewRow.findViewById(R.id.text_view);
        final ImageView mimageView = (ImageView) viewRow.findViewById(R.id.image_view);
        mtextView.setText(androidListViewStrings[i]);
        mimageView.setImageResource(imagesId[i]);

        mtextView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String str = mtextView.getText().toString();
                if(str=="Farmer")
                {
                    Intent intent=new Intent(getContext(),LoginActivity.class);
                    context.startActivity(intent);

                }
                else if(str=="Market")
                {

                    Intent intent=new Intent(getContext(),marketlogin.class);
                    context.startActivity(intent);

                }
                else if(str=="Customer")
                {
                    Intent intent=new Intent(getContext(),ScannerActivity.class);
                    context.startActivity(intent);

                }
                else if(str=="Processor")
                {
                    Intent intent=new Intent(getContext(),RetailerLogin.class);
                  context.startActivity(intent);

                }
            }
        });

        return viewRow;


    }





}