package com.example.mobilelearning.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;
import com.example.mobilelearning.data.pojo.Lecturer;

import java.util.List;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.LecturerVH>{

    private List<Lecturer> lecturerList;

    //public constructor
    public LecturerAdapter() {}

    //viewholder inner class
    class LecturerVH extends RecyclerView.ViewHolder{
        ImageView lectImage;
        TextView lectName;
        LecturerVH(@NonNull View itemView) {
            super(itemView);
            lectImage = itemView.findViewById(R.id.lect_image);
            lectName = itemView.findViewById(R.id.lect_name);
        }
    }

    @NonNull
    @Override
    public LecturerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.lecturer_recycler_view, parent, false);
        return new LecturerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LecturerVH holder, int position) {
        if (lecturerList != null) {
            Lecturer currentLecturer = lecturerList.get(position);

            if (currentLecturer.getLecturerPhoto() != null) {
                Glide.with(holder.lectImage.getContext()).load(currentLecturer.getLecturerPhoto()).into(holder.lectImage);
            }
            holder.lectName.setText(currentLecturer.getLecturerName());
        }
    }

    @Override
    public int getItemCount() {
        if (lecturerList != null)
            return lecturerList.size();
        else return 0;
    }

    public void setList(List<Lecturer> lecturers){
        lecturerList = lecturers;
        notifyDataSetChanged();
    }

}
