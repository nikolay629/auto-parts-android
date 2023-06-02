package com.nikolay.autoparts.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nikolay.autoparts.R;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner homeBrandS;
    private Spinner homeModelS;
    private Spinner homeCategoryS;
    private Spinner homePartNameS;
    private Button homeSearchB;

    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;
    private CategoryDatabase categoryDatabase;

    public SearchFragment() {}

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        brandDatabase = new BrandDatabase(getContext());
        modelDatabase = new ModelDatabase(getContext());
        categoryDatabase = new CategoryDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().findViewById(R.id.homeBackB).setVisibility(View.GONE);

        homeBrandS = view.findViewById(R.id.homeBrandS);
        homeModelS = view.findViewById(R.id.homeModelS);
        homeCategoryS = view.findViewById(R.id.homeCategoryS);
        homePartNameS = view.findViewById(R.id.homePartNameS);
        homeSearchB = view.findViewById(R.id.homeSearchB);

        List<Brand> brandList = new ArrayList<>();
        brandList.add(new Brand());
        brandList.addAll(brandDatabase.getAll());
        ArrayAdapter<Brand> brandAdapter = new ArrayAdapter<Brand>(getContext(), R.layout.spinner, brandList);
        brandAdapter.setDropDownViewResource(R.layout.spinner);
        homeBrandS.setAdapter(brandAdapter);
        homeBrandS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Model> modelList = new ArrayList<>();
                modelList.add(new Model());
                if (homeBrandS.getSelectedItem().toString() != null) {
                    modelList.addAll(modelDatabase.getByBrand((Brand)homeBrandS.getSelectedItem()));
                }
                ArrayAdapter<Model> modelAdapter = new ArrayAdapter<Model>(getContext(), R.layout.spinner, modelList);
                modelAdapter.setDropDownViewResource(R.layout.spinner);
                homeModelS.setAdapter(modelAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        categoryList.addAll(categoryDatabase.getAll());
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(getContext(), R.layout.spinner, categoryList);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        homeCategoryS.setAdapter(categoryAdapter);

        homeSearchB.setOnClickListener(this::search);
        return view;
    }

    private void search(View view) {
        Fragment fragment = PartResultFragment.newInstance(
            homeModelS.getSelectedItem().toString(),
            homeCategoryS.getSelectedItem().toString()
        );
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                fragment,
                "PartResultFragment"
        ).commit();
    }
}