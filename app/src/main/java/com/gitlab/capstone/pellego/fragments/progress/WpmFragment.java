package com.gitlab.capstone.pellego.fragments.progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.Storage;

import java.util.ArrayList;
import java.util.Date;

public class WpmFragment extends Fragment {

    private WpmViewModel mViewModel;
    private static final String TAG = "WpmFragment";
    private static final int RESULTS_COUNT = 20;
    private View root;
    private BarChart barChart;
    private ListView listView;
    protected ArrayList<ProgressResultsModel> results;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;
    protected ProgressAdapter pAdapter;

    public static WpmFragment newInstance() {
        return new WpmFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this data should come from a local content provider or remote server
        initDataSet();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_wpm, container, false);
        root.setTag(TAG);

        barChart = root.findViewById(R.id.wpmBarChart);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);
        mLayoutManager = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL, true);
        mLayoutManager.scrollToPosition(RESULTS_COUNT - 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        pAdapter = new ProgressAdapter(results);
        mRecyclerView.setAdapter(pAdapter);
        setupBarChart();
        loadBarChart();
        return root;
    }

/*    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WpmViewModel.class);
        // TODO: Use the ViewModel
    }*/

    private void initDataSet() {
        results = new ArrayList<>();
        for (int i = 0; i < RESULTS_COUNT; i++) {
            results.add(new ProgressResultsModel(i, i + 100, "February " + i +", 2021"));
        }
    }

    private void setupBarChart() {
        barChart.setFitBars(false);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.animateY(1200);
        barChart.setViewPortOffsets(70, 40, 70, 50);
    }

    private void loadBarChart() {
        ArrayList entries = new ArrayList();
        ArrayList<String> dates = new ArrayList<>();

        for(int i = 0; i < results.size(); i++) {
            entries.add(new BarEntry(results.get(i).wpm, results.get(i).wpm));
            dates.add(results.get(i).date);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Words Per Minute");
        dataSet.setDrawValues(true);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.7f);

        barChart.setScaleEnabled(true);
        barChart.setData(data);
        barChart.setVisibleXRangeMaximum(6f);
        barChart.invalidate();
    }
}