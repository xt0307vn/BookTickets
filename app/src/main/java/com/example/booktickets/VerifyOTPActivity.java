package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOTPActivity extends AppCompatActivity {
    public static final String TAG = VerifyOTPActivity.class.toString();
    EditText verify_OTP;
    Button verify_next;
    String verificationID, userPhone, verifyOTPinput;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initID();
        verificationID = getIntent().getStringExtra("verificationID");
        userPhone = getIntent().getStringExtra("userPhone");
        mAuth = FirebaseAuth.getInstance();

        verify_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTPinput = verify_OTP.getText().toString();
                if(!verifyOTPinput.isEmpty()) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, verifyOTPinput);
                    signInWithPhoneAuthCredential(credential);
                } else {
                    Toast.makeText(VerifyOTPActivity.this, "Không để trống", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initID() {
        verify_OTP = findViewById(R.id.verify_OTP);
        verify_next = findViewById(R.id.verify_next);
    }

    public void getDataIntent() {
        verificationID = getIntent().getStringExtra("verificationID");
        userPhone = getIntent().getStringExtra("userPhone");
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            gotoVerifyPassword(user.getPhoneNumber());
                        } else {
                            Toast.makeText(VerifyOTPActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotoVerifyPassword(String phoneNumber) {
        Intent intent = new Intent(VerifyOTPActivity.this, VerifyPasswordActivity.class);
        intent.putExtra("userPhone", phoneNumber);
        startActivity(intent);
    }



}