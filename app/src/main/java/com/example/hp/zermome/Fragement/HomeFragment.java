package com.example.hp.zermome.Fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.zermome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    String name,DOB,address,email_address,location,gender,another_location;
    TextView txtlocation,nametxt;
    CircleImageView profile;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    RecyclerView recyclerView;

    private ArrayList<Profile> msg;




    public HomeFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View view= inflater.inflate(R.layout.fragment_home, container, false);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        final String user_id=mAuth.getCurrentUser().getUid();
        profile=(CircleImageView) view.findViewById(R.id.profile);
        txtlocation=(TextView) view.findViewById(R.id.location);
        nametxt=(TextView) view.findViewById(R.id.name);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(profile);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(mAuth.getUid());
        DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference().child("User_Resgisration").child(user_id);
        current_user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              name=dataSnapshot.child("name").getValue().toString();
              email_address=dataSnapshot.child("phone_no").getValue().toString();
              location=dataSnapshot.child("location").getValue().toString();
              gender=dataSnapshot.child("gender").getValue().toString();
              DOB=dataSnapshot.child("DOB").getValue().toString();
              txtlocation.setText(location);
              nametxt.setText(name);

                msg = new ArrayList<>();
                msg.add(new Profile("Eamil ID",email_address));
                msg.add(new Profile("Gender",gender));
                msg.add(new Profile("DOB",DOB));
                msg.add(new Profile("Location",location));
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

               profileAdapter adapter = new profileAdapter(msg);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                // adding custom divider line with padding 16dp
                // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }




}
