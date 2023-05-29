package com.nikolay.autoparts;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.databinding.ActivityBrandBinding;
import com.nikolay.autoparts.ui.brand.BrandListFragment;
import com.nikolay.autoparts.ui.brand.CreateEditFragment;

public class BrandActivity extends BaseActivity {

    private ActivityBrandBinding activityBrandBinding;
    private FloatingActionButton createB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBrandBinding = ActivityBrandBinding.inflate(getLayoutInflater());
        setContentView(activityBrandBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_brand);

        createB = findViewById(R.id.categoryCreateB);
        createB.setOnClickListener(this::onCreateButton);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.brandFL,
                new BrandListFragment(),
                "BrandListFragment"
        ).commit();
    }


    private void onCreateButton(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("BrandListFragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.brandFL,
                    new CreateEditFragment(),
                    "CreateEditFragment"
            ).commit();
            createB.setImageResource(R.drawable.back);
        } else {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.brandFL,
                    new BrandListFragment(),
                    "BrandListFragment"
            ).commit();
            createB.setImageResource(R.drawable.create);
        }
    }
}