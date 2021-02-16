package com.example.mobilelearning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.utils.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int RC_PHOTO_PICKER =  2;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    String currentPhoto;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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

        //setting username, email and profile picture for current user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("photos/user_photo");
        firebaseDatabase = FirebaseDatabase.getInstance();

        if(firebaseAuth.getCurrentUser()!=null) {
            DatabaseReference databaseReference = firebaseDatabase.getReference("users/" + firebaseAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if(users!=null) {
                        userName.setText(users.getUserName());
                        userEmail.setText(users.getUserEmail());
                        currentPhoto = users.getUserPhoto();
                        if(currentPhoto != null) {
                            Glide.with(getActivity().getApplicationContext()).load(currentPhoto).into(profilePicture);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
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

    //Sending the chosen picture to firebase storage and creating a database reference to it
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            if(data!=null && firebaseAuth.getCurrentUser()!=null) {
                Uri selectedImageUri = data.getData();
                final StorageReference profilePhotoRef = storageReference.child(firebaseAuth.getCurrentUser().getUid() + "/" + selectedImageUri.getLastPathSegment());
                profilePhotoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        profilePhotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference ref2 = firebaseDatabase.getReference("users/" + firebaseAuth.getCurrentUser().getUid() + "/userPhoto");
                                if(currentPhoto != null){
                                    StorageReference ref = firebaseStorage.getReferenceFromUrl(currentPhoto);
                                    ref.delete();
                                }
                                ref2.setValue(uri.toString());
                            }
                        });
                    }
                });
            }

        }
    }


}