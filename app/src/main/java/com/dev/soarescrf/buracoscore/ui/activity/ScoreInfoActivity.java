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

public class ScoreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_info);
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
                findViewById(R.id.textView9),
        };

        for (TextView textView : textViews) {
            TextUtils.configureJustifiedText(textView);
        }
    }
}