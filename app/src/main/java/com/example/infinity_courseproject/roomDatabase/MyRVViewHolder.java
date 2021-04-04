package com.example.infinity_courseproject.roomDatabase;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 *
 */

public class MyRVViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public MyRVViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    //

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Set the value of TextView
     */
    public MyRVViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    //    public MyRVViewHolder setImageURI(Context context, int viewId, String url, int defultPicResId) {
//        ImageView view = getView(viewId);
//        ImageUtil.displayImage(view, url, context, defultPicResId);
//        return this;
//    }
}
