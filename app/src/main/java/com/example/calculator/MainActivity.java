package com.example.calculator;

import static android.content.ContentValues.TAG;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    String firstNum, secondNum,operation, equation;
    boolean equalsClicked,displayInitialState,isresult, operatorClicked, btnNumIsClicked, isError, percentBtnClicked;
    int numOfOperands,clickedmultipletimes;

    Stack<String> initialOperands;
    Stack<String> initialOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        equation="";
        equalsClicked = false;
        numOfOperands = 0;
        initialOperands = new Stack<>();
        initialOperator = new Stack<>();
        operatorClicked = false;
        clickedmultipletimes = 0;
        isresult = false;
        percentBtnClicked = false;
        displayInitialState = false;

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
        TextView currentEquation =  (TextView) findViewById(R.id.currentEq);

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
                    btnNumIsClicked =true;
                    operatorClicked=false;
                    if(displayInitialState){
                        equalsClicked = false;
                        displayInitialState = false;
                        result.setText(num.getText().toString());
                    } else if (!result.getText().toString().equals("0") && equalsClicked==false && !result.getText().toString().equals("Error")) {
                        result.setText(result.getText().toString() + num.getText().toString());
                    } else {
                        equalsClicked = false;
                        result.setText(num.getText().toString());
                    }
                    ac.setText("C");

                    percentBtnClicked = false;
                }
            });
        }




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isError){
                    result.setText("Error");
                    return;
                }
                String temp  = result.getText().toString();
                if(temp.length()>1){
                    temp = temp.substring(0,temp.length()-1);
                    result.setText(temp);
                }else{
                    result.setText("0");
                    ac.setText("AC");
                }
                equalsClicked = false;
                btnNumIsClicked = false;
                operatorClicked=false;

            }
        });

        period.setOnClickListener(view->{
            if(isError){
                result.setText("Error");
                return;
            }
            String temp = result.getText().toString();

            if(!temp.contains(".")){
                if(temp.equals(""))temp="0";
                if(temp.equals("-"))temp="-0";
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
                if(isError){
                    result.setText("Error");
                    return;
                }
                if(!result.getText().toString().equals("0")){
//                    String temp = result.getText().toString();
//                    Double num = Double.parseDouble(temp);
//                    num*=-1;
//                    temp = String.valueOf(num);
//                    result.setText(temp);
                    if(result.getText().toString().contains("-")){
                        String temp = result.getText().toString();
                        result.setText(temp.substring(1,temp.length()));

                    }else{
                        String temp = result.getText().toString();
                        result.setText("-"+temp);
                    }
                    equalsClicked=false;
                }
                btnNumIsClicked=false;
                operatorClicked=false;

            }
        });

        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isError){
                    result.setText("Error");
                    return;
                }
                percentBtnClicked = percentBtnClicked?false:true;

                if(!result.getText().toString().contains("%")&&percentBtnClicked){
                    String temp = result.getText().toString();
                    Double per = Double.parseDouble(temp)/100;
                    result.setText(per.toString());
                }else if(!percentBtnClicked) {
                    String temp = result.getText().toString();
                    Double per = Double.parseDouble(temp)*100;
                    result.setText(per.toString());
                }
                equalsClicked = false;
                btnNumIsClicked=false;
                operatorClicked=false;
            }
        });

        ac.setOnClickListener(view->{
            firstNum = "";
            secondNum="";
            operation=null;
            result.setText("");
            ac.setText("AC");
            equation="";
            equalsClicked=false;
            numOfOperands = 0;
            isresult = false;
            clickedmultipletimes = 0;
            operatorClicked=false;
            btnNumIsClicked = false;
            isError=false;

            currentEquation.setText(equation);

            while(!initialOperator.isEmpty()){
                initialOperator.pop();
            }

            while (!initialOperands.isEmpty()){
                initialOperands.pop();
            }
        });


        for (Button b : btnOp) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isError){
                        result.setText("Error");
                        return;
                    }
                    String currentOperation = b.getText().toString();

                    String num = result.getText().toString();
                    if(num.equals("Infinity") || num.equals("NaN") || num.equals("Error")){
                        result.setText("Error");
                        return;
                    }
                    if (equalsClicked) {
                        equation = (num+" "+currentOperation + " ");

                        while (!initialOperands.isEmpty()) {
                            initialOperands.pop();
                        }

                        initialOperands.push(num);
                        numOfOperands=1;

                        while (!initialOperator.isEmpty()) {
                            initialOperator.pop();
                        }
                        initialOperator.push(currentOperation);
                        currentEquation.setText(equation);
                        equalsClicked=false;
                        result.setText("");
                        return;
                    }



                    if ((operatorClicked || isresult || num.equals(""))  && equation.length() > 1 && isOperator(equation.substring(equation.length() - 2).charAt(0))) {
                        equation = equation.substring(0, equation.length() - 2) + currentOperation + " ";
                        while (!initialOperator.isEmpty()) {
                            initialOperator.pop();
                        }
                        initialOperator.push(currentOperation);
                        currentEquation.setText(equation);
                        clickedmultipletimes = 0;
                    } else if (!num.equals("")) {
                        operatorClicked = true;
                        result.setText("");
                        initialOperands.push(num);
                        initialOperator.push(currentOperation);
                        numOfOperands++;
                        equation += (num + " " + currentOperation + " ");
                        isresult=false;

                    } else {
                        result.setText("");
                        displayInitialState = false;
                        isresult=false;

                    }



                    if (numOfOperands == 2) {
                        secondNum = initialOperands.pop();
                        firstNum = initialOperands.pop();

                        isresult = true;

                        String op = null;
                        while (!initialOperator.isEmpty()) {
                            op = initialOperator.pop();
                        }
                        initialOperator.push(currentOperation); // Fix: Push the last operator clicked instead of the current operation


                        try {
                            firstNum = String.valueOf(Calculator.evaluateEquation(firstNum + " " + op + " " + secondNum));
                        }catch (Exception e){
                            Log.d(TAG, "onClick: "+e.getMessage());
                        }

                        if(firstNum.equals("Infinity")||firstNum.equals("NaN")){
                            result.setText("Error");
                            isError = true;
                            currentEquation.setText("");
                            return;
                        }

                        initialOperands.push(firstNum);

                        result.setText(firstNum);
                        numOfOperands = 1;
                        displayInitialState = true;
                    }
                    isresult = false;

                    btnNumIsClicked = false;
                    operatorClicked=true;
                    currentEquation.setText(equation);
                    clickedmultipletimes = 0;
                    percentBtnClicked = false;

                }
            });
        }




        equals.setOnClickListener(view->{
            if(isError){
                result.setText("Error");
                currentEquation.setText("");
                return;
            }

            String num = result.getText().toString();
            if(num.equals("Infinity") || num.equals("NaN")){
                result.setText("Error");
                currentEquation.setText("");
                return;
            }
            equalsClicked = true;
            clickedmultipletimes++;
            if(clickedmultipletimes>=1){
                if(!operatorClicked){

                    if(!num.isEmpty())equation+=(num+" ");
                }else{
                    String current = currentEquation.getText().toString();
                    equation = current.substring(0,current.length()-2);
                }


                isresult = false;
                currentEquation.setText(equation);



                String ans = String.valueOf(Calculator.evaluateEquation(equation));

                if(ans.equals("Infinity") || ans.equals("NaN")){
                    ans="Error";
                    isError = true;
                    currentEquation.setText("");
                }
                result.setText(ans);






            }else{
                //do nothing
            }

            percentBtnClicked = false;


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

    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            System.out.println("landscape");
            setContentView(R.layout.activity_main);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Do something specific for portrait orientation

            setContentView(R.layout.activity_main);
        }
    }



}