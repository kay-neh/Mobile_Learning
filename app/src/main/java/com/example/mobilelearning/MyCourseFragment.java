package com.example.mobilelearning;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilelearning.utils.Courses;
import com.example.mobilelearning.utils.MyCoursesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCourseFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;

    public MyCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_course, container, false);

        final RecyclerView recycle = view.findViewById(R.id.recycler_view_my_course);
        final LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycle.setLayoutManager(llm);
        recycle.setHasFixedSize(true);

        //read list of my courses from firebase...
        FirebaseAuth mFireBaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(mFireBaseAuth.getCurrentUser()!= null) {
            DatabaseReference courseRef = firebaseDatabase.getReference("courses");
            courseRef.orderByChild("users/" + mFireBaseAuth.getCurrentUser().getUid()).equalTo(true).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<Courses> myCourses = new ArrayList<>();
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Courses courses = dataSnapshot1.getValue(Courses.class);
                            myCourses.add(courses);
                        }
                        final MyCoursesAdapter myadapt = new MyCoursesAdapter(myCourses, new MyCoursesAdapter.ListItemCourseClickListener() {
                            @Override
                            public void onListItemClick(int index) {
                                Intent intent = new Intent(getContext(), CourseInfoPostActivity.class);
                                //passing data from fragment to activity......
                                intent.putExtra("Course Id", myCourses.get(index).getCourseId());
                                intent.putExtra("Course Title", myCourses.get(index).getCourseName());
                                intent.putExtra("Course Code", myCourses.get(index).getCourseCode());
                                intent.putExtra("Course Image", myCourses.get(index).getCoursePhoto());
                                intent.putExtra("Course Description", myCourses.get(index).getCourseDescription());
                                startActivity(intent);
                            }
                        });
                        recycle.setAdapter(myadapt);
                        recycle.setVisibility(View.VISIBLE);
                    } else {
                        recycle.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return view;
    }
}