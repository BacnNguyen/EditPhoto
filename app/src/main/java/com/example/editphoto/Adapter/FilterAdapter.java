package com.example.editphoto.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.editphoto.databinding.ItemFilterBinding;
import com.example.editphoto.model.ItemFilter;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHoler> {
    private LayoutInflater inflater;
    private int resLayout;
    private ArrayList<ItemFilter> data;
    private FilterListener listener;

    public void setListener(FilterListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<ItemFilter> data) {
        this.data = data;
    }

    public FilterAdapter(LayoutInflater inflater, int resId) {
        this.inflater = inflater;
        this.resLayout = resId;
    }

    @NonNull
    @Override
    public FilterHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding = DataBindingUtil.inflate(inflater, resLayout,parent,false);
        return new FilterHoler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHoler holder, final int position) {
        ItemFilter itemFilter = data.get(position);
        holder.binding.image.setImageResource(itemFilter.getRes());

        if( listener!=null){
            holder.binding.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFilterClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class FilterHoler extends RecyclerView.ViewHolder {
        private ItemFilterBinding binding;
        public FilterHoler(@NonNull ItemFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface FilterListener{
        void onFilterClick(int position);
    }
}
