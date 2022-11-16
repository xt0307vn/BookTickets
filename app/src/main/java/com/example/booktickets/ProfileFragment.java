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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    Button profile_Update , profile_ok;
    EditText profile_userEmail, profile_userPhone, profile_userFullname;

    FirebaseFirestore fdb;
    CollectionReference coll_users;

    MainActivity mainActivity;
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();


        initID(view);

        fdb = FirebaseFirestore.getInstance();
        coll_users = fdb.collection("users");

       coll_users.document(mainActivity.userID).
               get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if(task.isSuccessful()) {
                           DocumentSnapshot documentSnapshot = task.getResult();
                           profile_userEmail.setText(documentSnapshot.getString("userEmail"));
                           profile_userPhone.setText(documentSnapshot.getString("userPhone"));
                           profile_userFullname.setText(documentSnapshot.getString("userFullName"));
                       }
                   }
               });



        profile_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_ok.setVisibility(View.VISIBLE);
                profile_Update.setVisibility(View.INVISIBLE);
                profile_userEmail.setEnabled(true);
                profile_userFullname.setEnabled(true);

            }
        });

        profile_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_Update.setVisibility(View.VISIBLE);
                profile_ok.setVisibility(View.INVISIBLE);

                profile_userEmail.setEnabled(false);
                profile_userPhone.setEnabled(false);
                profile_userFullname.setEnabled(false);
                Map<String, Object> update = new HashMap<>();
                update.put("userFullName", profile_userFullname.getText().toString().trim());
                update.put("userEmail", profile_userFullname.getText().toString().trim());
                update.put("userPhone", profile_userPhone.getText().toString().trim());
                coll_users.document(mainActivity.userID).update(update)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });
    }

    private void initID(View view) {
        profile_Update = view.findViewById(R.id.profile_Update);
        profile_ok = view.findViewById(R.id.profile_ok);
        profile_userEmail = view.findViewById(R.id.profile_userEmail);
        profile_userPhone = view.findViewById(R.id.profile_userPhone);
        profile_userFullname = view.findViewById(R.id.profile_userFullname);

    }


}