package com.example.mobilelearning.ui.coursedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.BaseRepository;
import com.example.mobilelearning.ui.coursedetails.about.AboutFragment;
import com.example.mobilelearning.ui.coursedetails.books.BooksFragment;
import com.example.mobilelearning.ui.coursedetails.program.ProgramFragment;
import com.example.mobilelearning.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseInfoPostActivity extends AppCompatActivity {

    private SimpleFragmentPagerAdapter adapt;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_post);

        //get data from host activity
        final Intent j = getIntent();
        final String courseTitleData = j.getStringExtra(Constants.KEY_COURSE_TITLE);
        final String courseId = j.getStringExtra(Constants.KEY_COURSE_ID);
        String courseCodeData = j.getStringExtra(Constants.KEY_COURSE_CODE);
        String courseImageData = j.getStringExtra(Constants.KEY_COURSE_IMAGE);
        String courseDescription = j.getStringExtra(Constants.KEY_COURSE_DESCRIPTION);

        // Access Repository
        final BaseRepository baseRepository = new BaseRepository();

        Toolbar tool = findViewById(R.id.toolbar_course_post_sub);

        tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_remove_course) {
                    String message = courseTitleData + " removed from my courses";
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    baseRepository.unregisterCourse(firebaseAuth.getCurrentUser().getUid(), courseId);
                    Toast.makeText(CourseInfoPostActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                return false;
            }
        });

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView courseTitle = findViewById(R.id.course_title_post_reg);
        TextView courseCode = findViewById(R.id.course_code_post_reg);
        ImageView courseImage = findViewById(R.id.course_image_post_reg);

        //populate postreg acivity
        if(courseImageData != null) {
            Glide.with(getApplicationContext()).load(courseImageData).into(courseImage);
        }
        courseTitle.setText(courseTitleData);
        courseCode.setText(courseCodeData);

        //Populating About Fragment
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_COURSE_ID, courseId);
        bundle.putString(Constants.KEY_COURSE_DESCRIPTION, courseDescription);
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setArguments(bundle);

        //Populating Book Fragment
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.KEY_COURSE_ID, courseId);
        BooksFragment booksFragment = new BooksFragment();
        booksFragment.setArguments(bundle1);

        //Populating Program Fragment
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.KEY_COURSE_ID, courseId);
        ProgramFragment programFragment = new ProgramFragment();
        programFragment.setArguments(bundle2);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        adapt = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        adapt.addFragment(programFragment, "Program");
        adapt.addFragment(booksFragment, "Book");
        adapt.addFragment(aboutFragment, "About");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapt);

    }
}
