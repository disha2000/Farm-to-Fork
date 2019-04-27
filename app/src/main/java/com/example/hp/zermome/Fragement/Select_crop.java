package com.example.hp.zermome.Fragement;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hp.zermome.R;


public class Select_crop extends Fragment {

    Spinner spinner;
    Button Go;

    public Select_crop() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_select_crop, container, false);
        Go=view.findViewById(R.id.Go);
        spinner=view.findViewById(R.id.spinner1);
        String list_of_crop[]={"Vegetables","Fruits","Grains"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,list_of_crop);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if ((String) parent.getItemAtPosition(position) == "vegetables") {



                } else if ((String) parent.getItemAtPosition(position) == "Fruits") {

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                if(text=="Vegetables")
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FarmerCrop()).commit();
                    getActivity().overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
                }
                else if(text=="Fruits")
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new Fruit_crop()).commit();
                    getActivity().overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );

                }
                else
                {

                }

            }
        });


        return view;
    }



}
