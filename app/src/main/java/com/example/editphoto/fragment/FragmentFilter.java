package com.example.editphoto.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.editphoto.Adapter.FilterAdapter;
import com.example.editphoto.R;
import com.example.editphoto.activity.EditActivity;
import com.example.editphoto.databinding.FragmentFilterBinding;
import com.example.editphoto.model.ItemFilter;

import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FragmentFilter extends Fragment implements FilterAdapter.FilterListener {
    private FragmentFilterBinding binding;
    private ArrayList<ItemFilter> data;
    private FilterAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = new ArrayList<>();
        data.add(new ItemFilter(R.drawable.auto_fix));
        data.add(new ItemFilter(R.drawable.black_white));
        data.add(new ItemFilter(R.drawable.brightness));
        data.add(new ItemFilter(R.drawable.contrast));
        data.add(new ItemFilter(R.drawable.cross_process));
        data.add(new ItemFilter(R.drawable.documentary));
        data.add(new ItemFilter(R.drawable.due_tone));
        data.add(new ItemFilter(R.drawable.fill_light));
        data.add(new ItemFilter(R.drawable.fish_eye));
        data.add(new ItemFilter(R.drawable.flip_horizontal));
        data.add(new ItemFilter(R.drawable.flip_vertical));
        data.add(new ItemFilter(R.drawable.grain));
        data.add(new ItemFilter(R.drawable.gray_scale));
        data.add(new ItemFilter(R.drawable.lomish));
        data.add(new ItemFilter(R.drawable.negative));
        data.add(new ItemFilter(R.drawable.none));
        data.add(new ItemFilter(R.drawable.posterize));
        data.add(new ItemFilter(R.drawable.rotate));
        data.add(new ItemFilter(R.drawable.saturate));
        data.add(new ItemFilter(R.drawable.sepia));
        data.add(new ItemFilter(R.drawable.sharpen));
        data.add(new ItemFilter(R.drawable.temperature));
        data.add(new ItemFilter(R.drawable.tint));
        data.add(new ItemFilter(R.drawable.vignette));




        adapter = new FilterAdapter(getLayoutInflater(),R.layout.item_filter);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
        binding.recycler.setAdapter(adapter);
        adapter.setListener(this);

    }


    @Override
    public void onFilterClick(int position) {
       String filter = getResources().getResourceEntryName(data.get(position).getRes()).toUpperCase();
       EditActivity editActivity = (EditActivity) getActivity();
       editActivity.setFilterImage(filter);
       Log.e("FILTER",filter);
    }
}
