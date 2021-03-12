package com.gitlab.capstone.pellego.fragments.progress;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.gitlab.capstone.pellego.R;
import com.gitlab.capstone.pellego.app.BaseFragment;

import java.util.ArrayList;


/**********************************************
    Chris Bordoy

    The Progress Component
 **********************************************/
public class ProgressFragment extends BaseFragment {

    private ProgressViewModel progressViewModel;
    private BarChart lastWeekBarChart;
    private LineChart lastYearLineChart;

    public ProgressFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
                final TextView textView = root.findViewById(R.id.text_progress);
        progressViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText("Progress Reports"));

        lastWeekBarChart = root.findViewById(R.id.last_week_barChart);
        lastYearLineChart = root.findViewById(R.id.last_year_lineChart);

        setupLastWeekBarChart();
        loadLastWeekBarChart();
        setupLastYearLineChart();
        loadLastYearLineChart();

        return root;
    }

    /*private void setupLastWeekBubbleChart() {
       //lastWeekBubbleChart.setFitBars(true);
        lastWeekBubbleChart.setFitsSystemWindows(true);
        lastWeekBubbleChart.getDescription().setEnabled(false);
        lastWeekBubbleChart.animateY(1400);
        lastWeekBubbleChart.getAxisRight().setEnabled(false);
        lastWeekBubbleChart.getLegend().setEnabled(false);
        lastWeekBubbleChart.getAxisLeft().setSpaceTop(30f);
        lastWeekBubbleChart.getAxisLeft().setSpaceBottom(30f);
        lastWeekBubbleChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lastWeekBubbleChart.getXAxis().setTextColor(Color.WHITE);
        lastWeekBubbleChart.getAxisLeft().setTextColor(Color.WHITE);
        lastWeekBubbleChart.getXAxis().setDrawGridLines(false);
        lastWeekBubbleChart.getAxisLeft().setDrawGridLines(false);
        lastWeekBubbleChart.setTouchEnabled(false);
        lastWeekBubbleChart.getXAxis().setAxisMinimum(0.5f);
        lastWeekBubbleChart.getXAxis().setAxisMaximum(7.5f);
        //lastWeekBubbleChart.getAxisLeft().set;
    }

    private void loadLastWeekBubbleChart() {
        ArrayList<BubbleEntry> entries1 = new ArrayList<>();
        entries1.add(new BubbleEntry(1,100,100));
        entries1.add(new BubbleEntry(2,600,600));
        entries1.add(new BubbleEntry(3,550,550));
        entries1.add(new BubbleEntry(4,150,150));
        entries1.add(new BubbleEntry(5,320,320));
        entries1.add(new BubbleEntry(6,420,420));
        entries1.add(new BubbleEntry(7,666,666));


        ArrayList<BubbleEntry> entries2 = new ArrayList<>();
        entries2.add(new BubbleEntry(1,400,400));
        entries2.add(new BubbleEntry(2,700,700));
        entries2.add(new BubbleEntry(3,450,450));
        entries2.add(new BubbleEntry(4,590,590));
        entries2.add(new BubbleEntry(5,220,220));
        entries2.add(new BubbleEntry(6,320,320));
        entries2.add(new BubbleEntry(7,566,566));

        BubbleDataSet dataSet = new BubbleDataSet(entries1, "Bubble Data Set 1");
        dataSet.setColor(getResources().getColor(R.color.green), 130);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10);

        BubbleDataSet dataSet2 = new BubbleDataSet(entries2, "Bubble Data Set 2");
        dataSet2.setColor(getResources().getColor(R.color.purple), 130);
        dataSet2.setValueTextColor(Color.WHITE);
        dataSet2.setValueTextSize(10);

        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        BubbleData data = new BubbleData(dataSets);

        ArrayList<String> days = new ArrayList<>();
        days.add("Sun");
        days.add("Mon");
        days.add("Tues");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");

        lastWeekBubbleChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return days.get((int) value - 1);
            }
        });

        lastWeekBubbleChart.setData(data);
        lastWeekBubbleChart.invalidate();
    }*/

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
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1,890));
        entries.add(new BarEntry(2,600));
        entries.add(new BarEntry(3,550));
        entries.add(new BarEntry(4,533));
        entries.add(new BarEntry(5,620));
        entries.add(new BarEntry(6,420));
        entries.add(new BarEntry(7,666));

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(1,540));
        entries2.add(new BarEntry(2,230));
        entries2.add(new BarEntry(3,297));
        entries2.add(new BarEntry(4,89));
        entries2.add(new BarEntry(5,444));
        entries2.add(new BarEntry(6,333));
        entries2.add(new BarEntry(7,111));

        BarDataSet dataSet1 = new BarDataSet(entries, "Bar Data Set 1");
        dataSet1.setColor(getResources().getColor(R.color.green), 150);
        dataSet1.setValueTextColor(Color.WHITE);
        dataSet1.setValueTextSize(10);

        BarDataSet dataSet2 = new BarDataSet(entries2, "Bar Data Set 2");
        dataSet2.setColor(getResources().getColor(R.color.purple), 150);
        dataSet2.setValueTextColor(Color.WHITE);
        dataSet2.setValueTextSize(10);

        BarData data = new BarData(dataSet1, dataSet2);

        ArrayList<String> days = new ArrayList<>();
        days.add("Sun");
        days.add("Mon");
        days.add("Tues");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");

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
        //lastYearLineChart.getXAxis().setLabelRotationAngle(-45.0f);
        lastYearLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lastYearLineChart.getXAxis().setTextColor(Color.WHITE);
        lastYearLineChart.getAxisLeft().setTextColor(Color.WHITE);
        lastYearLineChart.setTouchEnabled(false);
    }

    private void loadLastYearLineChart() {
        ArrayList<Entry> dataSet1 = new ArrayList<>();
        dataSet1.add(new Entry(1, 233));
        dataSet1.add(new Entry(2, 120));
        dataSet1.add(new Entry(3, 450));
        dataSet1.add(new Entry(4, 500));
        dataSet1.add(new Entry(5, 80));
        dataSet1.add(new Entry(6, 145));
        dataSet1.add(new Entry(7, 278));
        dataSet1.add(new Entry(8, 333));
        dataSet1.add(new Entry(9, 444));
        dataSet1.add(new Entry(10, 580));
        dataSet1.add(new Entry(11, 700));
        dataSet1.add(new Entry(12, 490));

        ArrayList<Entry> dataSet2 = new ArrayList<>();
        dataSet2.add(new Entry(1, 100));
        dataSet2.add(new Entry(2, 120));
        dataSet2.add(new Entry(3, 140));
        dataSet2.add(new Entry(4, 150));
        dataSet2.add(new Entry(5, 180));
        dataSet2.add(new Entry(6, 200));
        dataSet2.add(new Entry(7, 190));
        dataSet2.add(new Entry(8, 125));
        dataSet2.add(new Entry(9, 175));
        dataSet2.add(new Entry(10, 195));
        dataSet2.add(new Entry(11, 205));
        dataSet2.add(new Entry(12, 211));

        LineDataSet d1 = new LineDataSet(dataSet1, "Line Data Set 1");
        d1.setColor(getResources().getColor(R.color.green), 150);
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4f);
        d1.setCircleColor(getResources().getColor(R.color.green));
        d1.setValueTextColor(Color.WHITE);
        d1.setValueTextSize(10);

        LineDataSet d2 = new LineDataSet(dataSet2, "Line Data Set 2");
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

        ArrayList<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

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