package com.example.booktickets;

import static com.example.booktickets.R.drawable.custom_backgroundedt_borderbrown;
import static com.example.booktickets.R.drawable.custom_backgroundedt_borderred;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    EditText login_userPhone, login_userPassword;
    TextView login_gotoRegister;
    Button login_btnLogin;
    FirebaseDatabase db;
    DatabaseReference mRef;
    FirebaseFirestore fdb;
    CollectionReference coll_users;
    final String secretKey = "AZaz@0123456789";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initID();
        db = FirebaseDatabase.getInstance();
        DatabaseReference mRef = db.getReference().child("users");
        fdb = FirebaseFirestore.getInstance();
        coll_users = fdb.collection("users");

        login_gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterPhoneActivity.class));
            }
        });


        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String userPhone = "+84" + handlerPhone(login_userPhone.getText().toString().trim());
                String userPassword = login_userPassword.getText().toString().trim();

                String up = AES.encrypt(userPassword, secretKey);
                String de = AES.decrypt(up, secretKey);

                if(login_userPhone.getText().toString().trim().isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {

                    coll_users.whereEqualTo("userPhone", userPhone)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if(task.getResult().size() != 0) {
                                            coll_users.whereEqualTo("userPhone", userPhone)
                                                    .whereEqualTo("userPassword", up)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()) {
                                                                if(task.getResult().size() != 0) {
                                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                                    gotoMain();
                                                                } else {
                                                                    Toast.makeText(LoginActivity.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Số điện thoại chưa đăng ký.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }


    public void initID() {
        login_userPhone = findViewById(R.id.login_userPhone);
        login_userPassword = findViewById(R.id.login_userPassword);
        login_gotoRegister = findViewById(R.id.login_gotoRegister);
        login_btnLogin = findViewById(R.id.login_btnLogin);
    }

    public String handlerPhone(String phone) {
        if(phone.startsWith("0")) {
            return phone.substring(1, phone.length());
        }
        return phone;
    }

    private void gotoMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}