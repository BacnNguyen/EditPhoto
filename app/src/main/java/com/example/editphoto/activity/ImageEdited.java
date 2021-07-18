package com.example.editphoto.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.editphoto.Adapter.ImageAdapter;
import com.example.editphoto.R;
import com.example.editphoto.databinding.ImageEditedBinding;

import java.io.File;
import java.util.ArrayList;

public class ImageEdited extends AppCompatActivity implements ImageAdapter.ImageClickListener {
    private ImageEditedBinding binding;
    private ArrayList<String> data = new ArrayList<>();
    private ImageAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.image_edited);
        adapter = new ImageAdapter(getLayoutInflater(),R.layout.image_layout);

        loadData();


        adapter.setData(data);
        adapter.notifyDataSetChanged();
        adapter.setListener(this);
        binding.recycler.setAdapter(adapter);
    }

    void loadData(){
        data.clear();
        String path = Environment.getExternalStorageDirectory().getPath();
        File folder = new File(path+"/editphoto/");
        File[] files = folder.listFiles();
        Log.e("SIZE",files.length+"");

        for(int i = 0;i<files.length;i++){
            Log.e("FILE",files[i].toString());
            if(files[i].isFile()){
                data.add(files[i].toString());
            }
        }
    }

    @Override
    public void onImageClick(int position) {

    }

    @Override
    public void onImageLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you want delete?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(data.get(position));
                if(file.delete()){
                    loadData();
                    adapter.setData(data);
//                    adapter.notifyDataSetChanged();
//                    binding.recycler.setAdapter(adapter);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ImageEdited.this, "Image deleted !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ImageEdited.this,MenuAcitvity.class));
        finish();
    }
}
