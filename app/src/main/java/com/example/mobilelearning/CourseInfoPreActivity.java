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
import com.example.mobilelearning.utils.Books;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoPreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_pre);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        ImageView courseImage = findViewById(R.id.course_image);
        TextView courseTitle = findViewById(R.id.course_title);
        TextView courseCode = findViewById(R.id.course_code);
        TextView courseDes = findViewById(R.id.course_description);

        //First Lecturer
       // ImageView lectImage1 = findViewById(R.id.lecturer_photo_1);
        final TextView lectName1 = findViewById(R.id.lecturer_name_1);

        //Second Lecturer
        final CardView card2 = findViewById(R.id.lecturer_card_2);
     //   ImageView lectImage2 = findViewById(R.id.lecturer_photo_2);
        final TextView lectName2 = findViewById(R.id.lecturer_name_2);

        //Third Lecturer
        final CardView card3 = findViewById(R.id.lecturer_card_3);
     //   ImageView lectImage3 = findViewById(R.id.lecturer_photo_3);
        final TextView lectName3 = findViewById(R.id.lecturer_name_3);

        //Toolbar
        Toolbar tool = findViewById(R.id.toolbar_course_pre_sub);
        tool.setOnClickListener(new View.OnClickListener() {
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
        final String[] lecturerData = i.getStringArrayExtra("Course Lecturer");
        final String courseDescription = i.getStringExtra("Course Description");
        final String[] books =i.getStringArrayExtra("Course Books");
        final String[] courseOut1 = i.getStringArrayExtra("Course Out1");
        final String[] courseOut2 = i.getStringArrayExtra("Course Out2");
        final String[] courseOut3 = i.getStringArrayExtra("Course Out3");


        //Populating courseinfo pre reg
        courseTitle.setText(courseTitleData);
        courseCode.setText(courseCodeData);
        Glide.with(courseImage.getContext()).load(courseImageData).into(courseImage);
        courseDes.setText(courseDescription);

        //for lecturer name
        if(lecturerData.length == 1){
            lectName1.setText(lecturerData[0]);
            card2.setVisibility(View.GONE);
            card3.setVisibility(View.GONE);
        }else if (lecturerData.length == 2){
            lectName1.setText(lecturerData[0]);
            lectName2.setText(lecturerData[1]);
            card3.setVisibility(View.GONE);
        }else if(lecturerData.length ==3){
            lectName1.setText(lecturerData[0]);
            lectName2.setText(lecturerData[1]);
            lectName3.setText(lecturerData[2]);
        }


        //Floating Action Button
        FloatingActionButton addCourse = findViewById(R.id.add_course);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseInfoPreActivity.this,CourseInfoPostActivity.class);
                intent.putExtra("Course Title",courseTitleData);
                intent.putExtra("Course Code",courseCodeData);
                intent.putExtra("Course Image",courseImageData);
                intent.putExtra("Course Lecturer",lecturerData);
                intent.putExtra("Course Description",courseDescription);
                intent.putExtra("Course Books",books);
                intent.putExtra("Course Out1",courseOut1);
                intent.putExtra("Course Out2",courseOut2);
                intent.putExtra("Course Out3",courseOut3);

                startActivity(intent);
                finish();
            }
        });


    }
}
