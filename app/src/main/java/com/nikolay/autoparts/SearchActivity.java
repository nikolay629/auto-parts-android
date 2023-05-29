package com.nikolay.autoparts;

import android.os.Bundle;
import android.view.View;

import com.nikolay.autoparts.databinding.ActivitySearchBinding;
import com.nikolay.autoparts.ui.search.PartResultFragment;
import com.nikolay.autoparts.ui.search.SearchFragment;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding activitySearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(activitySearchBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_home);



        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayout,
                new SearchFragment(),
                "SearchFragment"
        ).commit();
    }

    public void search(View view) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frameLayout,
                new PartResultFragment(),
                "PartResultFragment"
        ).commit();
    }
}