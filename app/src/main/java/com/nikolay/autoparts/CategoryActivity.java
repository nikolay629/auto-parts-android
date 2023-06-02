package com.nikolay.autoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.databinding.ActivityCategoryBinding;
import com.nikolay.autoparts.ui.brand.BrandListFragment;
import com.nikolay.autoparts.ui.category.CategoryCreateEditFragment;
import com.nikolay.autoparts.ui.category.CategoryListFragment;
import com.nikolay.autoparts.ui.model.ModelCreateEditFragment;
import com.nikolay.autoparts.ui.model.ModelListFragment;

public class CategoryActivity extends BaseActivity {

    private ActivityCategoryBinding activityCategoryBinding;
    private FloatingActionButton createB;
    private FloatingActionButton deleteB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_category);

        createB = findViewById(R.id.categoryCreateB);
        deleteB = findViewById(R.id.categoryDeleteB);

        createB.setOnClickListener(this::onCreateButton);
        deleteB.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.categoryFL,
                new CategoryListFragment(),
                "CategoryListFragment"
        ).commit();
    }


    private void onCreateButton(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("CategoryListFragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.categoryFL,
                    new CategoryCreateEditFragment(),
                    "CategoryCreateEditFragment"
            ).commit();
            createB.setImageResource(R.drawable.back);
        } else {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.categoryFL,
                    new CategoryListFragment(),
                    "CategoryListFragment"
            ).commit();
            createB.setImageResource(R.drawable.create);
        }
    }
}