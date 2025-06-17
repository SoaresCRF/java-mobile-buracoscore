package com.dev.soarescrf.buracoscore.utils;

import android.widget.ImageView;
import android.widget.TextView;

public class ManagerCartaUtils {
    public static void imageRemoveCartaListener(TextView textView, ImageView imageView) {
        imageView.setOnClickListener(v -> {
            int currentValue = parseTextToInt(textView.getText().toString());
            if (currentValue > 0) {
                textView.setText(String.valueOf(currentValue - 1));
            }
        });
    }

    public static void imageAddCartaListener(TextView textView, ImageView imageView, int max) {
        imageView.setOnClickListener(v -> {
            int currentValue = parseTextToInt(textView.getText().toString());
            if (currentValue < max) {
                textView.setText(String.valueOf(currentValue + 1));
            }
        });
    }

    private static int parseTextToInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
