package com.example.editphoto.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.editphoto.R;
import com.example.editphoto.activity.EditActivity;
import com.example.editphoto.databinding.FragmentPaintBinding;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class FragmentPaint extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private FragmentPaintBinding binding;
    private PhotoEditor photoEditor;

    private int r,g,b,o;
    private float s;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaintBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        binding.red.setOnSeekBarChangeListener(this);
        binding.green.setOnSeekBarChangeListener(this);
        binding.size.setOnSeekBarChangeListener(this);
        binding.blue.setOnSeekBarChangeListener(this);
        binding.opacity.setOnSeekBarChangeListener(this);


        EditActivity edt = (EditActivity) getActivity();
        photoEditor = edt.getmPhotoEditor();

        s = 5f;
        r =0 ;
        g=0;
        b=0;
        o=100;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.size:
                s = progress;
                break;
            case R.id.red:
                r = progress;
                break;
            case R.id.green:
                g = progress;
                break;
            case R.id.blue:
                b = progress;
                break;
            case R.id.opacity:
                o = progress;
                break;

        }
        photoEditor.setBrushSize(s);
        photoEditor.setBrushColor(Color.rgb(r,g,b));
        photoEditor.setOpacity(o);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
