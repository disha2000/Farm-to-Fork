package com.example.hp.zermome.Fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;import com.example.hp.zermome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class BlankFragment extends Fragment {

    ArrayList<String> phoneNumbers,name,crop_name,crop_quantity,Occupation,price;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        final RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);




        //******************************************************AAAAAAAA****************************************************************



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Offer");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());
                        MyAdapter adapter = new MyAdapter(getContext(),phoneNumbers,name,crop_name,crop_quantity,price,Occupation);
                        rv.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private void collectPhoneNumbers(Map<String,Object> users) {

         phoneNumbers = new ArrayList<>();
         name= new ArrayList<>();
        crop_name = new ArrayList<>();
       crop_quantity = new ArrayList<>();
        price= new ArrayList<>();
        Occupation= new ArrayList<>();



        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("contact_no"));
           crop_name.add((String) singleUser.get("crop_name"));
            crop_quantity.add((String) singleUser.get("crop_quantity"));
           name.add((String) singleUser.get("name"));
          price.add((String) singleUser.get("price"));
          Occupation.add((String) singleUser.get("Occupation"));
        }
    }

}