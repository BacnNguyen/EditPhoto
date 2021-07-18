package com.example.editphoto.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.editphoto.databinding.ImageEditedBinding;
import com.example.editphoto.databinding.ImageLayoutBinding;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private ArrayList<String> data;
    private LayoutInflater inflater;
    private int reslayout;
    private ImageClickListener listener;

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void setListener(ImageClickListener listener) {
        this.listener = listener;
    }

    public ImageAdapter(LayoutInflater inflater, int reslayout) {
        this.inflater = inflater;
        this.reslayout = reslayout;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageLayoutBinding binding = DataBindingUtil.inflate(inflater,reslayout,parent,false);
        return new ImageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, final int position) {
        String path = data.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Log.e("PATH",path);
        holder.binding.imageView.setImageBitmap(bitmap);

        if(listener!=null){
             holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     listener.onImageClick(position);
                 }
             });

             holder.binding.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View view) {
                     listener.onImageLongClick(position);
                     return false;
                 }
             });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        private ImageLayoutBinding binding;
        public ImageHolder(@NonNull ImageLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ImageClickListener{
        void onImageClick(int position);
        void onImageLongClick(int position);
    }
}
