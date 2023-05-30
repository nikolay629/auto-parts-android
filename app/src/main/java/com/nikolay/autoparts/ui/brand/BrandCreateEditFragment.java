package com.nikolay.autoparts.ui.brand;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BrandDatabase brandDatabase;
    private EditText brandNameET;
    private Button createB;

    public BrandCreateEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrandCreateEditFragment newInstance(String param1, String param2) {
        BrandCreateEditFragment fragment = new BrandCreateEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_brand_create_edit, container, false);

        brandNameET = view.findViewById(R.id.createEditBrandNameET);
        createB = view.findViewById(R.id.createEditBrandSaveB);

        createB.setOnClickListener(this::onClickCreateButton);
        return view;

    }

    private void onClickCreateButton(View view) {
        if (!this.isValid()) {
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        brandDatabase = new BrandDatabase(getContext());
        brandDatabase.insert(new Brand(brandNameET.getText().toString()));
        startActivity(new Intent(getActivity(), BrandActivity.class));
    }

    private boolean isValid() {
        return !brandNameET.getText().toString().isEmpty();
    }
}