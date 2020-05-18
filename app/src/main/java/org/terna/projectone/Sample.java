package org.terna.projectone;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Sample extends AppCompatActivity
{
    LinearLayout linearLayout;
    private LineGraphSeries<DataPoint> seriesx, seriesy, seriesz;
    GraphView graphx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        linearLayout = findViewById(R.id.linearLayout);
        prepareGraph();
        plot();
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
        linearLayout.addView(graph);

        return graph;
    }

    void prepareGraph()
    {
        graphx = createGraph("Plot Of Function");
        seriesx = new LineGraphSeries<>();
        seriesx.setThickness(3);
        seriesx.setColor(Color.RED);
        seriesy = new LineGraphSeries<>();
        seriesy.setThickness(3);
        seriesy.setColor(Color.GREEN);
        seriesz = new LineGraphSeries<>();
        seriesz.setThickness(3);
        seriesz.setColor(Color.BLUE);
        seriesx.setTitle("sin(x)");
        seriesy.setTitle("tanh(x)");
        seriesz.setTitle("(x^2)");
        graphx.addSeries(seriesx);
        graphx.addSeries(seriesy);
        graphx.addSeries(seriesz);
        graphx.getLegendRenderer().setVisible(true);
    }

    void plot()
    {
        for(int i = -360 ; i < 360 ; i++)
        {
            seriesx.appendData(new DataPoint(i, Math.sin(Math.toRadians(i))), true, 720);
            seriesy.appendData(new DataPoint(i, Math.tanh(i)), true, 720);
        }
        for(int i = -100 ; i < 100 ; i++)
        {
            seriesz.appendData(new DataPoint(i, computeForY(i)), true, 200);
        }
    }

    float computeForY(float x)
    {
        return (x*x/2000);
    }
}
