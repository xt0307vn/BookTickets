package com.example.booktickets;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VerifyPasswordActivity extends AppCompatActivity {
    EditText vPassword_inputPassword, vPassword_inputPhone;
    Button vPassword_btnRegister;
    String userPhone;
    FirebaseFirestore fdb;
    FirebaseAuth mAuth;
    final String secretKey = "AZaz@0123456789";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_password);

        initID();
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        setinputPhone();



        vPassword_btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String inputPassword = vPassword_inputPassword.getText().toString();
                String encriptPassword = AES.encrypt(inputPassword, secretKey);
                if(!inputPassword.isEmpty()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("userPhone", userPhone);
                    user.put("userPassword", encriptPassword);

                    fdb.collection("users").add(user);


                    gotoLogin();

                } else {
                    Toast.makeText(VerifyPasswordActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initID() {
        vPassword_inputPassword = findViewById(R.id.vPassword_inputPassword);
        vPassword_btnRegister = findViewById(R.id.vPassword_btnRegister);
        vPassword_inputPhone = findViewById(R.id.vPassword_inputPhone);
    }

    public void setinputPhone() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userPhone = bundle.getString("userPhone");
            vPassword_inputPhone.setText(userPhone);
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(VerifyPasswordActivity.this, LoginActivity.class);
        intent.putExtra("userPhone", userPhone);
        startActivity(intent);
    }

}