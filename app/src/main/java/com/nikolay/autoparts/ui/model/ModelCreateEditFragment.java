package com.nikolay.autoparts.ui.model;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.BrandActivity;
import com.nikolay.autoparts.ModelActivity;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModelCreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModelCreateEditFragment extends Fragment {

    private static final String MODEL_ID = "modelId";

    private boolean createForm = true;
    private String title;
    private String modelId;


    private TextView createEditModelTitleTV;
    private Spinner modelCreateEditBrandNameS;
    private EditText modelCreateEditModelNameET;
    private Button createEditModelSaveB;
    private FloatingActionButton modelDeleteB;

    private Brand brand;
    private Model model;
    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;

    public ModelCreateEditFragment() {}

    public static ModelCreateEditFragment newInstance(int modelId) {
        ModelCreateEditFragment fragment = new ModelCreateEditFragment();
        Bundle args = new Bundle();
        args.putInt(MODEL_ID, modelId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandDatabase = new BrandDatabase(getContext());
        modelDatabase = new ModelDatabase(getContext());

        if (getArguments() != null) {
            modelId = getArguments().getInt(MODEL_ID) + "";
            model = modelDatabase.getById(modelId);
            createForm = false;
            title      = "Edit Form";
        } else {
            model = new Model();
            title = "Create Form";
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_model_create_edit, container, false);

        createEditModelTitleTV = view.findViewById(R.id.createEditModelTitleTV);
        modelCreateEditBrandNameS = view.findViewById(R.id.modelCreateEditBrandNameS);
        modelCreateEditModelNameET = view.findViewById(R.id.modelCreateEditModelNameET);
        createEditModelSaveB = view.findViewById(R.id.createEditModelSaveB);
        modelDeleteB = getActivity().findViewById(R.id.modelDeleteB);

        createEditModelTitleTV.setText(title);
        modelCreateEditModelNameET.setText(model.getName());
        createEditModelSaveB.setOnClickListener(this::onClickSaveButton);

        List<Brand> brandList = brandDatabase.getAll();
        ArrayAdapter<Brand> brandAdapter = new ArrayAdapter<Brand>(getContext(), R.layout.spinner, brandList);
        brandAdapter.setDropDownViewResource(R.layout.spinner);
        modelCreateEditBrandNameS.setAdapter(brandAdapter);

        if (!createForm) {
            modelCreateEditBrandNameS.setSelection(getItemPosition(brandAdapter, model.getBrand()));
            createEditModelSaveB.setText("Update");
            modelDeleteB.setVisibility(View.VISIBLE);
            modelDeleteB.setOnClickListener(this::onClickDeleteButton);
        }

        return view;
    }

    private void onClickDeleteButton(View view) {
        modelDeleteB.setVisibility(View.GONE);
        modelDatabase.delete(model);
        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), ModelActivity.class));
    }

    private void onClickSaveButton(View view) {
        if (!isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        brand = (Brand) modelCreateEditBrandNameS.getSelectedItem();
        model.setBrand(brand);
        model.setName(modelCreateEditModelNameET.getText().toString());
        if (model.getId() != 0) {
            modelDatabase.update(model);
            Toast.makeText(getContext(), "Updated!", Toast.LENGTH_LONG).show();
        } else {
            modelDatabase.insert(model);
            Toast.makeText(getContext(), "Created!", Toast.LENGTH_LONG).show();
        }

        modelDeleteB.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), ModelActivity.class));
    }


    private boolean isValid() {
        return
            !modelCreateEditBrandNameS.getSelectedItem().toString().isEmpty()
            && !modelCreateEditModelNameET.getText().toString().isEmpty();
    }

    private int getItemPosition(ArrayAdapter<Brand> arrayAdapter, Brand brand) {
        for (int position = 0; position < arrayAdapter.getCount(); position++) {
            if(arrayAdapter.getItem(position).toString().equals(brand.toString())) {
                return position;
            }
        }
        return -1;
    }
}