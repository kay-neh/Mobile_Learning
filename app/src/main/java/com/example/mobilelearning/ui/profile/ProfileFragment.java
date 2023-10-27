package com.example.mobilelearning.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.data.pojo.User;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int RC_PHOTO_PICKER =  2;
    private FirebaseAuth firebaseAuth;
    String currentPhoto;
    BaseRepository baseRepository;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Access Repository
        baseRepository = new BaseRepository();

        //Sign out flow
        CardView signOut = view.findViewById(R.id.sign_out_button) ;
                signOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment exitDialog = new ExitDialog();
                        exitDialog.show(getFragmentManager(),"exit dialog");
                        exitDialog.setCancelable(false);
                    }
                });

        final TextView userName = view.findViewById(R.id.user_name);
        final TextView userEmail = view.findViewById(R.id.user_email);
        final ImageView profilePicture = view.findViewById(R.id.user_image);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null) {
            //setting username, email and profile picture for current user
            baseRepository.getUser(firebaseAuth.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user !=null) {
                        userName.setText(user.getUserName());
                        userEmail.setText(user.getUserEmail());
                        currentPhoto = user.getUserPhoto();
                        if(currentPhoto != null) {
                            Glide.with(getActivity().getApplicationContext()).load(currentPhoto).into(profilePicture);
                        }
                    }
                }
            });
        }

        //Choosing profile picture
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

/*
        // Dark mode behaviour flow
        final SwitchCompat darkModeSwitch = view.findViewById(R.id.dark_mode_button);

                if(darkModeSwitch.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //darkModeSwitch.setChecked(false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //darkModeSwitch.setChecked(true);
                }
*/
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            if(data!=null && firebaseAuth.getCurrentUser()!=null) {
                Uri selectedImageUri = data.getData();
                //Sending the chosen picture to firebase storage and creating a database reference to it
                baseRepository.updateProfilePhoto(firebaseAuth.getCurrentUser().getUid(), selectedImageUri, currentPhoto);
            }
        }
    }


}