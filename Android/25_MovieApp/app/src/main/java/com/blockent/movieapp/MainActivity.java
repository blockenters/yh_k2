package com.blockent.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    Fragment firstFragment;
    Fragment secondFragment;
    Fragment thirdFragment;
    Fragment forthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottomNavigationView);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        forthFragment = new ForthFragment();

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                Fragment fragment = null;

                if(itemId == R.id.firstFragment){
                    fragment = firstFragment;
                }else if(itemId == R.id.secondFragment){
                    fragment = secondFragment;
                }else if(itemId == R.id.thirdFragment){
                    fragment = thirdFragment;
                }else if(itemId == R.id.forthFragment){
                    fragment = forthFragment;
                }

                return loadFragment(fragment);
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}






