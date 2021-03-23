package com.gitlab.capstone.pellego.fragments.progress;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;

import java.util.ArrayList;

/**********************************************
    Chris Bordoy

    The Progress Component
 **********************************************/
public class ProgressFragment extends BaseFragment {

    private View root;
    private ProgressViewModel progressViewModel;
    private BarChart lastWeekBarChart;
    private LineChart lastYearLineChart;
    private ArrayList<String> days;
    private ArrayList<String> months;
    private View header;
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);
        root = inflater.inflate(R.layout.fragment_progress, container, false);
                final TextView textView = root.findViewById(R.id.text_progress);
        progressViewModel.getText().observe(getViewLifecycleOwner(),
                s -> textView.setText(getString(R.string.title_progress_reports)));
        super.setupHeader(root);
        header = getActivity().findViewById(R.id.header_circular);
        lastWeekBarChart = root.findViewById(R.id.last_week_barChart);
        lastYearLineChart = root.findViewById(R.id.last_year_lineChart);

        ScrollView scrollView = root.findViewById(R.id.progress_scroll_view);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrolledDistance = scrollView.getScrollY();
                if (scrolledDistance != 0 && controlsVisible) {
                    hideViews();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance == 0 && !controlsVisible) {
                    showViews();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }
            }
        });

        loadProgressTextValues();
        populateDaysList();
        populateMonthsList();
        setupLastWeekBarChart();
        loadLastWeekBarChart();
        setupLastYearLineChart();
        loadLastYearLineChart();

        return root;
    }

    private void hideViews() {
        header.animate().translationY(-header.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        header.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    private void loadProgressTextValues() {
        TextView todayWordsReadTextview = root.findViewById(R.id.today_words_read_description);
        TextView todayWpmTextview = root.findViewById(R.id.today_wpm_description);
        todayWordsReadTextview.setText(progressViewModel.getTodayValues()[0]);
        todayWpmTextview.setText(progressViewModel.getTodayValues()[1]);

        TextView lastWeekWordsReadTextview = root.findViewById(R.id.last_week_words_read_description);
        TextView lastWeekWpmTextview = root.findViewById(R.id.last_week_wpm_description);
        lastWeekWordsReadTextview.setText(progressViewModel.getLastWeekValues()[0]);
        lastWeekWpmTextview.setText(progressViewModel.getLastWeekValues()[1]);

        TextView lastYearWordsReadTextview = root.findViewById(R.id.last_year_words_read_description);
        TextView lastYearWpmTextview = root.findViewById(R.id.last_year_wpm_description);
        lastYearWordsReadTextview.setText(progressViewModel.getLastYearValues()[0]);
        lastYearWpmTextview.setText(progressViewModel.getLastYearValues()[1]);
    }

    private void populateDaysList() {
        days = progressViewModel.getDaysList();
    }

    private void populateMonthsList() {
        months = progressViewModel.getMonthsList();
    }

    private void setupLastWeekBarChart() {
        lastWeekBarChart.setFitBars(true);
        lastWeekBarChart.getDescription().setEnabled(false);
        lastWeekBarChart.animateY(1400);
        lastWeekBarChart.getAxisRight().setEnabled(false);
        lastWeekBarChart.getLegend().setEnabled(false);
        lastWeekBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lastWeekBarChart.getXAxis().setTextColor(Color.WHITE);
        lastWeekBarChart.getAxisLeft().setTextColor(Color.WHITE);
        lastWeekBarChart.setTouchEnabled(false);
    }

    private void loadLastWeekBarChart() {
        BarDataSet dataSet1 = new BarDataSet(progressViewModel.getMockLastWeekWordsReadData(), "");
        dataSet1.setColor(getResources().getColor(R.color.green), 150);
        dataSet1.setValueTextColor(Color.WHITE);
        dataSet1.setValueTextSize(10);

        BarDataSet dataSet2 = new BarDataSet(progressViewModel.getMockLastWeekWpmData(), "");
        dataSet2.setColor(getResources().getColor(R.color.purple), 150);
        dataSet2.setValueTextColor(Color.WHITE);
        dataSet2.setValueTextSize(10);

        BarData data = new BarData(dataSet1, dataSet2);

        lastWeekBarChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return days.get((int) value - 1);
            }
        });

        lastWeekBarChart.setData(data);
        lastWeekBarChart.invalidate();
    }

    private void setupLastYearLineChart() {
        lastYearLineChart.getDescription().setEnabled(false);
        lastYearLineChart.animateY(1400);
        lastYearLineChart.getAxisRight().setEnabled(false);
        lastYearLineChart.getLegend().setEnabled(false);
        lastYearLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lastYearLineChart.getXAxis().setTextColor(Color.WHITE);
        lastYearLineChart.getXAxis().setAxisMinimum(0.5f);
        lastYearLineChart.getAxisLeft().setTextColor(Color.WHITE);
        lastYearLineChart.setTouchEnabled(false);
    }

    private void loadLastYearLineChart() {
        LineDataSet d1 = new LineDataSet(progressViewModel.getMockLastYearWordsReadData(),"");
        d1.setColor(getResources().getColor(R.color.green), 150);
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4f);
        d1.setCircleColor(getResources().getColor(R.color.green));
        d1.setValueTextColor(Color.WHITE);
        d1.setValueTextSize(10);

        LineDataSet d2 = new LineDataSet(progressViewModel.getMockLastYearWpmData(), "");
        d2.setColor(getResources().getColor(R.color.purple), 150);
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4f);
        d2.setCircleColor(getResources().getColor(R.color.purple));
        d2.setValueTextColor(Color.WHITE);
        d2.setValueTextSize(10);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(d1);
        dataSets.add(d2);

        LineData data = new LineData(dataSets);

        lastYearLineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return months.get((int) value - 1);
            }
        });

        lastYearLineChart.setData(data);
        lastYearLineChart.invalidate();
    }
}