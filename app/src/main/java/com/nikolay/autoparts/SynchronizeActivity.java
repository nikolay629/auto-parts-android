package com.nikolay.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.database.PartDatabase;
import com.nikolay.autoparts.databinding.ActivityCategoryBinding;
import com.nikolay.autoparts.databinding.ActivitySynchronizeBinding;
import com.nikolay.autoparts.rest.BrandRest;
import com.nikolay.autoparts.rest.CategoryRest;
import com.nikolay.autoparts.rest.ModelRest;
import com.nikolay.autoparts.rest.PartRest;

public class SynchronizeActivity extends BaseActivity {

    private ActivitySynchronizeBinding activitySynchronizeBinding;

    private Button synchronizeCheckB;
    private Button synchronizeSendDataB;
    private Button synchronizeUpdateServiceB;
    private Button synchronizeCreateB;
    private Button synchronizeUpdateB;
    private Button synchronizeDuplicateB;

    private BrandDatabase brandDatabase;
    private ModelDatabase modelDatabase;
    private CategoryDatabase categoryDatabase;
    private PartDatabase partDatabase;

    private BrandRest brandRest;
    private ModelRest modelRest;
    private CategoryRest categoryRest;
    private PartRest partRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySynchronizeBinding = ActivitySynchronizeBinding.inflate(getLayoutInflater());
        setContentView(activitySynchronizeBinding.getRoot());
        navigationView.setCheckedItem(R.id.nav_synchronize);

        brandDatabase = new BrandDatabase(this);
        modelDatabase = new ModelDatabase(this);
        categoryDatabase = new CategoryDatabase(this);
        partDatabase = new PartDatabase(this);

        brandRest = new BrandRest(this);
        modelRest = new ModelRest(this);
        categoryRest = new CategoryRest(this);
        partRest = new PartRest(this);

        synchronizeCheckB = findViewById(R.id.synchronizeCheckB);
        synchronizeSendDataB = findViewById(R.id.synchronizeSendDataB);
        synchronizeUpdateServiceB = findViewById(R.id.synchronizeUpdateServiceB);
        synchronizeCreateB = findViewById(R.id.synchronizeCreateB);
        synchronizeUpdateB = findViewById(R.id.synchronizeUpdateB);
        synchronizeDuplicateB = findViewById(R.id.synchronizeDuplicateB);

        synchronizeCheckB.setOnClickListener(this::onClickCheckButton);
        synchronizeSendDataB.setOnClickListener(this::onClickSendDataB);
        synchronizeUpdateServiceB.setOnClickListener(this::onClickUpdateServiceB);
        synchronizeCreateB.setOnClickListener(this::onClickCreateButton);
        synchronizeUpdateB.setOnClickListener(this::onClickUpdateLocalDBButton);
        synchronizeDuplicateB.setOnClickListener(this::onClickDuplicateButton);
    }


    private void onClickCheckButton(View view) {
        brandRest.checkForDifference(brandDatabase.getAll());
        categoryRest.checkForDifference(categoryDatabase.getAll());
        modelRest.checkForDifference(modelDatabase.getAll());
        partRest.checkForDifference(partDatabase.getAll());
    }

    private void onClickSendDataB(View view) {
        brandRest.sendAllNewBrand(brandDatabase.getAllNewData());
        categoryRest.sendAllNewCategory(categoryDatabase.getAllNewData());
        modelRest.sendAllNewModel(modelDatabase.getAllNewData());
        partRest.sendAllNewPart(partDatabase.getAllNewData());
    }

    private void onClickUpdateServiceB(View view) {
        brandRest.update(brandDatabase.getAllOldData());
        categoryRest.update(categoryDatabase.getAllOldData());
        modelRest.update(modelDatabase.getAllOldData());
        partRest.update(partDatabase.getAllOldData());
    }

    private void onClickCreateButton(View view) {
        brandRest.getAllNew(brandDatabase.getAllOldData());
        categoryRest.getAllNew(categoryDatabase.getAllOldData());
        modelRest.getAllNew(modelDatabase.getAllOldData());
        partRest.getAllNew(partDatabase.getAllOldData());
    }

    private void onClickUpdateLocalDBButton(View view) {

    }

    private void onClickDuplicateButton(View view) {

    }
}