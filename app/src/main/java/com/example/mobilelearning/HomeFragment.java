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

import com.example.mobilelearning.utils.Books;
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
        final List<Courses> course = new ArrayList<>();

        final RecyclerView recycle = view.findViewById(R.id.recycler_view_explore);
        final GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        recycle.setLayoutManager(glm);
        recycle.setHasFixedSize(true);




        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("courses");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String courseCode = (String)dataSnapshot1.child("courseCode").getValue();

                    String courseImage = (String)dataSnapshot1.child("courseImg").getValue();

                    String courseTitle = (String)dataSnapshot1.child("courseTitle").getValue();

                    String courseDescription = (String)dataSnapshot1.child("courseDescription").getValue();


                    String bookName =(String) dataSnapshot1.child("books/book1/bookName").getValue();
                    String bookSize = (String) dataSnapshot1.child("books/book1/bookSize").getValue();
                    String bookDownloadUrl = (String) dataSnapshot1.child("books/book1/bookDownloadUrl").getValue();

                    String[]books ={bookName,bookSize,bookDownloadUrl};

                   String courseLect1 = (String)dataSnapshot1.child("courseLecturer/Lecturer1").getValue();

                   String courseLect2 = (String)dataSnapshot1.child("courseLecturer/Lecturer2").getValue();

                   //Course Outline 1
                   String courseIndex1 = (String) dataSnapshot1.child("courseOutline/outline1/index").getValue();
                   String courseOutTitle1 = (String) dataSnapshot1.child("courseOutline/outline1/title").getValue();
                   String courseOutContent1 = (String) dataSnapshot1.child("courseOutline/outline1/content").getValue();
                   String [] courseOut1 = {courseIndex1,courseOutTitle1,courseOutContent1};

                   //Course Outline 2
                   String courseIndex2 = (String) dataSnapshot1.child("courseOutline/outline2/index").getValue();
                   String courseOutTitle2 = (String) dataSnapshot1.child("courseOutline/outline2/title").getValue();
                   String courseOutContent2 = (String) dataSnapshot1.child("courseOutline/outline2/content").getValue();
                   String [] courseOut2 = {courseIndex2,courseOutTitle2,courseOutContent2};


                   //Course Outline 3
                   String courseIndex3 = (String) dataSnapshot1.child("courseOutline/outline3/index").getValue();
                   String courseOutTitle3 = (String) dataSnapshot1.child("courseOutline/outline3/title").getValue();
                   String courseOutContent3 = (String) dataSnapshot1.child("courseOutline/outline3/content").getValue();
                   String [] courseOut3 = {courseIndex3,courseOutTitle3,courseOutContent3};


                   if(dataSnapshot1.child("courseLecturer/Lecturer3").getValue() != null){
                       String courseLect3 = (String)dataSnapshot1.child("courseLecturer/Lecturer3").getValue();
                       String []  lect = {courseLect1,courseLect2,courseLect3};
                       course.add(new Courses(courseCode,courseImage,courseTitle,lect,courseDescription,books,courseOut1,courseOut2,courseOut3));
                   }else{
                       String [] lect = {courseLect1,courseLect2};
                       course.add(new Courses(courseCode,courseImage,courseTitle,lect,courseDescription,books,courseOut1,courseOut2,courseOut3));
                   }



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
                        intent.putExtra("Course Lecturer",course.get(index).getCourseLecturer());
                        intent.putExtra("Course Books",course.get(index).getBooks());
                        intent.putExtra("Course Out1",course.get(index).getCourseOut1());
                        intent.putExtra("Course Out2",course.get(index).getCourseOut2());
                        intent.putExtra("Course Out3",course.get(index).getCourseOut3());
                        startActivity(intent);
                    }
                });

                recycle.setAdapter(adapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        }
        );
    //    String [] lect = {"Engr. Dr. E. Olaye","Engr. Eguagie","Engr. James"};
        return view;
    }

}
