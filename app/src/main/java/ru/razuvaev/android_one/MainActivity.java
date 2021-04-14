package ru.razuvaev.android_one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView resultText, preResultText;
    private String actionsList = "/*+-";
    boolean mathOperationFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView) findViewById(R.id.resultText);
        preResultText = (TextView) findViewById(R.id.preText);
        resultText.setText("0");
        preResultText.setText("");
    }

    public void numberBtnClick(View v) {
        Button button = (Button) v;
        if (resultText.getText().toString().equals("0") & button.getText().toString().equals("0")) {
            return;
        }
        if (resultText.getText().toString().equals("0")) {
            resultText.setText("");
        }
        resultText.setText((resultText.getText().toString() + button.getText().toString()));
        mathOperationFlag = false;
        preResultText.setText(ReversePolishNotation.transformationString(resultText.getText().toString()));
    }

    public void pointBtnClick(View v) {
        if (resultText.getText().toString().contains(".")) {
            return;
        }
        resultText.setText((resultText.getText().toString() + "."));
        mathOperationFlag = false;
        preResultText.setText(ReversePolishNotation.transformationString(resultText.getText().toString()));
    }

    public void operationClick(View v) {
        Button button = (Button) v;
        String action = button.getText().toString();
        if (!mathOperationFlag) {
            resultText.setText((resultText.getText().toString() + action));
        } else {
            resultText.setText(resultText.getText().toString().substring(0, resultText.getText().toString().length() - 1));
            resultText.setText((resultText.getText().toString() + action));
        }
        mathOperationFlag = true;
    }

    public void onResultClick(View v) {
       /* if (calculateList.size() < 3) {return;}
        preResultText.setText("");
        resultText.setText(ReversePolishNotation.transformationString(calculateList));*/
    }

    public void cancelClick(View v) {
        resultText.setText("0");
        preResultText.setText("");
        mathOperationFlag = false;
    }

    public void backSpaceClick(View v) {
        int lengthText = resultText.getText().toString().length() - 1;
        if (resultText.getText().toString().length() == 1) {
            resultText.setText("0");
            preResultText.setText("");
            return;
        }

        mathOperationFlag = actionsList.contains(resultText.getText().toString().substring(lengthText - 1, lengthText));

       /* if (actionsList.contains(resultText.getText().toString().substring(lengthText - 1, lengthText))) {
            mathOperationFlag = true;
        } else {
            mathOperationFlag = false;
        }*/
        resultText.setText(resultText.getText().toString().substring(0, lengthText));
        preResultText.setText(ReversePolishNotation.transformationString(resultText.getText().toString()));

    }
}