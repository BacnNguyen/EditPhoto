package com.example.editphoto.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.example.editphoto.R;
import com.example.editphoto.databinding.TakeImageBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;

public class TakeImage_Activity extends AppCompatActivity implements View.OnClickListener {
    private TakeImageBinding binding;
    private Uri imageUriCapture;
    private Uri uri;
    private PhotoEditor photoEditor;
    String image_path;
    public static final int REQUEST_ID_IMAGE_CAPTURE = 131;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.take_image);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"capture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From camera");
        imageUriCapture = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        photoEditor = new PhotoEditor.Builder(this,binding.imageTaken).setPinchTextScalable(true).build();
        if(ContextCompat.checkSelfPermission(TakeImage_Activity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TakeImage_Activity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCapture);
            startActivityForResult(intent,REQUEST_ID_IMAGE_CAPTURE);
            binding.btnNext.setOnClickListener(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(String p:permissions){
            if(checkSelfPermission(p)==PackageManager.PERMISSION_DENIED) return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCapture);
        startActivityForResult(intent,REQUEST_ID_IMAGE_CAPTURE);
        binding.btnNext.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriCapture);
                    binding.imageTaken.getSource().setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TakeImage_Activity.this,MenuAcitvity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        photoEditor.saveAsBitmap(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap) {
                try {
                    String path = Environment.getExternalStorageDirectory().getPath();
                    File file = new File(path, "editphoto/camera/" + System.currentTimeMillis() + ".jpg");
                    file.getParentFile().mkdirs();
//                    image_path = file.getAbsolutePath();
//                    Log.e("PATH",image_path);
                    FileOutputStream out = new FileOutputStream(file);
                    saveBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    uri = Uri.fromFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                Bundle bundle = new Bundle();
                if(uri==null) return;
                bundle.putString("uri",uri.toString());
                Log.e("URI",uri.toString());
                Intent intent = new Intent(TakeImage_Activity.this,EditActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(TakeImage_Activity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
