package com.team429.hometeacher.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.team429.hometeacher.R;
import com.team429.hometeacher.data.RuntimeData;
import com.team429.hometeacher.ui.question.QuestionFragment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        final LinearLayout linearLayout = root.findViewById(R.id.home_main_layout);
        final FragmentManager fragmentManager = getParentFragmentManager();
        //homeViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        homeViewModel.getFragment().observe(getViewLifecycleOwner(), f -> {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.replace(R.id.home_view, f);
            beginTransaction.commit();
        });
        ResultSet today = null;
            try {
                today = RuntimeData.getHistoryToday();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //today.moveToFirst();
        try {
            if (today.getInt(3) < today.getInt(4)) {
                homeViewModel.setFragment(new QuestionFragment());
            } else {
                // show finished
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //today.close();
        return root;
    }
}