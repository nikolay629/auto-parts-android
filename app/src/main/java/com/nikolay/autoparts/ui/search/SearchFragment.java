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

    private Spinner homeBrandS;
    private Spinner homeModelS;
    private Spinner homeCategoryS;
    private Button homeSearchB;

    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;
    private CategoryDatabase categoryDatabase;

    public SearchFragment() {}

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandDatabase = new BrandDatabase(getContext());
        modelDatabase = new ModelDatabase(getContext());
        categoryDatabase = new CategoryDatabase(getContext());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().findViewById(R.id.homeBackB).setVisibility(View.GONE);

        homeBrandS = view.findViewById(R.id.homeBrandS);
        homeModelS = view.findViewById(R.id.homeModelS);
        homeCategoryS = view.findViewById(R.id.homeCategoryS);
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
            ((Model) homeModelS.getSelectedItem()).getId(),
            ((Category) homeCategoryS.getSelectedItem()).getId()
        );
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                fragment,
                "PartResultFragment"
        ).commit();
    }
}