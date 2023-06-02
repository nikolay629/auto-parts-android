package com.nikolay.autoparts.ui.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.BrandActivity;
import com.nikolay.autoparts.CategoryActivity;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Part;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryCreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCreateEditFragment extends Fragment {

    private static final String CATEGORY_ID = "categoryId";

    private boolean createForm = true;
    private String title;
    private int categoryId;

    private TextView createEditCategoryTitleTV;
    private EditText createEditCategoryNameET;
    private Button createB;
    private FloatingActionButton deleteB;

    private Category category;
    private CategoryDatabase categoryDatabase;

    public CategoryCreateEditFragment() {}

    public static CategoryCreateEditFragment newInstance(int categoryId) {
        CategoryCreateEditFragment fragment = new CategoryCreateEditFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDatabase = new CategoryDatabase(getContext());
        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
            category   = categoryDatabase.getById(categoryId);
            createForm = false;
            title      = "Edit Form";
        } else {
            category = new Category();
            title    = "Create Form";
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_category_create_edit, container, false);

        createEditCategoryTitleTV = view.findViewById(R.id.createEditCategoryTitleTV);
        createEditCategoryNameET = view.findViewById(R.id.createEditCategoryNameET);
        createB = view.findViewById(R.id.createEditCategorySaveB);
        deleteB = getActivity().findViewById(R.id.categoryDeleteB);

        createEditCategoryTitleTV.setText(title);
        createEditCategoryNameET.setText(category.getName());

        if (!createForm) {
            createB.setText("Update");
            deleteB.setVisibility(View.VISIBLE);
            deleteB.setOnClickListener(this::onClickDeleteButton);
        }

        createB.setOnClickListener(this::onClickCreateButton);
        return view;
    }

    private void onClickDeleteButton(View view) {
        deleteB.setVisibility(View.GONE);
        categoryDatabase.delete(category);
        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), CategoryActivity.class));
    }

    private void onClickCreateButton(View view) {
        if (!this.isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        category.setName(createEditCategoryNameET.getText().toString());
        if (category.getId() != 0) {
            categoryDatabase.update(category);
            Toast.makeText(getContext(), "Updated!", Toast.LENGTH_LONG).show();
        } else {
            categoryDatabase.insert(category);
            Toast.makeText(getContext(), "Created!", Toast.LENGTH_LONG).show();
        }

        deleteB.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), CategoryActivity.class));
    }

    private boolean isValid() {
        return !createEditCategoryNameET.getText().toString().isEmpty();
    }
}