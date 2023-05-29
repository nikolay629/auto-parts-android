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
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;
import java.util.List;

public class PartListAdapter extends ArrayAdapter<Part> {

    private static final String TAG = "PartListAdapter";
    private Context context;
    int resource;

    public PartListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Part> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        int qty = getItem(position).getQty();
        float price = getItem(position).getPrice();

        Part part = new Part(name, qty, price);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView nameTV = convertView.findViewById(R.id.partNameTV);
        TextView qtyTV = convertView.findViewById(R.id.partQtyTV);
        TextView priceTV = convertView.findViewById(R.id.partPriceTV);

        nameTV.setText(name);
        qtyTV.setText(qty + "s");
        priceTV.setText(price + "");

        return convertView;
    }
}
