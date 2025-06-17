package com.dev.soarescrf.buracoscore.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dev.soarescrf.buracoscore.R;
import com.dev.soarescrf.buracoscore.utils.TextUtils;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rules);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setJustifiedText();
    }

    private void setJustifiedText() {
        TextView[] textViews = new TextView[]{
                findViewById(R.id.textView1),
                findViewById(R.id.textView2),
                findViewById(R.id.textView3),
                findViewById(R.id.textView4),
                findViewById(R.id.textView5),
                findViewById(R.id.textView6),
                findViewById(R.id.textView7),
                findViewById(R.id.textView8),
                findViewById(R.id.textView10),
                findViewById(R.id.textView11),
                findViewById(R.id.textView12),
                findViewById(R.id.textView13),
                findViewById(R.id.textView14),
                findViewById(R.id.textView15),
                findViewById(R.id.textView16),
                findViewById(R.id.textView17),
                findViewById(R.id.textView18),
                findViewById(R.id.textView19),
                findViewById(R.id.textView20),
                findViewById(R.id.textView21),
                findViewById(R.id.textView22),
                findViewById(R.id.textView23),
                findViewById(R.id.textView24),
                findViewById(R.id.textView25),
                findViewById(R.id.textView26),
                findViewById(R.id.textView27),
                findViewById(R.id.textView28),
                findViewById(R.id.textView29),
                findViewById(R.id.textView30),
        };

        for (TextView textView : textViews) {
            TextUtils.configureJustifiedText(textView);
        }
    }
}