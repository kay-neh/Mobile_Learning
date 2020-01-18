package com.example.mobilelearning;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        final TextView lectName1 = view.findViewById(R.id.lecturer_name_1_post_reg);
        final CardView card2 = view.findViewById(R.id.lecturer_card_2_post_reg);
        final TextView lectName2 = view.findViewById(R.id.lecturer_name_2_post_reg);
        final CardView card3 = view.findViewById(R.id.lecturer_card_3_post_reg);
        final TextView lectName3 = view.findViewById(R.id.lecturer_name_3_post_reg);
        final TextView courseDes = view.findViewById(R.id.course_description_post_reg);

        //retrieve data from host activity
        String courseTitleData = getArguments().getString("Course Title");
        String courseDescription = getArguments().getString("Course Description");
        courseDes.setText(courseDescription);

        databaseReference = firebaseDatabase.getReference("courses/"+courseTitleData+"/courseLecturer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> lecturerData = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String lect = (String) dataSnapshot1.getValue();
                    lecturerData.add(lect);
                }
                //for lecturer name
                if(lecturerData.size() == 1){
                    lectName1.setText(lecturerData.get(0));
                    card2.setVisibility(View.GONE);
                    card3.setVisibility(View.GONE);
                }else if (lecturerData.size() == 2){
                    lectName1.setText(lecturerData.get(0));
                    lectName2.setText(lecturerData.get(1));
                    card3.setVisibility(View.GONE);
                }else if(lecturerData.size() ==3){
                    lectName1.setText(lecturerData.get(0));
                    lectName2.setText(lecturerData.get(1));
                    lectName3.setText(lecturerData.get(2));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    return view;
    }

}
