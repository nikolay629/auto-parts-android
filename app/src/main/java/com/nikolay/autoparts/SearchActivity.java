package com.nikolay.autoparts;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikolay.autoparts.databinding.ActivitySearchBinding;
import com.nikolay.autoparts.ui.search.PartCreateEditFragment;
import com.nikolay.autoparts.ui.search.PartResultFragment;
import com.nikolay.autoparts.ui.search.SearchFragment;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding activitySearchBinding;
    private FloatingActionButton homeBackB;
    private FloatingActionButton partCreateB;
    private FloatingActionButton partDeleteB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(activitySearchBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_home);
        homeBackB = findViewById(R.id.homeBackB);
        partCreateB = findViewById(R.id.partCreateB);
        partDeleteB = findViewById(R.id.partDeleteB);

        homeBackB.setVisibility(View.GONE);
        partCreateB.setVisibility(View.VISIBLE);
        partDeleteB.setVisibility(View.GONE);

        homeBackB.setOnClickListener(this::onClickBackButton);
        partCreateB.setOnClickListener(this::onClickPartCreateButton);


        getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                new SearchFragment(),
                "SearchFragment"
        ).commit();
    }

    private void onClickPartCreateButton(View view) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                new PartCreateEditFragment(),
                "PartCreateEditFragment"
        ).commit();
    }


    private void onClickBackButton(View view) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.searchFL,
                new SearchFragment(),
                "SearchFragment"
        ).commit();
    }
}