package com.example.booktickets;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://appdatvexemphim-4ac35-default-rtdb.firebaseio.com");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);


        TextView txtfullname = (TextView) view.findViewById(R.id.txtnameprofile);
        TextView txtphone = (TextView) view.findViewById(R.id.txtphoneprofile);
        TextView txtemail = (TextView) view.findViewById(R.id.txtemailprofile);
        TextView txtdiachi = (TextView) view.findViewById(R.id.txtdiachiprofile);
        Button btnupdate = (Button) view.findViewById(R.id.btnupdateprofile);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),updata_profile.class));
            }
        });
        databaseReference.child("users/0899885260/fulname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullname = snapshot.getValue(String.class);
                txtfullname.setText(fullname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("users/0899885260/email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.getValue(String.class);
                txtemail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("users/0899885260/diachi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String diachi = snapshot.getValue(String.class);
                txtdiachi.setText(diachi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("users/0899885260/phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.getValue(String.class);
                txtphone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}