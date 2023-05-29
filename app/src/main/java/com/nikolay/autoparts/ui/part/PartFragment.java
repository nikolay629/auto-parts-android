package com.nikolay.autoparts.ui.part;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nikolay.autoparts.R;
import com.nikolay.autoparts.adapters.PartListAdapter;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;

public class PartFragment extends Fragment {

    private PartViewModel mViewModel;

    public static PartFragment newInstance() {
        return new PartFragment();
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_part, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.partLV);
        Part part1 = new Part("test1", 12, 27.30f);
        Part part2 = new Part("test2", 14, 10.30f);
        Part part3 = new Part("test3", 1, 300.30f);
        Part part4 = new Part("test4", 11, 1000.99f);

        ArrayList<Part> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
        parts.add(part3);
        parts.add(part4);

        PartListAdapter partListAdapter = new PartListAdapter(this.getActivity(), R.layout.part_adapter_view_layout, parts);
        mListView.setAdapter(partListAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PartViewModel.class);
        // TODO: Use the ViewModel
    }

}