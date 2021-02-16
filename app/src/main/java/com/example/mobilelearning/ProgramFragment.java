package com.example.mobilelearning;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mobilelearning.utils.CourseOutline;
import com.example.mobilelearning.utils.CourseOutlineAdapter;
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
public class ProgramFragment extends Fragment {


    public ProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final ListView courseOutlineListView = view.findViewById(R.id.course_outline_list_view);

        //retrieve data from host activity
        String courseId = getArguments().getString("Course Id");

        DatabaseReference databaseReference = firebaseDatabase.getReference("courses/"+courseId+"/courseOutline");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<CourseOutline> courseOutlines = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CourseOutline courseOutline = dataSnapshot1.getValue(CourseOutline.class);
                    courseOutlines.add(courseOutline);
                }
                CourseOutlineAdapter courseOutlineAdapter = new CourseOutlineAdapter(getContext(),courseOutlines);
                courseOutlineListView.setAdapter(courseOutlineAdapter);
                courseOutlineListView.setClickable(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return view;
    }

}
