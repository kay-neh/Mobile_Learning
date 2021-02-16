package com.example.mobilelearning;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilelearning.utils.LecturerAdapter;
import com.example.mobilelearning.utils.Lecturers;
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

    RecyclerView lectRecyclerView;
    LecturerAdapter lecturerAdapter;

    List<Lecturers> selectedLecturers;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        final TextView courseDes = view.findViewById(R.id.course_description_post_reg);
        lectRecyclerView = view.findViewById(R.id.lect_recycler_view);

        selectedLecturers = new ArrayList<>();

        //retrieve data from host activity
        String courseId = getArguments().getString("Course Id");
        String courseDescription = getArguments().getString("Course Description");
        courseDes.setText(courseDescription);

        initAdap();

        databaseReference = firebaseDatabase.getReference("lecturers");
        databaseReference.orderByChild("courses/" + courseId).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    selectedLecturers.clear();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Lecturers lecturers = d.getValue(Lecturers.class);
                        selectedLecturers.add(lecturers);
                    }
                    //set lecturers view
                    lecturerAdapter.setLecturersList(selectedLecturers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    return view;
    }

    public void initAdap(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        lectRecyclerView.setLayoutManager(llm);
        lectRecyclerView.setHasFixedSize(true);
        lecturerAdapter = new LecturerAdapter(getContext());
        lectRecyclerView.setAdapter(lecturerAdapter);
    }

}
