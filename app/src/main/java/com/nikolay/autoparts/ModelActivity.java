package com.nikolay.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nikolay.autoparts.databinding.ActivityModelBinding;

public class ModelActivity extends BaseActivity {

    private ActivityModelBinding activityModelBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityModelBinding = ActivityModelBinding.inflate(getLayoutInflater());
        setContentView(activityModelBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_model);

    }
}