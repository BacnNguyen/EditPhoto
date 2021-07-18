package com.example.editphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.editphoto.R;
import com.example.editphoto.databinding.LoginLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginLayoutBinding binding;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_layout);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            Intent intent = new Intent(this,MenuAcitvity.class);
            startActivity(intent);
            finish();
        }
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPass.getText().toString();
                if(email.isEmpty()||password.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Intent intent = new Intent(LoginActivity.this,MenuAcitvity.class);
                           startActivity(intent);
                           finish();
                       }
                       else {
                           Toast.makeText(LoginActivity.this, "Login failed. Wrong account!", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
