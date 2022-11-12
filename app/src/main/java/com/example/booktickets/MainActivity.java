package com.example.booktickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button main_logout;
    FirebaseAuth auth;
    MoviesFragment moviesFragment;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_logout = findViewById(R.id.main_logout);
        auth = FirebaseAuth.getInstance();
        moviesFragment = new MoviesFragment();
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();
        bottomNavigationView = findViewById(R.id.main_navigation_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, moviesFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_movies:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, moviesFragment).commit();
                        return true;
                    case R.id.menu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });

        main_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, RegisterPhoneActivity.class));
                finish();
            }
        });



    }



}