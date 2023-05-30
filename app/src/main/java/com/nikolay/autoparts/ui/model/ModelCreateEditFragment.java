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
import android.widget.Toast;

import com.nikolay.autoparts.ModelActivity;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModelCreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModelCreateEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;

    private Spinner modelCreateEditBrandNameS;
    private EditText modelCreateEditModelNameET;
    private Button createEditModelSaveB;

    public ModelCreateEditFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModelCreateEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModelCreateEditFragment newInstance(String param1, String param2) {
        ModelCreateEditFragment fragment = new ModelCreateEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_model_create_edit, container, false);

        modelCreateEditBrandNameS = view.findViewById(R.id.modelCreateEditBrandNameS);
        modelCreateEditModelNameET = view.findViewById(R.id.modelCreateEditModelNameET);
        createEditModelSaveB = view.findViewById(R.id.createEditModelSaveB);
        createEditModelSaveB.setOnClickListener(this::onClickSaveButton);

        brandDatabase = new BrandDatabase(getContext());

        List<Brand> brandList = brandDatabase.getAll();
        ArrayAdapter<Brand> brandAdapter = new ArrayAdapter<Brand>(getContext(), R.layout.spinner, brandList);
        brandAdapter.setDropDownViewResource(R.layout.spinner);
        modelCreateEditBrandNameS.setAdapter(brandAdapter);

        return view;
    }

    private void onClickSaveButton(View view) {
        if (!isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        Brand brand = (Brand) modelCreateEditBrandNameS.getSelectedItem();
        Model model = new Model(brand, modelCreateEditModelNameET.getText().toString());
        modelDatabase = new ModelDatabase(getContext());
        modelDatabase.insert(model);

        startActivity(new Intent(getActivity(), ModelActivity.class));
    }


    private boolean isValid() {
        return
            !modelCreateEditBrandNameS.getSelectedItem().toString().isEmpty()
            && !modelCreateEditModelNameET.getText().toString().isEmpty();
    }
}