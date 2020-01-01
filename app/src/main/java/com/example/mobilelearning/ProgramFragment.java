package com.example.mobilelearning;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilelearning.utils.Courses;
import com.example.mobilelearning.utils.ProgramAdapter;

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

        String[]courseOut1 = getArguments().getStringArray("Course Out1");
        String[]courseOut2 = getArguments().getStringArray("Course Out2");
        String[]courseOut3 = getArguments().getStringArray("Course Out3");

        TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;

        txt1 = view.findViewById(R.id.course_outline_index_no1);
        txt2 =  view.findViewById(R.id.course_outline_title1);
        txt3 = view.findViewById(R.id.course_outline_description1);

        txt4 = view.findViewById(R.id.course_outline_index_no2);
        txt5 =  view.findViewById(R.id.course_outline_title2);
        txt6 = view.findViewById(R.id.course_outline_description2);

        txt7 = view.findViewById(R.id.course_outline_index_no3);
        txt8 =  view.findViewById(R.id.course_outline_title3);
        txt9 = view.findViewById(R.id.course_outline_description3);



        txt1.setText(courseOut1[0]);
        txt2.setText(courseOut1[1]);
        txt3.setText(courseOut1[2]);

        txt4.setText(courseOut2[0]);
        txt5.setText(courseOut2[1]);
        txt6.setText(courseOut2[2]);

        txt7.setText(courseOut3[0]);
        txt8.setText(courseOut3[1]);
        txt9.setText(courseOut3[2]);

        return view;
    }

}
