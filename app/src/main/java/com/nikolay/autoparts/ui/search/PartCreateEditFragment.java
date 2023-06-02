package com.nikolay.autoparts.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.ModelActivity;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.SearchActivity;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.database.PartDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartCreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartCreateEditFragment extends Fragment {

    private static final String PART_ID = "part_id";

    private boolean createForm = true;
    private String title;
    private String partId;

    private TextView partCreateEditPartTitleTV;
    private Spinner partCreateEditBrandS;
    private Spinner partCreateEditModelS;
    private Spinner partCreateEditCategoryS;
    private EditText partCreateEditPartNameET;
    private EditText partCreateEditPartQtyET;
    private EditText partCreateEditPartPriceET;
    private Button partCreateEditPartSaveB;
    private FloatingActionButton homeBackB;
    private FloatingActionButton partCreateB;
    private FloatingActionButton partDeleteB;

    private Part part;

    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;
    private CategoryDatabase categoryDatabase;
    private PartDatabase partDatabase;

    public PartCreateEditFragment() {}

    public static PartCreateEditFragment newInstance(int partId) {
        PartCreateEditFragment fragment = new PartCreateEditFragment();
        Bundle args = new Bundle();
        args.putInt(PART_ID, partId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brandDatabase    = new BrandDatabase(getContext());
        modelDatabase    = new ModelDatabase(getContext());
        categoryDatabase = new CategoryDatabase(getContext());
        partDatabase     = new PartDatabase(getContext());

        if (getArguments() != null) {
            partId     = getArguments().getInt(PART_ID) + "";
            part       = partDatabase.getById(partId);
            createForm = false;
            title      = "Edit Form";
        } else {
            part  = new Part();
            title = "Create Form";
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_part_create_edit, container, false);

        partCreateEditPartTitleTV = view.findViewById(R.id.partCreateEditPartTitleTV);
        partCreateEditBrandS      = view.findViewById(R.id.partCreateEditBrandS);
        partCreateEditModelS      = view.findViewById(R.id.partCreateEditModelS);
        partCreateEditCategoryS   = view.findViewById(R.id.partCreateEditCategoryS);
        partCreateEditPartNameET  = view.findViewById(R.id.partCreateEditPartNameET);
        partCreateEditPartQtyET   = view.findViewById(R.id.partCreateEditPartQtyET);
        partCreateEditPartPriceET = view.findViewById(R.id.partCreateEditPartPriceET);
        partCreateEditPartSaveB   = view.findViewById(R.id.partCreateEditPartSaveB);
        homeBackB                 = getActivity().findViewById(R.id.homeBackB);
        partCreateB               = getActivity().findViewById(R.id.partCreateB);
        partDeleteB               = getActivity().findViewById(R.id.partDeleteB);

        partCreateEditPartTitleTV.setText(title);

        List<Brand> brandList = new ArrayList<>();
        brandList.add(new Brand());
        brandList.addAll(brandDatabase.getAll());
        ArrayAdapter<Brand> brandAdapter = new ArrayAdapter<Brand>(getContext(), R.layout.spinner, brandList);
        brandAdapter.setDropDownViewResource(R.layout.spinner);
        partCreateEditBrandS.setAdapter(brandAdapter);
        partCreateEditBrandS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Model> modelList = new ArrayList<>();
                modelList.add(new Model());
                if (partCreateEditBrandS.getSelectedItem().toString() != null) {
                    modelList.addAll(modelDatabase.getByBrand((Brand)partCreateEditBrandS.getSelectedItem()));
                }
                ArrayAdapter<Model> modelAdapter = new ArrayAdapter<Model>(getContext(), R.layout.spinner, modelList);
                modelAdapter.setDropDownViewResource(R.layout.spinner);
                partCreateEditModelS.setAdapter(modelAdapter);
                if (!createForm) {
                    partCreateEditModelS.setSelection(getModelPosition(modelAdapter, part.getModel()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        List<Model> modelList = new ArrayList<>();
        modelList.add(new Model());
        ArrayAdapter<Model> modelAdapter = new ArrayAdapter<Model>(getContext(), R.layout.spinner, modelList);
        modelAdapter.setDropDownViewResource(R.layout.spinner);
        partCreateEditModelS.setAdapter(modelAdapter);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        categoryList.addAll(categoryDatabase.getAll());
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(getContext(), R.layout.spinner, categoryList);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        partCreateEditCategoryS.setAdapter(categoryAdapter);


        if (!createForm) {
            partCreateEditBrandS.setSelection(getBrandPosition(brandAdapter, part.getModel().getBrand()));
            partCreateEditCategoryS.setSelection(getCategoryPosition(categoryAdapter, part.getCategory()));

            partCreateEditPartNameET.setText(part.getName());
            partCreateEditPartQtyET.setText(part.getQty() + "");
            partCreateEditPartPriceET.setText(part.getPrice() + "");
            partCreateEditPartSaveB.setText("Update");

            partDeleteB.setVisibility(View.VISIBLE);
            partCreateB.setVisibility(View.GONE);
        } else {
            partDeleteB.setVisibility(View.GONE);
            partCreateB.setVisibility(View.GONE);
        }

        homeBackB.setVisibility(View.VISIBLE);

        partCreateEditPartSaveB.setOnClickListener(this::onClickSaveButton);

        return view;
    }

    private void onClickDeleteButton(View view) {
        partDeleteB.setVisibility(View.GONE);
        partDatabase.delete(part);
        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    private void onClickSaveButton(View view) {
        if (!isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        part.setModel((Model) partCreateEditModelS.getSelectedItem());
        part.setCategory((Category) partCreateEditCategoryS.getSelectedItem());
        part.setName(partCreateEditPartNameET.getText().toString());
        part.setQty(Integer.parseInt(partCreateEditPartQtyET.getText().toString()));
        part.setPrice(Float.parseFloat(partCreateEditPartPriceET.getText().toString()));

        if (part.getId() != 0) {
            partDatabase.update(part);
            Toast.makeText(getContext(), "Updated!", Toast.LENGTH_LONG).show();
        } else {
            partDatabase.insert(part);
            Toast.makeText(getContext(), "Created!", Toast.LENGTH_LONG).show();
        }

        partDeleteB.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    private boolean isValid() {
        return
            partCreateEditBrandS.getSelectedItem().toString() != null
            && partCreateEditBrandS.getSelectedItem().toString() != null
            && partCreateEditBrandS.getSelectedItem().toString() != null
            && partCreateEditPartNameET.getText().toString() != null
            && partCreateEditPartQtyET.getText().toString() != null
            && partCreateEditPartPriceET.getText().toString() != null;
    }


    private int getBrandPosition(ArrayAdapter<Brand> arrayAdapter, Brand brand) {
        for (int position = 0; position < arrayAdapter.getCount(); position++) {
            if(
                arrayAdapter.getItem(position).toString() != null &&
                arrayAdapter.getItem(position).toString().equals(brand.toString())
            ) {
                return position;
            }
        }
        return -1;
    }

    private int getModelPosition(ArrayAdapter<Model> arrayAdapter, Model model) {
        for (int position = 0; position < arrayAdapter.getCount(); position++) {
            if(
                arrayAdapter.getItem(position).toString() != null &&
                arrayAdapter.getItem(position).toString().equals(model.toString())
            ) {
                return position;
            }
        }
        return -1;
    }

    private int getCategoryPosition(ArrayAdapter<Category> arrayAdapter, Category category) {
        for (int position = 0; position < arrayAdapter.getCount(); position++) {
            if(
                arrayAdapter.getItem(position).toString() != null &&
                arrayAdapter.getItem(position).toString().equals(category.toString())
            ) {
                return position;
            }
        }
        return -1;
    }
}