package com.example.mobilelearning.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilelearning.R;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {

    List<Courses> courseOutline;

    public ProgramAdapter(List<Courses> courseOutline) {
        this.courseOutline = courseOutline;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.program_fragment_list_view, parent, false);
        ProgramViewHolder pvh = new ProgramViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        /*
        holder.txt1.setText(courseOutline.get(position).courseOutlineTitle);
        holder.txt2.setText(courseOutline.get(position).courseOutlineDesc);
        holder.txt3.setText(courseOutline.get(position).courseOutlineIndex);

         */
    }

    @Override
    public int getItemCount() {
        return courseOutline.size();
    }

    class ProgramViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView txt1,txt2,txt3;

        public ProgramViewHolder(View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_view_program);
            txt1 = itemView.findViewById(R.id.course_outline_title);
            txt2 = itemView.findViewById(R.id.course_outline_description);
            txt3 = itemView.findViewById(R.id.course_outline_index_no);
        }
    }
}

