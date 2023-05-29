package com.nikolay.autoparts.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nikolay.autoparts.R;
import com.nikolay.autoparts.adapters.PartListAdapter;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartResultFragment newInstance(String param1, String param2) {
        PartResultFragment fragment = new PartResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_part_result, container, false);
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
}