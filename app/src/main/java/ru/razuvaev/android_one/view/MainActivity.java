package ru.razuvaev.android_one.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.razuvaev.android_one.MainContract;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.model.ReversePolishNotation;

public class MainActivity extends AppCompatActivity {
    private TextView resultText, preResultText;
    private boolean mathOperationFlag = false;
    private boolean pointFlag = false;

    private static final String KEY_RESULT = "RESULT";
    private static final String KEY_PRE_RESULT = "PRE_RESULT";
    private static final String KEY_MATH_FLAG = "MATH_FLAG";
    private static final String KEY_POINT_FLAG = "POINT_FLAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.resultText);
        preResultText = findViewById(R.id.preText);
        if (savedInstanceState != null) {
            resultText.setText(savedInstanceState.getString(KEY_RESULT));
            preResultText.setText(savedInstanceState.getString(KEY_PRE_RESULT));
            mathOperationFlag = savedInstanceState.getBoolean(KEY_MATH_FLAG);
            pointFlag = savedInstanceState.getBoolean(KEY_POINT_FLAG);
        } else {
            resultText.setText("0");
            preResultText.setText("");
        }
        //findViewById(R.id.button_0).setOnClickListener();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putString(KEY_RESULT, resultText.getText().toString());
        instanceState.putString(KEY_PRE_RESULT, preResultText.getText().toString());
        instanceState.putBoolean(KEY_MATH_FLAG, mathOperationFlag);
        instanceState.putBoolean(KEY_POINT_FLAG, pointFlag);
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