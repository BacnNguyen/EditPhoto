package com.example.editphoto.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.editphoto.R;
import com.example.editphoto.databinding.MenuLayoutBinding;
import com.example.editphoto.event_listener.SelectListener;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAcitvity extends AppCompatActivity implements SelectListener {
    private MenuLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.menu_layout);

        binding.setListener(this);
    }

    @Override
    public void onPickImageListener() {
        startActivity(new Intent(this,PickImageActivity.class));
        finish();
    }

    @Override
    public void onTakeImageListener() {
        Intent intent1 = new Intent(MenuAcitvity.this, TakeImage_Activity.class);
        startActivity(intent1);
        finish();
    }

    @Override
    public void onShowEditedImage() {
        startActivity(new Intent(MenuAcitvity.this,ImageEdited.class));
        finish();
    }

    @Override
    public void onHowToUse() {

    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
