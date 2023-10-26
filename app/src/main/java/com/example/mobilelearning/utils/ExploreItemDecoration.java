package com.example.mobilelearning.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreItemDecoration extends RecyclerView.ItemDecoration{
    private int spaceInPixels;
    public ExploreItemDecoration(int spaceInPixels) {
        this.spaceInPixels = spaceInPixels;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view,
                               RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(spaceInPixels, spaceInPixels, spaceInPixels, spaceInPixels);
    }
}
