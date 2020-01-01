package com.example.mobilelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.utils.Books;
import com.example.mobilelearning.utils.SimpleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CourseInfoPostActivity extends AppCompatActivity {

    private SimpleFragmentPagerAdapter adapt;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_post);


        Toolbar tool = findViewById(R.id.toolbar_course_post_sub);
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView courseTitle = findViewById(R.id.course_title_post_reg);
        TextView courseCode = findViewById(R.id.course_code_post_reg);
        ImageView courseImage = findViewById(R.id.course_image_post_reg);


        Intent j = getIntent();
        courseTitle.setText(j.getStringExtra("Course Title"));
        courseCode.setText(j.getStringExtra("Course Code"));
        Glide.with(courseImage.getContext()).load(j.getStringExtra("Course Image")).into(courseImage);


        //Populating About Fragment
        String[] lecturerData = j.getStringArrayExtra("Course Lecturer");
        String courseDescription = j.getStringExtra("Course Description");

        Bundle bundle = new Bundle();
        bundle.putStringArray("Course Lecturer",lecturerData);
        bundle.putString("Course Description",courseDescription);
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setArguments(bundle);

        //Populating Books Fragment
        final String[] books =j.getStringArrayExtra("Course Books");
        Bundle bundle1 = new Bundle();
        bundle1.putStringArray("Course Books",books);
        BooksFragment booksFragment = new BooksFragment();
        booksFragment.setArguments(bundle1);


        //Populating Program Fragment
        final String[] courseOut1 = j.getStringArrayExtra("Course Out1");
        final String[] courseOut2 = j.getStringArrayExtra("Course Out2");
        final String[] courseOut3 = j.getStringArrayExtra("Course Out3");
        Bundle bundle2 = new Bundle();
        bundle2.putStringArray("Course Out1",courseOut1);
        bundle2.putStringArray("Course Out2",courseOut2);
        bundle2.putStringArray("Course Out3",courseOut3);
        ProgramFragment programFragment = new ProgramFragment();
        programFragment.setArguments(bundle2);

        viewPager = findViewById(R.id.viewpager);


        tabLayout = findViewById(R.id.tab_layout);
        adapt = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        adapt.addFragment(programFragment,"Program");
        adapt.addFragment(booksFragment,"Books");
        adapt.addFragment(aboutFragment,"About");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapt);
    }
}
