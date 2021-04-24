package ru.razuvaev.android_one.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ru.razuvaev.android_one.MainContract;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.presenter.Manager;

public class MainActivity extends AppCompatActivity implements MainContract.ViewText {

    private Manager manager;
    private TextView resultText, preResultText;
    private static final String KEY_RESULT = "RESULT";
    private static final String KEY_PRE_RESULT = "PRE_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        Boolean darkTheme = arguments.getBoolean("Theme");
        if (darkTheme) {
            setTheme(R.style.Theme_Android_Two);
        } else {
            setTheme(R.style.Theme_Android_One);
        }
        setContentView(R.layout.activity_main);
        manager = new Manager(this);
        resultText = findViewById(R.id.resultText);
        preResultText = findViewById(R.id.preText);
        if (savedInstanceState == null) {
            resultText.setText("0");
            preResultText.setText("");
        } else {
            resultText.setText(savedInstanceState.getString(KEY_RESULT));
            preResultText.setText(savedInstanceState.getString(KEY_PRE_RESULT));
        }
        findViewById(R.id.button_0).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "0");
        });
        findViewById(R.id.button_1).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "1");
        });
        findViewById(R.id.button_2).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "2");
        });
        findViewById(R.id.button_3).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "3");
        });
        findViewById(R.id.button_4).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "4");
        });
        findViewById(R.id.button_5).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "5");
        });
        findViewById(R.id.button_6).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "6");
        });
        findViewById(R.id.button_7).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "7");
        });
        findViewById(R.id.button_8).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "8");
        });
        findViewById(R.id.button_9).setOnClickListener(v -> {
            manager.numberBtnClick(resultText.getText().toString(), "9");
        });
        findViewById(R.id.button_point).setOnClickListener(v -> {
            manager.pointBtnClick(resultText.getText().toString());
        });

        findViewById(R.id.button_Minus).setOnClickListener(v -> {
            manager.operationClick(resultText.getText().toString(), "-");
        });
        findViewById(R.id.button_plus).setOnClickListener(v -> {
            manager.operationClick(resultText.getText().toString(), "+");
        });
        findViewById(R.id.button_MULT).setOnClickListener(v -> {
            manager.operationClick(resultText.getText().toString(), "*");
        });
        findViewById(R.id.button_DIV).setOnClickListener(v -> {
            manager.operationClick(resultText.getText().toString(), "/");
        });

        findViewById(R.id.button_RESULT).setOnClickListener(v -> {
            manager.onResultClick(resultText.getText().toString());
        });

        findViewById(R.id.button_CLEAR).setOnClickListener(v -> {
            manager.cancelClick();
        });

        findViewById(R.id.button_BS).setOnClickListener(v -> {
            manager.backSpaceClick(resultText.getText().toString());
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putString(KEY_RESULT, resultText.getText().toString());
        instanceState.putString(KEY_PRE_RESULT, preResultText.getText().toString());
    }

    @Override
    public void ShowText(String result, String preResult) {
        resultText.setText(result);
        preResultText.setText(preResult);
    }
}