package com.nikolay.autoparts.ui.category;

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
import com.nikolay.autoparts.adapters.CategoryListAdapter;
import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.ui.brand.BrandCreateEditFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Category category;
    private CategoryDatabase categoryDatabase;

    public CategoryListFragment() {}

    public static CategoryListFragment newInstance(String param1, String param2) {
        CategoryListFragment fragment = new CategoryListFragment();
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
        categoryDatabase = new CategoryDatabase(getContext());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.categoryLV);
        ArrayList<Category> categories = categoryDatabase.getAll();

        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(
                this.getActivity(),
                R.layout.category_adapter_view_layout,
                categories
        );
        mListView.setAdapter(categoryListAdapter);
        mListView.setOnItemClickListener(this::onItemClick);
        return view;
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int categoryId = ((Category) adapterView.getItemAtPosition(i)).getId();

        FloatingActionButton categoryCreateB = getActivity().findViewById(R.id.categoryCreateB);
        categoryCreateB.setImageResource(R.drawable.back);

        Fragment fragment = CategoryCreateEditFragment.newInstance(categoryId);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.categoryFL,
                fragment,
                "CategoryCreateEditFragment"
        ).commit();
    }

}