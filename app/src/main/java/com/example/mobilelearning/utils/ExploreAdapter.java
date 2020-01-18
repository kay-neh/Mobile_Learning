package com.example.mobilelearning.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilelearning.R;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {


    public interface ListItemClickListener{
        void onListItemClick(int index);
    }

    List<Courses> course;
    final private ListItemClickListener mOnclickListener;

    public ExploreAdapter(List<Courses> course,ListItemClickListener mOnclickListener) {
        this.course = course;
        this.mOnclickListener = mOnclickListener;
    }

    class ExploreViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView txt1, txt2;
        ImageView image;

        public ExploreViewHolder(final View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_view_explore);
            txt1 = itemView.findViewById(R.id.course_code);
            txt2 = itemView.findViewById(R.id.course_title);
            image = itemView.findViewById(R.id.course_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnclickListener.onListItemClick(clickedPosition);
                }
            });
        }
    }


    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.explore_recycler_view, parent, false);
        ExploreViewHolder cvh = new ExploreViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {


        Glide.with(holder.image.getContext()).load(course.get(position).courseImg).into(holder.image);
        holder.txt1.setText(course.get(position).courseCode);
        holder.txt2.setText(course.get(position).courseTitle);
    }

    @Override
    public int getItemCount() {
        return course.size();
    }

}

