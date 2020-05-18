/*
@Author
Saurabh Bhagat
*/

package org.terna.projectone;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NestedLayout2 extends AppCompatActivity
{
    //Notations Are Just For Saving Calculations To History.
    TextView input, previous;
    String notation = "", inputText = "";
    double operandOne = 0.0, operandTwo = 0.0, result;
    int operator = 0;
    boolean advancedPanel = false;
    LinearLayout basicPane, advancedPane;
    Button adButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_layout2);
        input = findViewById(R.id.input);
        previous = findViewById(R.id.previous);
        basicPane = findViewById(R.id.basicPanel);
        advancedPane = findViewById(R.id.advancedPanel);
        adButton = findViewById(R.id.adButton);
    }

    //Method Linked With Operator Buttons.
    public void operatorPressed(View view)
    {
        inputText = input.getText().toString();
        //If Input Is Blank, Just Update The Operator And Return.
        if("".equals(inputText))
        {
            operator = view.getId();
            return;
        }
        else
        {
            try
            {
                //If No Operator Is Currently Selected, Just Store First Operand.
                if(operator == 0)
                {
                    operandOne = Double.parseDouble(inputText);
                    previous.setText(inputText);
                }
                //If Previously Some Operator Is Stored In Variable 'operator' Then Store Second Operand And Calculate Result.
                else
                {
                    operandTwo = Double.parseDouble(inputText);
                    previous.setText(String.valueOf(calculate(view)));
                }
            }
            catch(NumberFormatException nfe)
            {
                Toast.makeText(this, "Not A Number !", Toast.LENGTH_SHORT).show();
                clear(view);
                return;
            }
        }

        //At The End We Have To Update The Currently Selected Operator And Some Presentation Stuff.
        operator = view.getId();
        input.setText("");
    }

    //To Handle Single Operand Calculations For Eg. sin, cos, tan, log etc.
    public void specialOperatorPressed(View view)
    {
        inputText = input.getText().toString();
        try
        {
            operandOne = Double.parseDouble(inputText);
        }
        catch(NumberFormatException nfe)
        {
            Toast.makeText(this, "Not A Number !", Toast.LENGTH_SHORT).show();
            clear(view);
            return;
        }

        switch(view.getId())
        {
            case R.id.sin :
            {
                result = Math.sin(operandOne);
                notation = "sin(" + inputText + ")";
                break;
            }
            case R.id.cos :
            {
                result = Math.cos(operandOne);
                notation = "cos(" + inputText + ")";
                break;
            }
            case R.id.tan :
            {
                result = Math.tan(operandOne);
                notation = "tan(" + inputText + ")";
                break;
            }
            case R.id.log :
            {
                result = Math.log10(operandOne);
                notation = "log(" + inputText + ")";
                break;
            }
            case R.id.ln :
            {
                result = Math.log(operandOne);
                notation = "ln(" + inputText + ")";
                break;
            }
            case R.id.square :
            {
                result = Math.pow(operandOne, 2.0);
                notation = "(" + inputText + ")^2";
                break;
            }
            case R.id.cube :
            {
                result = Math.pow(operandOne, 3.0);
                notation = "(" + inputText + ")^3";
                break;
            }
            case R.id.root :
            {
                result = Math.sqrt(operandOne);
                notation = "sqrt(" + inputText + ")";
                break;
            }
        }

        input.setText(String.valueOf(result));
        previous.setText(notation);
        addToHistory(notation + " = " + String.valueOf(result));
        //Update The First Operand To Result, Such That We Can Calculate Continuously Without Use Of Equals Operator.
        operandOne = result;
    }

    //To Handle Two Operand Calculations For Eg. +, -, /, *, %, ^
    public double calculate(View view)
    {
        inputText = input.getText().toString();
        try
        {
            operandTwo = Double.parseDouble(inputText);
        }
        catch(NumberFormatException nfe)
        {
            Toast.makeText(this, "Not A Number !", Toast.LENGTH_SHORT).show();
            clear(view);
            return 0.0;
        }
        switch(operator)
        {
            case R.id.plus :
            {
                result = operandOne + operandTwo;
                addToHistory(String.valueOf(operandOne) + " + " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
            case R.id.minus :
            {
                result = operandOne - operandTwo;
                addToHistory(String.valueOf(operandOne) + " - " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
            case R.id.divide :
            {
                result = operandOne / operandTwo;
                addToHistory(String.valueOf(operandOne) + " / " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
            case R.id.mult :
            {
                result = operandOne * operandTwo;
                addToHistory(String.valueOf(operandOne) + " X " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
            case R.id.mod :
            {
                result = operandOne % operandTwo;
                addToHistory(String.valueOf(operandOne) + " mod " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
            case R.id.raise :
            {
                result = Math.pow(operandOne, operandTwo);
                addToHistory(String.valueOf(operandOne) + " ^ " + String.valueOf(operandTwo) + " = " + String.valueOf(result));
                break;
            }
        }
        previous.setText(String.valueOf(result));
        operandOne = result;
        input.setText("");
        return result;
    }

    //To Append Clicked Number's Text With Already Inputted Text
    public void numberPressed(View view)
    {
        Button pressedButton = (Button) view;
        input.setText(input.getText().toString() + pressedButton.getText().toString());
    }

    //To Clear Input And Memory
    public void clear(View view)
    {
        /*
            Refresh Variables,
            Some Presentation,
            And Set Operator To 0 (No Operator Selected).
        */
        operandOne = 0.0;
        operandTwo = 0.0;
        input.setText("");
        previous.setText("0");
        operator = 0;
    }

    //Panel Switching And Animation
    public void advancedPanel(View view)
    {
        //Check If AdvancedPanel Is Not Already Opened
        if(!advancedPanel)
        {
            //Scale Down Basic Panel To 0.0f (Invisible)
            ObjectAnimator scaler = ObjectAnimator.ofFloat(basicPane, "scaleY", 0.0f);
            scaler.setDuration(200);
            scaler.start();

            //Listener For Operations After Animation Is Completed
            scaler.addListener(new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animator) {}
                @Override
                public void onAnimationEnd(Animator animator)
                {
                    //Making Basic Panel Non-Interactive By Setting Height To 0
                    basicPane.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                }
                @Override
                public void onAnimationCancel(Animator animator) {}
                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
            advancedPanel = true;
            adButton.setText(getResources().getString(R.string.basic));
        }
        else
        {
            //Reverse If Advanced Panel Is Opened
            ObjectAnimator scaler = ObjectAnimator.ofFloat(basicPane, "scaleY", 1.0f);
            scaler.setDuration(200);
            basicPane.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            scaler.start();
            advancedPanel = false;
            adButton.setText(getResources().getString(R.string.advanced));
        }
    }

    //Switch To History Activity
    public void history(View view)
    {
        Intent openHistory = new Intent(this, History.class);
        startActivity(openHistory);
    }

    //Add Current Calculation To History
    private void addToHistory(String data)
    {
        History.historyStore.add(data);
    }

    public void plot(View view)
    {
        Intent i = new Intent(this, EquationMaker.class);
        startActivity(i);
    }
}
