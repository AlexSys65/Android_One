package ru.razuvaev.android_one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultText, preResultText;
    private boolean mathOperationFlag = false;
    private boolean pointFlag = false;


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
        String stringResult = resultText.getText().toString();
        if (stringResult.equals("0") & button.getText().toString().equals("0")) {
            return;
        }
        if (stringResult.equals("0")) {
            stringResult = "";
        }
        stringResult = stringResult + button.getText().toString();
        resultText.setText(stringResult);
        mathOperationFlag = false;
        preResultText.setText(ReversePolishNotation.transformationString(stringResult));
    }

    public void pointBtnClick(View v) {
        String stringResult = resultText.getText().toString();
        if (pointFlag) {
            return;
        }
        stringResult = stringResult + ".";
        resultText.setText((stringResult));
        mathOperationFlag = false;
        pointFlag = true;
        preResultText.setText(ReversePolishNotation.transformationString(stringResult));
    }

    public void operationClick(View v) {
        Button button = (Button) v;
        String stringResult = resultText.getText().toString();
        if (mathOperationFlag) {
            stringResult = stringResult.substring(0, stringResult.length() - 1);
        }
        resultText.setText((stringResult + button.getText().toString()));
        mathOperationFlag = true;
        pointFlag = false;
    }

    public void onResultClick(View v) {
        String arithmeticExpr = ReversePolishNotation.transformationString(resultText.getText().toString());
        if (arithmeticExpr.isEmpty()) {
            return;
        }
        preResultText.setText("");
        resultText.setText(arithmeticExpr);
        pointFlag = false;
    }

    public void cancelClick(View v) {
        resultText.setText("0");
        preResultText.setText("");
        mathOperationFlag = false;
    }

    public void backSpaceClick(View v) {
        final String actionsList = "/*+-";
        String stringResult = resultText.getText().toString();
        int lengthText = stringResult.length() - 1;
        if (lengthText == 0) {
            resultText.setText("0");
            preResultText.setText("");
            return;
        }
        mathOperationFlag = actionsList.contains(stringResult.substring(lengthText - 1, lengthText));
        stringResult = stringResult.substring(0, lengthText);
        lengthText--;
        while (!actionsList.contains(Character.toString(stringResult.charAt(lengthText)))) {
            if (Character.toString(stringResult.charAt(lengthText)).equals(".")) {
                pointFlag = true;
                break;
            }
            pointFlag = false;
            lengthText--;
            if (lengthText < 0) {
                break;
            }
        }
        resultText.setText(stringResult);
        preResultText.setText(ReversePolishNotation.transformationString(stringResult));
    }
}