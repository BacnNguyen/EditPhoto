package com.example.editphoto.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.editphoto.R;
import com.example.editphoto.databinding.PickImageLayoutBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PickImageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_IMAGE = 4232;
    private PickImageLayoutBinding binding;
    private Uri bitmap_uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pick_image_layout);

        binding.btnPick.setOnClickListener(this);
        binding.btnLetgo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pick:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_IMAGE);
                break;
            case R.id.btn_letgo:
                if(bitmap_uri==null){
                    Snackbar.make(binding.getRoot(),"None image selected",BaseTransientBottomBar.LENGTH_LONG).show();
                    break;
                }

                Bundle bundle = new Bundle();
                bundle.putString("uri",bitmap_uri.toString());
                Intent intent = new Intent(this,EditActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_IMAGE&&resultCode==RESULT_OK&&data!=null){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                binding.imgPick.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                bitmap_uri = data.getData();
                Log.e("URI",bitmap_uri.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        else {
            Snackbar.make(binding.getRoot(),"Something was wrong",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MenuAcitvity.class));
        finish();
    }
}
