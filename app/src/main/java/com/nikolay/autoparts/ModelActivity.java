package com.nikolay.autoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.databinding.ActivityModelBinding;
import com.nikolay.autoparts.ui.brand.BrandCreateEditFragment;
import com.nikolay.autoparts.ui.brand.BrandListFragment;
import com.nikolay.autoparts.ui.model.ModelCreateEditFragment;
import com.nikolay.autoparts.ui.model.ModelListFragment;

public class ModelActivity extends BaseActivity {

    private ActivityModelBinding activityModelBinding;
    private FloatingActionButton createB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityModelBinding = ActivityModelBinding.inflate(getLayoutInflater());
        setContentView(activityModelBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_model);

        createB = findViewById(R.id.modelCreateB);
        createB.setOnClickListener(this::onCreateButton);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.modelFL,
                new ModelListFragment(),
                "ModelListFragment"
        ).commit();
    }

    private void onCreateButton(View view) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("ModelListFragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.modelFL,
                    new ModelCreateEditFragment(),
                    "ModelCreateEditFragment"
            ).commit();
            createB.setImageResource(R.drawable.back);
        } else {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.modelFL,
                    new ModelListFragment(),
                    "ModelListFragment"
            ).commit();
            createB.setImageResource(R.drawable.create);
        }
    }
}