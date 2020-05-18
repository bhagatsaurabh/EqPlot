package org.terna.projectone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EquationMaker extends AppCompatActivity
{
    TextView input;
    Button b;
    String inputText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_maker);

        input = findViewById(R.id.input);
        b = findViewById(R.id.clear);
        b.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                input.setText("");
                return false;
            }
        });
    }

    public void numberPressed(View view)
    {
        input.setText(input.getText().toString() + ((Button)view).getText().toString());
    }

    public void specialOperator(View view)
    {
        input.setText(input.getText().toString() + ((Button)view).getText().toString().toUpperCase() + "(");
    }

    public void plotFunction(View view)
    {
        if (input.getText().toString().contains("x") && input.getText().toString().contains("y")) {
            // 3D Plot
            Intent i = new Intent(this, Graph3D.class);
            i.putExtra("equation", input.getText().toString());
            startActivity(i);
        } else {
            // 2D Plot
            Intent i = new Intent(this, Graph.class);
            i.putExtra("equation", input.getText().toString());
            startActivity(i);
        }
    }

    public void clear(View view)
    {
        inputText = input.getText().toString();
        if(inputText.length() > 0)  input.setText(inputText.substring(0, inputText.length()-1));
    }

    public void showSample(View view)
    {
        Intent i = new Intent(this, Sample.class);
        startActivity(i);
    }
}
