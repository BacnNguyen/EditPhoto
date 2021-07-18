package com.example.editphoto.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.example.editphoto.R;
import com.example.editphoto.databinding.ActivityMainBinding;
import com.example.editphoto.databinding.EditLayoutBinding;
import com.example.editphoto.databinding.LoginLayoutBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;

public class MainActivity extends AppCompatActivity{
    private PhotoEditorView photoEditorView;
    private AppCompatSeekBar seekBar;
    private String  name;
    PhotoEditor mPhotoEditor;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.view.getSource().setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.effect));
        mPhotoEditor = new PhotoEditor.Builder(this, binding.view)
                .setPinchTextScalable(true)
                .build();

        final ArrayList<PhotoFilter> photoFilters = new ArrayList<>();
        photoFilters.add(PhotoFilter.NEGATIVE);
        photoFilters.add(PhotoFilter.POSTERIZE);
        photoFilters.add(PhotoFilter.ROTATE);
        photoFilters.add(PhotoFilter.SATURATE);
        photoFilters.add(PhotoFilter.TEMPERATURE);
        photoFilters.add(PhotoFilter.VIGNETTE);
        photoFilters.add(PhotoFilter.BLACK_WHITE);
        photoFilters.add(PhotoFilter.DUE_TONE);
        photoFilters.add(PhotoFilter.FISH_EYE);
        photoFilters.add(PhotoFilter.GRAY_SCALE);
        photoFilters.add(PhotoFilter.AUTO_FIX);
        photoFilters.add(PhotoFilter.BRIGHTNESS);
        photoFilters.add(PhotoFilter.CONTRAST);
        photoFilters.add(PhotoFilter.CROSS_PROCESS);
        photoFilters.add(PhotoFilter.DOCUMENTARY);
        photoFilters.add(PhotoFilter.FILL_LIGHT);
        photoFilters.add(PhotoFilter.FLIP_HORIZONTAL);
        photoFilters.add(PhotoFilter.FLIP_VERTICAL);
        photoFilters.add(PhotoFilter.GRAIN);
        photoFilters.add(PhotoFilter.LOMISH);
        photoFilters.add(PhotoFilter.SEPIA);
        photoFilters.add(PhotoFilter.SHARPEN);
        photoFilters.add(PhotoFilter.TINT);
        photoFilters.add(PhotoFilter.NONE);
        int n =23;
            mPhotoEditor.setFilterEffect(photoFilters.get(n));
            name = photoFilters.get(n).name().toLowerCase();
            Log.e("Name :",name);
            mPhotoEditor.saveAsBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap saveBitmap) {
                    try {
                        String path = Environment.getExternalStorageDirectory().getPath();
                        File file = new File(path, "image/" + name + ".jpg");
                        file.getParentFile().mkdirs();
                        Log.e("Path", file.getAbsolutePath());
                        FileOutputStream out = new FileOutputStream(file);
                        saveBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e("PhotoEditor", "Failed to save Image");
                }
            });
//        binding.btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });

//        photoEditorView = findViewById(R.id.photoEditorView);
//        photoEditorView.getSource().setImageResource(R.drawable.image);
//        photoEditorView.getSource().setImageAlpha(0);
//        seekBar = findViewById(R.id.seekbar);
//
//        seekBar.setOnSeekBarChangeListener(this);
//        seekBar.setMax(255);
    }
}
