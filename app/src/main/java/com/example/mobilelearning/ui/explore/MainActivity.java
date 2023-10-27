package com.example.mobilelearning.ui.explore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.ui.mycourses.MyCourseFragment;
import com.example.mobilelearning.ui.profile.ProfileFragment;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.pojo.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigate;
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Fragment fragment1,fragment2,fragment3,active;
    FragmentManager fm;
    String userId;
    String userName;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize FireBase tools
        firebaseAuth = FirebaseAuth.getInstance();

        // Access Repository
        final BaseRepository baseRepository = new BaseRepository();

        //Auth Listener Setup..
        authStateListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userId = firebaseUser.getUid();
                    userName = firebaseUser.getDisplayName();
                    userEmail = firebaseUser.getEmail();

                    User user = new User(userName, userEmail);
                    baseRepository.saveUser(user, userId);

                } else {
                    // not signed in
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false, true)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        //Loads all Fragments as Global variables
        fragment1 = new HomeFragment();
        fragment2 = new MyCourseFragment();
        fragment3 = new ProfileFragment();
        active = fragment1;
        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.frame_container, fragment1, "1").commit();
        fm.beginTransaction().add(R.id.frame_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment3, "3").hide(fragment3).commit();

        //Bottom Navigation Listener Setup..
        navigate = findViewById(R.id.navigation);
        navigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.button_home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.button_courses:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.button_profile:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                }
                return false;
            }
        }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN ){
            if (resultCode == RESULT_OK){
                navigate.setSelectedItemId(R.id.button_home);
            }else if (resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
