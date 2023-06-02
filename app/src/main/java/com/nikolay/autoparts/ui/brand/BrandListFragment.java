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

    public BrandListFragment() {}

    public static BrandListFragment newInstance() {
        BrandListFragment fragment = new BrandListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
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