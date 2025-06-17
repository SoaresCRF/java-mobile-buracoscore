package com.dev.soarescrf.buracoscore.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayerNamePreferenceManager {
    private static final String PREF_NAME = "PlayerNamePreference";
    private static final String KEY_PLAYER_NAME_A = "playerNameA";
    private static final String KEY_PLAYER_NAME_B = "playerNameB";

    private SharedPreferences sharedPreferences;

    public PlayerNamePreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Salva nome do player A
    public void savePlayerNameA(String playerNameA) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PLAYER_NAME_A, playerNameA);
        editor.apply();
    }

    // Salva nome do player B
    public void savePlayerNameB(String playerNameB) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PLAYER_NAME_B, playerNameB);
        editor.apply();
    }


    // Retorna o primeiro nome
    public String getPlayerNameA() {
        return sharedPreferences.getString(KEY_PLAYER_NAME_A, "JOGADOR A");
    }

    // Retorna o segundo nome
    public String getPlayerNameB() {
        return sharedPreferences.getString(KEY_PLAYER_NAME_B, "JOGADOR B");
    }
}
