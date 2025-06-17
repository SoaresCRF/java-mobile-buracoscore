package com.dev.soarescrf.buracoscore.utils;

import android.os.Build;
import android.text.Layout;
import android.widget.TextView;

public class TextUtils {
    public static void configureJustifiedText(TextView textView) {
        // Verifica se a versão do Android é 8.0 (API 26) ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
    }
}
