package com.nikolay.autoparts;

import android.os.Bundle;

import com.nikolay.autoparts.databinding.ActivityBrandBinding;

public class BrandActivity extends BaseActivity {

    private ActivityBrandBinding activityBrandBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBrandBinding = ActivityBrandBinding.inflate(getLayoutInflater());
        setContentView(activityBrandBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_brand);

    }


}