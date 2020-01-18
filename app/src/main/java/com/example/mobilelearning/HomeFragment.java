package com.example.mobilelearning;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilelearning.utils.Courses;
import com.example.mobilelearning.utils.ExploreAdapter;
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
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final RecyclerView recycle = view.findViewById(R.id.recycler_view_explore);
        final GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        recycle.setLayoutManager(glm);
        recycle.setHasFixedSize(true);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("courses");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Courses> course  = new ArrayList<>();
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   Courses c = dataSnapshot1.getValue(Courses.class);
                   course.add(c);
                }
                ExploreAdapter adapt = new ExploreAdapter(course, new ExploreAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(int index) {
                        Intent intent = new Intent(getContext(), CourseInfoPreActivity.class);
                        //passing data from fragment to activity......
                        intent.putExtra("Course Title",course.get(index).getCourseTitle());
                        intent.putExtra("Course Code",course.get(index).getCourseCode());
                        intent.putExtra("Course Image",course.get(index).getCourseImg());
                        intent.putExtra("Course Description",course.get(index).getCourseDescription());
                        startActivity(intent);
                    }
                });
                recycle.setAdapter(adapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        };
        databaseReference.addValueEventListener(valueEventListener);
        return view;
    }
}