package com.example.think.androidfun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.think.androidfun.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by think on 2016/5/17.
 */
public class SelectView extends LinearLayout implements View.OnClickListener {
    private static final int DEFAULT_DATA = 0;

    Context context;
    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;

    @Bind(R.id.tv_1)
    Button tv1;
    @Bind(R.id.tv_2)
    Button tv2;
    @Bind(R.id.tv_3)
    Button tv3;
    @Bind(R.id.tv_4)
    Button tv4;
    @Bind(R.id.tv_5)
    Button tv5;
    @Bind(R.id.tv_6)
    Button tv6;


    public SelectView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    private void init() {
        View view = View.inflate(context, R.layout.select_layout, this);
        chart = (ColumnChartView) view.findViewById(R.id.chart);
        ButterKnife.bind(this, view);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        generateDefaultData();
    }

    @Override
    public void onClick(View v) {
        resetTv(v);
        switch (v.getId()) {
            case R.id.tv_1:
                break;
            case R.id.tv_2:
                break;
            case R.id.tv_3:
                break;
            case R.id.tv_4:
                break;
            case R.id.tv_5:
                break;
            case R.id.tv_6:
                break;
        }
    }

    private void resetTv(View v) {
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);
        tv5.setSelected(false);
        tv6.setSelected(false);
        v.setSelected(true);

    }

    private void generateDefaultData() {
        int numSubcolumns = 1;
        int numColumns = 8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);
        chart.setBackgroundColor(getResources().getColor(R.color.transparent));


    }

}
