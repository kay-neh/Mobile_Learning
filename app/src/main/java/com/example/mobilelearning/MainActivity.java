package com.example.mobilelearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobilelearning.utils.Users;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

        BottomNavigationView navigate;

        //Auth tools
        private static final int RC_SIGN_IN = 123;
        private FirebaseAuth mFireBaseAuth;
        private FirebaseAuth.AuthStateListener mAuthStateListener;

        //Realtime Db tools
        private FirebaseDatabase mFireBaseDatabase;
        private DatabaseReference mDbref;


        //Loads all Fragments as Global variables
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment3 = new MyCourseFragment();
        final Fragment fragment4 = new ProfileFragment();
        Fragment active = fragment1;
        final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Initialize FireBase tools
        mFireBaseAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFireBaseDatabase.setPersistenceEnabled(true);
        mDbref = mFireBaseDatabase.getReference("users");

        fm.beginTransaction().add(R.id.frame_container, fragment1, "1").commit();
        fm.beginTransaction().add(R.id.frame_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment4, "4").hide(fragment4).commit();



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
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                    case R.id.button_profile:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;
                }
                return false;
            }
        }
        );
        //Auth Listener Setup..
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // already signed in
                    //Dynamically adding new users to the database
                    Users users = new Users(firebaseAuth.getCurrentUser().getDisplayName(),firebaseAuth.getCurrentUser().getEmail());
                    mDbref.child(firebaseAuth.getCurrentUser().getUid()).setValue(users);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){

                
                navigate.setSelectedItemId(R.id.button_home);
            }else if (resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFireBaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
