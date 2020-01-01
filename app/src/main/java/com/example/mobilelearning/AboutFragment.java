package com.example.mobilelearning;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {



    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView lectName1 = view.findViewById(R.id.lecturer_name_1_post_reg);
        CardView card2 = view.findViewById(R.id.lecturer_card_2_post_reg);
        TextView lectName2 = view.findViewById(R.id.lecturer_name_2_post_reg);
        CardView card3 = view.findViewById(R.id.lecturer_card_3_post_reg);
        TextView lectName3 = view.findViewById(R.id.lecturer_name_3_post_reg);
        TextView courseDes = view.findViewById(R.id.course_description_post_reg);

        String courseDescription = getArguments().getString("Course Description");
        courseDes.setText(courseDescription);
        String [] lecturerData = getArguments().getStringArray("Course Lecturer");
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


        return view;
    }

}
