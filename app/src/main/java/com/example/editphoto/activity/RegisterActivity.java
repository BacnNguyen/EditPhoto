package com.example.editphoto.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.content.AsyncTaskLoader;

import com.example.editphoto.R;
import com.example.editphoto.databinding.RegisterLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RegisterLayoutBinding binding;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_layout);
        fAuth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(this);
        binding.progressBar.getIndeterminateDrawable().setColorFilter(Color.RED,PorterDuff.Mode.DST_IN);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        String email = binding.edtEmail.getText().toString();
        String pass = binding.edtPass.getText().toString();
        String name = binding.edtName.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, "Email can not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<6){
            Toast.makeText(this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    },2000);

                } else {
                    Snackbar snb = Snackbar.make(binding.getRoot(), "Try another email !", Snackbar.LENGTH_LONG);
                    snb.show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
