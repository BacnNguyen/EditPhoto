package com.example.editphoto.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.editphoto.R;


import com.example.editphoto.databinding.EditLayoutBinding;
import com.example.editphoto.fragment.FragmentFilter;
import com.example.editphoto.fragment.FragmentPaint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class EditActivity extends AppCompatActivity  {
    private EditLayoutBinding binding;
    private PhotoEditor mPhotoEditor;
    private FragmentPaint fmPaint = new FragmentPaint();
    private FragmentFilter fmFilter = new FragmentFilter();

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.edit_layout);
        Bundle bundle = getIntent().getExtras();
        String str_uri = bundle.getString("uri");
        Uri uri = Uri.parse(str_uri);

        try {
            Bitmap  bitmap  = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,80,outputStream);
            Bitmap bitmap_compressed = BitmapFactory.decodeStream(new ByteArrayInputStream(outputStream.toByteArray()));

            binding.display.getSource().setImageBitmap(bitmap_compressed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        binding.display.getSource().setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zoom));
        mPhotoEditor = new PhotoEditor.Builder(this, binding.display)
                .setPinchTextScalable(true)
                .build();
        mPhotoEditor.setBrushSize(5);
        mPhotoEditor.setOpacity(100);
        mPhotoEditor.setBrushDrawingMode(false);

        mPhotoEditor.setFilterEffect(PhotoFilter.AUTO_FIX);

        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);

        initFragment();
    }

    private void fmShow(Fragment fm) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fmPaint);
        transaction.hide(fmFilter);
        transaction.show(fm);
        transaction.commit();
    }

    private void initFragment() {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame,fmPaint);
        transaction.add(R.id.frame,fmFilter);
        transaction.hide(fmPaint);
        transaction.hide(fmFilter);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }


    public PhotoEditor getmPhotoEditor() {
        return mPhotoEditor;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.paint:
                mPhotoEditor.setBrushDrawingMode(true);
                fmShow(fmPaint);
                return true;
            case R.id.undo:
                mPhotoEditor.undo();
                return true;
            case R.id.redo:
                mPhotoEditor.redo();
                return true;
            case R.id.eraser:
                mPhotoEditor.brushEraser();
                return true;
            case R.id.effect:
                fmShow(fmFilter);
                return true;
            case R.id.save:
                savedImage();
                return true;
        }
        return false;
    }

    public void savedImage(){

        mPhotoEditor.saveAsBitmap(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap) {
                try {
                    String path = Environment.getExternalStorageDirectory().getPath();
                    File file = new File(path, "editphoto/" + System.currentTimeMillis() + ".jpg");
                    file.getParentFile().mkdirs();
//                    Log.e("Path", file.getAbsolutePath());
                    FileOutputStream out = new FileOutputStream(file);
                    saveBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    Toast.makeText(EditActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e){
                Toast.makeText(EditActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void  setFilterImage(String filterImage){
        mPhotoEditor.setFilterEffect(PhotoFilter.valueOf(filterImage));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MenuAcitvity.class));
        finish();
    }
}
