package com.example.pellego.ui.progress;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pellego.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**********************************************
    Chris Bordoy

    The Progress Component
 **********************************************/
public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;
    private PieChart pieChart;
    private PieChart pieChart2;
    private LineChart lineChart;
    private BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
        pieChart = root.findViewById(R.id.piechart);
        pieChart2 = root.findViewById(R.id.piechart2);
        lineChart = root.findViewById(R.id.linechart);
        barChart = root.findViewById(R.id.barchart);

        setupPieChart();
        loadPieChartData();
        setupPieChart2();
        loadPieChartData2();
        setupLineChart();
        loadLineChartData();
        setupBarChart();
        loadBarChart();
        return root;
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        //set legend
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void setupPieChart2() {
        pieChart2.setDrawHoleEnabled(false);
        pieChart2.setUsePercentValues(true);
        pieChart2.setEntryLabelTextSize(12);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.setCenterTextSize(24);
        pieChart2.getDescription().setEnabled(false);

        //set legend
        Legend l = pieChart2.getLegend();
        l.setTextSize(14);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void setupLineChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setExtraOffsets(10,10,10,10);

        Legend l = lineChart.getLegend();
        l.setTextSize(14);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setEnabled(true);
    }

    private void setupBarChart() {
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1400);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(0.2f, "Food & Dining"));
            entries.add(new PieEntry(0.15f, "Medical"));
            entries.add(new PieEntry(0.10f, "Entertainment"));
            entries.add(new PieEntry(0.25f, "Electricity & Gas"));
            entries.add(new PieEntry(0.3f, "Housing"));

            ArrayList<Integer> colors = new ArrayList<>();
            for (int color: ColorTemplate.MATERIAL_COLORS) {
                colors.add(color);
            }

            for (int color: ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(color);
            }

            PieDataSet dataSet = new PieDataSet(entries, null);
            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter(pieChart));
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.BLACK);

            pieChart.setData(data);
            //This lets the pie chart know data has been updated and refreshes data
            pieChart.invalidate();

            //pieChart animation
            pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void loadPieChartData2() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Food & Dining"));
        entries.add(new PieEntry(0.15f, "Medical"));
        entries.add(new PieEntry(0.10f, "Entertainment"));
        entries.add(new PieEntry(0.25f, "Electricity & Gas"));


        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart2));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart2.setData(data);
        //This lets the pie chart know data has been updated and refreshes data
        pieChart2.invalidate();

        //pieChart animation
        pieChart2.animateY(1400, Easing.EaseInOutQuad);
    }

    private void loadLineChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(.2f, .25f));
        entries.add(new Entry(.72f, .1f));
        entries.add(new Entry(.3f, .8f));
        entries.add(new Entry(.9f, .18f));
        entries.add(new Entry(.6f, .9f));

        LineDataSet dataSet = new LineDataSet(entries, "Data Set");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        LineData data = new LineData(dataSet);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        lineChart.setData(data);
        lineChart.invalidate();

        lineChart.animateX(1400, Easing.EaseInOutElastic);
    }

    private void loadBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(2000,890));
        entries.add(new BarEntry(2001,600));
        entries.add(new BarEntry(2002,550));
        entries.add(new BarEntry(2003,1590));
        entries.add(new BarEntry(2004,320));
        entries.add(new BarEntry(2005,420));
        entries.add(new BarEntry(2006,666));
        entries.add(new BarEntry(2007,777));
        entries.add(new BarEntry(2008,999));

        BarDataSet dataSet = new BarDataSet(entries, "Bar Data Set");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate();
    }
}