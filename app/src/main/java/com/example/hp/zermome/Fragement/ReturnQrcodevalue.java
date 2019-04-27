package com.example.hp.zermome.Fragement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.zermome.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class ReturnQrcodevalue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_qrcodevalue);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }
}
