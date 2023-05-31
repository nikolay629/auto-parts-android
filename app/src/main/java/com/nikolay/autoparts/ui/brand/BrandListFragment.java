package com.nikolay.autoparts.ui.brand;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.SearchActivity;
import com.nikolay.autoparts.adapters.BrandListAdapter;
import com.nikolay.autoparts.adapters.PartListAdapter;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Part;
import com.nikolay.autoparts.ui.search.PartResultFragment;
import com.nikolay.autoparts.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrandListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandListFragment extends Fragment {

    private BrandDatabase brandDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrandListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrandListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrandListFragment newInstance(String param1, String param2) {
        BrandListFragment fragment = new BrandListFragment();
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
        View view = inflater.inflate(R.layout.fragment_brand_list, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.brandLV);

        brandDatabase = new BrandDatabase(getContext());
        ArrayList<Brand> brandList = brandDatabase.getAll();

        BrandListAdapter brandListAdapter = new BrandListAdapter(this.getActivity(), R.layout.brand_adapter_view_layout, brandList);
        mListView.setAdapter(brandListAdapter);
        mListView.setOnItemClickListener(this::onItemClick);
        return view;
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int brandId = ((Brand) adapterView.getItemAtPosition(i)).getId();

        FloatingActionButton brandCreateB = getActivity().findViewById(R.id.brandCreateB);
        brandCreateB.setImageResource(R.drawable.back);

        Fragment fragment = BrandCreateEditFragment.newInstance(brandId);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.brandFL,
                fragment,
                "CreateEditFragment"
        ).commit();
    }

}