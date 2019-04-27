package com.example.hp.zermome.Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.zermome.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;


@SuppressLint("ValidFragment")
public class MARKETQRCODE extends Fragment {
    ImageView image;
    String message;

    @SuppressLint("ValidFragment")
    public MARKETQRCODE(String user_id) {
        this.message=user_id;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view=inflater.inflate(R.layout.fragment_marketqrcode, container, false);
       image=view.findViewById(R.id.imageView3);
       genrateQRCode();
       return  view;
    }

    private void genrateQRCode() {


        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try
        {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(message, BarcodeFormat.QR_CODE, 400, 400);
            image.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }


}
