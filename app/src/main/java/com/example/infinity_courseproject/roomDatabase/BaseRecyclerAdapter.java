package com.example.infinity_courseproject.roomDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 *
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<MyRVViewHolder> {
    public List<T> mDatas;
    private final LayoutInflater mInflater;
    private final int mlayoutId;
    public Context context;
    private   OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;


    public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId ) {
        this.context = context;
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);

    }


    public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @NonNull
    @Override
    public MyRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRVViewHolder(mInflater.inflate(mlayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRVViewHolder holder, int position) {
        setView(holder, mDatas.get(position), position);
        setUpItemEvent(holder);
    }

    public abstract void setView(MyRVViewHolder holder, T datadto, int position);
    public void setUpItemEvent(final MyRVViewHolder holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int layoutPosition = holder.getAdapterPosition();
                onItemClickListener.onItemClick(holder.itemView, layoutPosition);
            });
        }
        if (null != onItemLongClickListener) {
            holder.itemView.setOnLongClickListener(v -> {
                int layoutPosition = holder.getAdapterPosition();
                onItemLongClickListener.onItemLongClick(holder.itemView, layoutPosition);
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
