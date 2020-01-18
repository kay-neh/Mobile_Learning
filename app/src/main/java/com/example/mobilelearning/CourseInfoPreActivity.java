package com.example.mobilelearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.utils.Courses;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoPreActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_pre);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ImageView courseImage = findViewById(R.id.course_image);
        TextView courseTitle = findViewById(R.id.course_title);
        TextView courseCode = findViewById(R.id.course_code);
        TextView courseDes = findViewById(R.id.course_description);

        //First Lecturer
        //ImageView lectImage1 = findViewById(R.id.lecturer_photo_1);
        final TextView lectName1 = findViewById(R.id.lecturer_name_1);

        //Second Lecturer
        final CardView card2 = findViewById(R.id.lecturer_card_2);
     // ImageView lectImage2 = findViewById(R.id.lecturer_photo_2);
        final TextView lectName2 = findViewById(R.id.lecturer_name_2);

        //Third Lecturer
        final CardView card3 = findViewById(R.id.lecturer_card_3);
     // ImageView lectImage3 = findViewById(R.id.lecturer_photo_3);
        final TextView lectName3 = findViewById(R.id.lecturer_name_3);

        //Toolbar
        Toolbar tool = findViewById(R.id.toolbar_course_pre_sub);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Get Data from main activity for courseinfo post reg
        Intent i = getIntent();
        final String courseTitleData = i.getStringExtra("Course Title");
        final String courseCodeData = i.getStringExtra("Course Code");
        final String courseImageData = i.getStringExtra("Course Image");
        final String courseDescription = i.getStringExtra("Course Description");


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

        //Populating courseinfo pre reg
        courseTitle.setText(courseTitleData);
        courseCode.setText(courseCodeData);
        Glide.with(courseImage.getContext()).load(courseImageData).into(courseImage);
        courseDes.setText(courseDescription);

        //Floating Action Button
        final ExtendedFloatingActionButton addCourse = findViewById(R.id.add_course);
        final ExtendedFloatingActionButton removeCourse = findViewById(R.id.remove_course);

        //Switches the FAB between register and unregister based on users courselist
        DatabaseReference ref2 = firebaseDatabase.getReference("users/"+firebaseAuth.getCurrentUser().getUid());
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("my courses/"+courseTitleData)) {
                        addCourse.setVisibility(View.GONE);
                        removeCourse.setVisibility(View.VISIBLE);
                    }else{
                        addCourse.setVisibility(View.VISIBLE);
                        removeCourse.setVisibility(View.GONE);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref2 = firebaseDatabase.getReference("users/"+firebaseAuth.getCurrentUser().getUid()+"/my courses");
                Courses courses = new Courses(courseCodeData,courseTitleData,courseImageData,courseDescription);
                ref2.child(courseTitleData).setValue(courses);
                Toast.makeText(CourseInfoPreActivity.this,""+courseTitleData+" added to my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        removeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref2 = firebaseDatabase.getReference("users/"+firebaseAuth.getCurrentUser().getUid()+"/my courses");
                ref2.child(courseTitleData).setValue(null);
                Toast.makeText(CourseInfoPreActivity.this,""+courseTitleData+" removed from my courses",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}