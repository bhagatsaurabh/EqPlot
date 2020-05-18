package org.terna.projectone;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udojava.evalex.Expression;

public class Graph extends AppCompatActivity
{
    LinearLayout linearLayout;
    private LineGraphSeries<DataPoint> seriesx;
    String equation = "";
    TextView leftStat, rightStat, resolutionText;
    SeekBar leftRange, rightRange, resolution;
    int lRange, rRange;
    GraphView graphx;
    float maxY, minY;
    float expressionResult;
    int resolve;
    Switch normalize;
    boolean isNormalized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        linearLayout = findViewById(R.id.linearLayout);
        equation = getIntent().getStringExtra("equation");
        leftStat = findViewById(R.id.leftStat);
        rightStat = findViewById(R.id.rightStat);
        resolutionText = findViewById(R.id.resolutionText);
        leftRange = findViewById(R.id.rangeLeft);
        rightRange = findViewById(R.id.rangeRight);
        resolution = findViewById(R.id.resolution);
        normalize = findViewById(R.id.normalize);

        resolve = 1;

        lRange = -200;
        rRange = 200;
        maxY = Float.MIN_VALUE;
        minY = Float.MAX_VALUE;

        prepareGraph();

        leftRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                lRange = (leftRange.getProgress() - 360);
                leftStat.setText(String.valueOf(lRange));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                computeAndPlot();
            }
        });

        rightRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                rRange = (rightRange.getProgress() - 360);
                rightStat.setText(String.valueOf(rRange));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                computeAndPlot();
            }
        });

        resolution.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if((resolution.getProgress() + 1) < ((rRange - lRange) / 2))
                {
                    resolve = resolution.getProgress() + 1;
                    resolutionText.setText(String.valueOf(resolution.getProgress()+1));
                }
                else
                {
                    Toast.makeText(Graph.this, "Resolution Should Be Less Than X-Range !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                computeAndPlot();
            }
        });

        normalize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                isNormalized = b;
                computeAndPlot();
            }
        });

        computeAndPlot();
    }

    GraphView createGraph(String title)
    {
        GraphView graph = new GraphView(this);
        graph.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        graph.setTitle(title);
        graph.setTitleColor(Color.DKGRAY);
        graph.getGridLabelRenderer().setGridColor(Color.GRAY);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setMinX(-360);
        graph.getViewport().setMaxX(360);
        graph.getViewport().setMinY(-1000);
        graph.getViewport().setMaxY(1000);
        graph.setTitleColor(Color.WHITE);
        linearLayout.addView(graph);

        return graph;
    }

    void prepareGraph()
    {
        graphx = createGraph("Plot Of Function");
    }

    void computeAndPlot()
    {
        String tempEquation = "";
        graphx.removeAllSeries();
        seriesx = new LineGraphSeries<>();
        seriesx.setThickness(3);
        seriesx.setColor(Color.WHITE);
        graphx.addSeries(seriesx);

        try
        {
            for(int i = lRange ; i < rRange ; i+=resolve)
            {
                tempEquation = equation.replaceAll((equation.contains("y") ? "y": "x"), String.valueOf(i));

                if(!isNormalized)
                {
                    expressionResult = new Expression(tempEquation).eval().floatValue();

                    if(expressionResult > maxY) maxY = expressionResult;
                    if(expressionResult < minY) minY = expressionResult;
                }
                else    expressionResult = normalize(new Expression(tempEquation).eval().floatValue());

                seriesx.appendData(new DataPoint(i, expressionResult), true, rRange - lRange);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Bad Equation !", Toast.LENGTH_SHORT).show();
        }

        graphx.getViewport().setMinX(lRange);
        graphx.getViewport().setMaxX(rRange);
    }

    float normalize(float x)
    {
        return (x - minY)/(maxY - minY);
    }
}
