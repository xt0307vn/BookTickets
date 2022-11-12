package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class RegisterPhoneActivity extends AppCompatActivity {
    public static final String TAG = RegisterPhoneActivity.class.toString();
    EditText register_userPhone;
    TextView register_gotoRegister;
    Button register_next;
    FirebaseAuth mAuth;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseFirestore fdb;
    CollectionReference coll_users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        initID();
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        coll_users = fdb.collection("users");

        register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhone = "+84" + handlerPhone(register_userPhone.getText().toString().trim());
                if(register_userPhone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterPhoneActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    coll_users.whereEqualTo("userPhone",userPhone)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if(task.getResult().size() != 0) {
                                            Toast.makeText(RegisterPhoneActivity.this, "Đã tồn tại số điện thoại", Toast.LENGTH_SHORT).show();
                                        } else {
                                            verifyPhoneNumber(userPhone);
                                        }
                                    } else {
                                        Toast.makeText(RegisterPhoneActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });

        register_gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin();
            }
        });

    }

    private void gotoLogin() {
        startActivity(new Intent(RegisterPhoneActivity.this, LoginActivity.class));
    }


    public void initID() {
        register_userPhone = findViewById(R.id.register_userPhone);
        register_next = findViewById(R.id.register_next);
        register_gotoRegister = findViewById(R.id.register_gotoRegister);
    }

    private void verifyPhoneNumber(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                signInWithPhoneAuthCredential(credential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(RegisterPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                gotoVerifyOTP(verificationId, phoneNumber);
                            }
                        }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            gotoVerifyPassword(user.getPhoneNumber());
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }



    private void gotoVerifyPassword(String phoneNumber) {
        Intent intent = new Intent(RegisterPhoneActivity.this, VerifyPasswordActivity.class);
        intent.putExtra("userPhone", phoneNumber);
        startActivity(intent);
    }


    private void gotoVerifyOTP(String verificationID, String phone) {
        Intent intent = new Intent(RegisterPhoneActivity.this, VerifyOTPActivity.class);
        intent.putExtra("verificationID", verificationID);
        intent.putExtra("userPhone", phone);
        startActivity(intent);
    }

    public String handlerPhone(String phone) {
        if(phone.startsWith("0")) {
            return phone.substring(1, phone.length());
        }
        return phone;
    }

}