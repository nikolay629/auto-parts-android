package com.nikolay.autoparts.ui.model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.adapters.BrandListAdapter;
import com.nikolay.autoparts.adapters.ModelListAdapter;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.ui.brand.BrandCreateEditFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModelListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModelListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ModelDatabase modelDatabase;

    public ModelListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModelListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModelListFragment newInstance(String param1, String param2) {
        ModelListFragment fragment = new ModelListFragment();
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
        modelDatabase = new ModelDatabase(getContext());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_model_list, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.modelLV);
        ArrayList<Model> models = modelDatabase.getAll();

        ModelListAdapter modelListAdapter = new ModelListAdapter(this.getActivity(), R.layout.model_adapter_view_layout, models);
        mListView.setAdapter(modelListAdapter);
        mListView.setOnItemClickListener(this::onItemClick);

        return view;
    }


    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int modelId = ((Model) adapterView.getItemAtPosition(i)).getId();

        FloatingActionButton modelCreateB = getActivity().findViewById(R.id.modelCreateB);
        modelCreateB.setImageResource(R.drawable.back);

        Fragment fragment = ModelCreateEditFragment.newInstance(modelId);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.modelFL,
                fragment,
                "ModelCreateEditFragment"
        ).commit();
    }
}