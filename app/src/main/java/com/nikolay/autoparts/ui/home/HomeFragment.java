package com.nikolay.autoparts.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.nikolay.autoparts.MainActivity;
import com.nikolay.autoparts.R;
import com.nikolay.autoparts.databinding.FragmentHomeBinding;
import com.nikolay.autoparts.ui.part.PartFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.homeTitleTV;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.homeFilterB.setOnClickListener(this::searchPart);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void searchPart(View view) {
        Navigation.findNavController(view).navigate(R.id.nav_part);
    }
}