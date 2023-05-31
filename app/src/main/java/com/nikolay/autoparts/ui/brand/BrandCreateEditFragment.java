package com.nikolay.autoparts.ui.brand;

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
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.model.Brand;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrandCreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandCreateEditFragment extends Fragment {

    private static final String BRAND_ID = "brandId";

    private boolean createForm = true;
    private String title;
    private int brandId;

    private TextView createEditBrandTitleTV;
    private EditText brandNameET;
    private Button createB;
    private FloatingActionButton deleteB;

    private Brand brand;
    private BrandDatabase brandDatabase;

    public BrandCreateEditFragment() {}

    public static BrandCreateEditFragment newInstance(int brandId) {
        BrandCreateEditFragment fragment = new BrandCreateEditFragment();
        Bundle args = new Bundle();
        args.putInt(BRAND_ID, brandId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandDatabase = new BrandDatabase(getContext());
        if (getArguments() != null) {
            brandId    = getArguments().getInt(BRAND_ID);
            brand      = brandDatabase.getById(brandId + "");
            createForm = false;
            title      = "Edit Form";
        } else {
            brand = new Brand();
            title = "Create Form";
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_brand_create_edit, container, false);

        createEditBrandTitleTV = view.findViewById(R.id.createEditBrandTitleTV);
        brandNameET = view.findViewById(R.id.createEditBrandNameET);
        createB = view.findViewById(R.id.createEditBrandSaveB);
        deleteB = getActivity().findViewById(R.id.brandDeleteB);

        createEditBrandTitleTV.setText(title);
        brandNameET.setText(brand.getName());

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
        brandDatabase.delete(brand);
        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), BrandActivity.class));
    }

    private void onClickCreateButton(View view) {
        if (!this.isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        brand.setName(brandNameET.getText().toString());
        if (brand.getId() != 0) {
            brandDatabase.update(brand);
            Toast.makeText(getContext(), "Updated!", Toast.LENGTH_LONG).show();
        } else {
            brandDatabase.insert(brand);
            Toast.makeText(getContext(), "Created!", Toast.LENGTH_LONG).show();
        }

        deleteB.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), BrandActivity.class));
    }

    private boolean isValid() {
        return !brandNameET.getText().toString().isEmpty();
    }
}