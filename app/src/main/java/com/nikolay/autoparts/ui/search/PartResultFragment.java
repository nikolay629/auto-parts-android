package com.nikolay.autoparts.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.adapters.PartListAdapter;
import com.nikolay.autoparts.database.PartDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.model.Part;
import com.nikolay.autoparts.ui.brand.BrandCreateEditFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartResultFragment extends Fragment {

    private static final String MODEL_ID = "model_id";
    private static final String CATEGORY_ID = "category_id";

    private int modelId;
    private int categoryId;

    private PartDatabase partDatabase;

    public PartResultFragment() {}

    public static PartResultFragment newInstance(int modelId, int categoryId) {
        PartResultFragment fragment = new PartResultFragment();
        Bundle args = new Bundle();
        args.putInt(MODEL_ID, modelId);
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partDatabase = new PartDatabase(getContext());
        if (getArguments() != null) {
            modelId    = getArguments().getInt(MODEL_ID);
            categoryId = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_part_result, container, false);
        getActivity().findViewById(R.id.homeBackB).setVisibility(View.VISIBLE);
        ListView mListView = (ListView) view.findViewById(R.id.partLV);

        ArrayList<Part> parts;
        if (modelId == 0 && categoryId == 0) {
            parts = partDatabase.getAll();
        } else if (modelId != 0 && categoryId != 0) {
            parts = partDatabase.getByModelAndCategory(modelId + "", categoryId + "");
        } else if (modelId != 0 && categoryId == 0) {
            parts = partDatabase.getByModel(modelId+ "");
        } else {
            parts = partDatabase.getByCategory(categoryId + "");
        }

        PartListAdapter partListAdapter = new PartListAdapter(this.getActivity(), R.layout.part_adapter_view_layout, parts);
        mListView.setAdapter(partListAdapter);
        mListView.setOnItemClickListener(this::onItemClick);
        return view;
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int partId = ((Part) adapterView.getItemAtPosition(i)).getId();

        Fragment fragment = PartCreateEditFragment.newInstance(partId);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                fragment,
                "PartCreateEditFragment"
        ).commit();
    }
}