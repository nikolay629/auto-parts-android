package com.nikolay.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nikolay.autoparts.databinding.ActivityCategoryBinding;

public class CategoryActivity extends BaseActivity {

    private ActivityCategoryBinding activityCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_category);

    }
}