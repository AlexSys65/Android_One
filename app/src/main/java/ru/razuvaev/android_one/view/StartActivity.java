package ru.razuvaev.android_one.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.switchmaterial.SwitchMaterial;
import ru.razuvaev.android_one.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SwitchMaterial theme_switch = findViewById(R.id.switch_theme);
        findViewById(R.id.button_start).setOnClickListener(v -> {
            Intent myIntent = new Intent(this, MainActivity.class);
            Boolean darkTheme = theme_switch.isChecked();
            myIntent.putExtra("Theme", darkTheme );
            ActivityInfo activityInfo = myIntent.resolveActivityInfo(getPackageManager(), myIntent.getFlags());
            if (activityInfo != null){
                startActivity(myIntent);
            }
        });
    }
}