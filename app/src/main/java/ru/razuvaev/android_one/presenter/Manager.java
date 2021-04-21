package ru.razuvaev.android_one.presenter;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.razuvaev.android_one.MainContract;
import ru.razuvaev.android_one.model.ReversePolishNotation;

public class Manager implements MainContract.Presenter {
    private static final String TAG = "Manager";

    private final MainContract.ViewText mView;

    private String message;
    private String preMessage;
    private boolean mathOperationFlag = false;
    private boolean pointFlag = false;

    public Manager(MainContract.ViewText mView, MainContract.Model calculatorRPN) {
        this.mView = mView;
        Log.d(TAG, "Constructor");
    }

    @Override
    public void numberBtnClick(String stringResult, String btnNumber) {
        if (stringResult.equals("0") & btnNumber.equals("0")) {
            return;
        }
        if (stringResult.equals("0")) {
            stringResult = "";
        }
        mathOperationFlag = false;
        message = stringResult + btnNumber;
        preMessage = ReversePolishNotation.calculateExpression(message);
        mView.ShowText(message,preMessage);
    }
    @Override
    public void pointBtnClick(String stringResult) {
        if (pointFlag) {
            return;
        }
        mathOperationFlag = false;
        pointFlag = true;
        message = stringResult + ".";
        preMessage = ReversePolishNotation.calculateExpression(message);
        mView.ShowText(message,preMessage);
    }
    @Override
    public void operationClick(String stringResult, String operation) {
        if (mathOperationFlag) {
            stringResult = stringResult.substring(0, stringResult.length() - 1);
        }
        mathOperationFlag = true;
        pointFlag = false;
        message = stringResult + operation;
        preMessage = ReversePolishNotation.calculateExpression(message);
        mView.ShowText(message,preMessage);
    }
    @Override
    public void onResultClick(String stringResult) {
        message = ReversePolishNotation.calculateExpression(stringResult);
        if (message.isEmpty() | message.equals("Деление на 0")) {return;}
        preMessage = "";
        pointFlag = false;
        mView.ShowText(message,preMessage);
    }
    @Override
    public void cancelClick() {
        mathOperationFlag = false;
        pointFlag = false;
        mView.ShowText("0","");
    }
    @Override
    public void backSpaceClick(String stringResult) {
        final String actionsList = "/*+-";
        int lengthText = stringResult.length() - 1;
        if (lengthText == 0) {
            mView.ShowText("0","");
            return;
        }
        mathOperationFlag = actionsList.contains(stringResult.substring(lengthText - 1, lengthText));
        message = stringResult.substring(0, lengthText);
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
        preMessage = ReversePolishNotation.calculateExpression(message);
        mView.ShowText(message,preMessage);
    }
}
