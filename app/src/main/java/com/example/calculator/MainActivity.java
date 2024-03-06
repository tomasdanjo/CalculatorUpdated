package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    double firstNum, secondNum;;

    String operation;
    String equation;
    boolean equalsClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        equation="";
        equalsClicked = false;


        Button num0 = (Button) findViewById(R.id.num0);
        Button num1 = (Button) findViewById(R.id.num1);
        Button num2 = (Button) findViewById(R.id.num2);
        Button num3 = (Button) findViewById(R.id.num3);
        Button num4 = (Button) findViewById(R.id.num4);
        Button num5 = (Button) findViewById(R.id.num5);
        Button num6 = (Button) findViewById(R.id.num6);
        Button num7 = (Button) findViewById(R.id.num7);
        Button num8 = (Button) findViewById(R.id.num8);
        Button num9 = (Button) findViewById(R.id.num9);

        Button ac = (Button) findViewById(R.id.ac);
        Button flipSign = (Button) findViewById(R.id.flipSign);
        Button percent = (Button) findViewById(R.id.percent);

        Button plus = (Button) findViewById(R.id.plus);
        Button divide = (Button) findViewById(R.id.divide);
        Button multiply = (Button) findViewById(R.id.multiply);
        Button minus = (Button) findViewById(R.id.minus);
        Button equals = (Button) findViewById(R.id.equals);
        Button period = (Button) findViewById(R.id.period);

        Button delete = (Button) findViewById(R.id.backspace);

        TextView result = (TextView) findViewById(R.id.result);

        ac.setOnClickListener(view->{
            firstNum = 0;
            operation=null;
            result.setText("0");
            ac.setText("AC");
            equation="";
            equalsClicked=false;
        });

        ArrayList<Button> nums = new ArrayList<>();
        nums.add(num0);
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);



        for(Button num:nums){
            num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!result.getText().toString().equals("0") && equalsClicked==false) {
                        result.setText(result.getText().toString() + num.getText().toString());

                    } else {
                        equalsClicked = false;
                        result.setText(num.getText().toString());
                    }
                    ac.setText("C");
                }
            });
        }



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp  = result.getText().toString();
                if(temp.length()>1){
                    temp = temp.substring(0,temp.length()-1);
                    result.setText(temp);
                }else{
                    result.setText("0");
                    ac.setText("AC");
                }
                equalsClicked = false;


            }
        });

        period.setOnClickListener(view->{
            String temp = result.getText().toString();
            if(!temp.contains(".")){
                result.setText(temp+period.getText().toString());
            }else if(temp.substring(temp.length()-1).contains(".")){
                temp = temp.substring(0,temp.length()-1);
                result.setText(temp);
            }
        });

        ArrayList<Button> btnOp = new ArrayList<>();
        btnOp.add(plus);
        btnOp.add(minus);
        btnOp.add(multiply);
        btnOp.add(divide);

        flipSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!result.getText().toString().equals("0")){
                    if(result.getText().toString().contains("-")){
                        String temp = result.getText().toString();
                        result.setText(temp.substring(1,temp.length()));

                    }else{
                        result.setText("-"+result.getText().toString());
                    }
                    equalsClicked=false;
                }
            }
        });

        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!result.getText().toString().contains("%")){
                    String temp = result.getText().toString();
                    double per = Double.parseDouble(temp)/100;
                    result.setText(String.valueOf(per));
                    equalsClicked = false;

                }
            }
        });


        for (Button b : btnOp) {
            b.setOnClickListener(view -> {
                String currentOperation = b.getText().toString();
                String num = result.getText().toString();

                if (num.isEmpty()) {
                    // If the result is empty, replace the last two characters in equation with the current operation
                    if (!equation.isEmpty() && isOperator(equation.charAt(equation.length() - 1))) {
                        equation = equation.substring(0, equation.length() - 2) + currentOperation + " ";
                    } else {
                        equation += currentOperation + " ";
                    }
                } else {
                    equation += num + " " + currentOperation + " ";
                }

                result.setText("");
            });
        }




        equals.setOnClickListener(view->{
            equalsClicked = true;
            String num = result.getText().toString();
            if(!num.isEmpty())equation+=num;
            String ans = String.valueOf(Calculator.evaluateEquation(equation));
            result.setText(ans);
            equation="";


        });



    }

    boolean isOperator(char c){
        switch (c){
            case '+':
            case '-':
            case 'x':
            case '/':
                return true;
            default:
                return false;
        }
    }



}