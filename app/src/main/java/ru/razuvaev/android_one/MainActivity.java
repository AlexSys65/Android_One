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
    private String partNumber = "0";
    private List<Object> calculateList = new ArrayList<>();
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
        if (resultText.getText().toString().equals("0") & button.getText().toString().equals("0")){return;}
        if (resultText.getText().toString().equals("0")) {resultText.setText("");}
        resultText.setText((resultText.getText().toString() + button.getText().toString()));
        partNumber = partNumber + button.getText().toString();
        mathOperationFlag = false;
        if (calculateList.size() < 3) {
            preResultText.setText(resultMethod(false, partNumber));
        }
    }
    public void pointBtnClick(View v){
        if (resultText.getText().toString().contains(".")) {return;}
        resultText.setText((resultText.getText().toString() + "."));
        partNumber = partNumber + ".";
        mathOperationFlag = false;
    }
    public void operationClick(View v){
        Button button = (Button) v;
        String action = button.getText().toString();

        if (!mathOperationFlag){

            resultText.setText((resultText.getText().toString() + action));
        } else {
            resultText.setText(resultText.getText().toString().substring(0, resultText.getText().toString().length() - 1));

            resultText.setText((resultText.getText().toString() + action));
        }
        mathOperationFlag = true;
    }
    public void onResultClick(View v){
        if (calculateList.size() < 3) {return;}
        preResultText.setText("");
        resultText.setText(ReversePolishNotation.transformationString(calculateList));
    }
    public void cancelClick(View v){
        resultText.setText("0");
        partNumber = "0";
        calculateList.clear();
        preResultText.setText("");
        mathOperationFlag = false;
    }
    public void backSpaceClick(View v) {
        int lengthText = resultText.getText().toString().length() - 1;
        if (resultText.getText().toString().length() == 1) {
            resultText.setText("0");
            partNumber = "0";
            preResultText.setText("");
            return;
        }
        if (actionsList.contains(resultText.getText().toString().substring(lengthText - 1, lengthText))) {
            mathOperationFlag = true;
        } else {
            mathOperationFlag = false;
        }
        resultText.setText(resultText.getText().toString().substring(0, lengthText));
        partNumber = resultText.getText().toString();
    }
    private String resultMethod(boolean flag, String partB){
        Double number = null;
        try {
            number = Double.parseDouble(partB);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (calculateList.size() < 1 & flag) {
            calculateList.add(number);
            partNumber = "";
            return partB;
        }
        if (actionsList.contains(calculateList.get(calculateList.size() - 1).toString())) {
            calculateList.add(number);
        } else {
            calculateList.set(calculateList.size() - 1, number);
        }
        if (flag) {
            partNumber = "";
        }
        return ReversePolishNotation.transformationString(calculateList);
    }
}