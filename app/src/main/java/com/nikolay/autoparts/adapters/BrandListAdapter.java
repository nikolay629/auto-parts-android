package com.nikolay.autoparts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nikolay.autoparts.R;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;

public class BrandListAdapter extends ArrayAdapter<Brand> {

    private static final String TAG = "BrandListAdapter";
    private Context context;
    int resource;

    public BrandListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Brand> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();

        Brand brand = new Brand(id, name);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView nameTV = convertView.findViewById(R.id.brandNameTV);

        nameTV.setText(name);
        return convertView;
    }
}
