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
import com.nikolay.autoparts.model.Category;

import java.util.ArrayList;

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private static final String TAG = "CategoryListAdapter";
    private Context context;
    int resource;

    public CategoryListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView nameTV = convertView.findViewById(R.id.categoryNameTV);

        nameTV.setText(name);
        return convertView;
    }
}
