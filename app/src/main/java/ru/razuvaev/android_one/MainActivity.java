package ru.razuvaev.android_one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnListener(View v) {
        Button btn = (Button) v;
        switch (btn.getText().toString()) {
            case ("Push me") : {
                Toast.makeText(this, "Push me", Toast.LENGTH_LONG).show();
                break;
            }
            case ("Open second activity") : {
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            }
            default: {Toast.makeText(this, "Push me again", Toast.LENGTH_LONG).show();}
        }
    }

    public void btnToggleListener(View v) {

        boolean on = ((ToggleButton) v).isChecked();
        if (on) {
            Toast.makeText(this, "light on!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "light off!", Toast.LENGTH_LONG).show();
        }
    }
}