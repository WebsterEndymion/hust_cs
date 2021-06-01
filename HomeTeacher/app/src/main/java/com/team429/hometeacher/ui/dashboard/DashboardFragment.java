package com.team429.hometeacher.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.team429.hometeacher.R;
import com.team429.hometeacher.data.RuntimeData;

/**
 * 设置页面
 * @author zzh
 */
public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private SeekBar seekBar;
    private Spinner spinner;
    private TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // 连接组件
        seekBar = root.findViewById(R.id.seekbar);
        spinner = root.findViewById(R.id.spinner);
        textView = root.findViewById(R.id.seekbar_value);

        // 设置初值
        seekBar.setProgress(RuntimeData.Target.getValue()/5);
        int a = RuntimeData.Target.getValue();
        textView.setText("数量:"+RuntimeData.Target.getValue());

        // 设置滑动条
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("数量:"+seekBar.getProgress()*5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                RuntimeData.Target.setValue(seekBar.getProgress()*5);
                int a = RuntimeData.Target.getValue();
            }
        });
        // 设置默认值 注意是减一
        // 因为 spinner的ITem选中id从0开始，而年级从1开始
        spinner.setSelection(RuntimeData.Difficulty.getValue() - 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RuntimeData.Difficulty.setValue(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return root;
    }
}