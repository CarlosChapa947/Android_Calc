package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView calcBox, resultBox;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
    buttonDel, buttonMinus, buttonPlus, buttonMulti, buttonDiv, buttonLeftParenthese, buttonRightParenthese,
    buttonC, buttonDot, buttonEquals;


    void assignbuttonID(MaterialButton button, int id){
        button = findViewById(id);
        button.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        resultBox = findViewById(R.id.result_box);
        calcBox = findViewById(R.id.calcBox);
        assignbuttonID(button0, R.id.button_0);
        assignbuttonID(button1, R.id.button_1);
        assignbuttonID(button2, R.id.button_2);
        assignbuttonID(button3, R.id.button_3);
        assignbuttonID(button4, R.id.button_4);
        assignbuttonID(button5, R.id.button_5);
        assignbuttonID(button6, R.id.button_6);
        assignbuttonID(button7, R.id.button_7);
        assignbuttonID(button8, R.id.button_8);
        assignbuttonID(button9, R.id.button_9);
        assignbuttonID(buttonDel, R.id.button_del);
        assignbuttonID(buttonMinus, R.id.button_minus);
        assignbuttonID(buttonPlus, R.id.button_plus);
        assignbuttonID(buttonMulti, R.id.button_multi);
        assignbuttonID(buttonDiv, R.id.button_division);
        assignbuttonID(buttonLeftParenthese, R.id.button_leftParentheses);
        assignbuttonID(buttonRightParenthese, R.id.button_rightParentheses);
        assignbuttonID(buttonC, R.id.button_C);
        assignbuttonID(buttonDot, R.id.button_dot);
        assignbuttonID(buttonEquals, R.id.button_equal);

    }

    @Override
    public void onClick(View view) {
        MaterialButton materialButton = (MaterialButton) view;
        String buttonText = materialButton.getText().toString();
        String dataToCalculate = calcBox.getText().toString();

        if(buttonText.equals("C")){
            calcBox.setText("");
            resultBox.setText("");
            return;
        } else if (buttonText.equals("DEL")){
            if (!dataToCalculate.equals("")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            calcBox.setText(dataToCalculate);
            return;
            } else {
                return;
            }
        } else if (buttonText.equals("=")){
            if (dataToCalculate.equals("")){
                return;
            }
            Double result = calculate(dataToCalculate);
            resultBox.setText(String.valueOf(result));

            return;
        }
        dataToCalculate = dataToCalculate + buttonText;
        calcBox.setText(dataToCalculate);
    }

    public static Double calculate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
                StringBuilder sbuf = new StringBuilder();

                while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.'))
                    sbuf.append(tokens[i++]);
                i--;
                values.push(Double.parseDouble(sbuf.toString()));
            }
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            }
            else if (tokens[i] == '+' ||
                    tokens[i] == '-' ||
                    tokens[i] == '*' ||
                    tokens[i] == '/')
            {
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));

                ops.push(tokens[i]);
            }
        }
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
                (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static Double applyOp(char op,
                              Double b, Double a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException(
                            "Error");
                return a / b;
        }
        return 0.0;
    }
}
