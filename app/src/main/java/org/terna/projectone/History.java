package org.terna.projectone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.LinkedList;

public class History extends AppCompatActivity
{
    static LinkedList<String> historyStore = new LinkedList<>();
    TextView textView;
    String stringifiedData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textView = findViewById(R.id.textView);
        stringify();
        plot();
    }

    private void stringify()
    {
        for(String history : historyStore)
        {
            stringifiedData += "\n\n" + history;
        }
    }

    private void plot()
    {
        textView.setText(stringifiedData);
    }

}
