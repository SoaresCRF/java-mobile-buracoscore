package com.dev.soarescrf.buracoscore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dev.soarescrf.buracoscore.R;
import com.dev.soarescrf.buracoscore.utils.ManagerCartaUtils;

/**
 * Activity responsável por adicionar pontos ao jogador na partida de Buraco.
 */
public class AddPointsActivity extends AppCompatActivity {

    // Views
    private SwitchCompat switchMorto, switchBatida;
    private Button buttonAction;
    private TextView textPlayer, textAction;

    private TextView[] textCartas, textCanastras;
    private ImageView[] imageAddCartas, imageRemoveCartas;

    // Constantes de pontuação
    private static final int PTS_5 = 5;
    private static final int PTS_10 = 10;
    private static final int PTS_15 = 15;
    private static final int PTS_20 = 20;
    private static final int PTS_CANASTRA_SUJA = 100;
    private static final int PTS_CANASTRA_LIMPA = 200;
    private static final int PTS_CANASTRA_REAL = 500;
    private static final int PTS_CANASTRA_AS = 1000;
    private static final int PTS_BATIDA = 100;
    private static final int PTS_MORTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculate_score);
        applyEdgeToEdge();

        initViews();
        setupUITexts();
        setupListeners();
    }

    /**
     * Ajusta a Activity para o modo Edge-to-Edge.
     */
    private void applyEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa todas as views da interface.
     */
    private void initViews() {
        switchMorto = findViewById(R.id.switchMorto);
        switchBatida = findViewById(R.id.switchBatida);
        textPlayer = findViewById(R.id.textPlayer);
        textAction = findViewById(R.id.textAction);
        buttonAction = findViewById(R.id.buttonAction);

        initCartasViews();
        initCanastrasViews();
        initImages();
    }

    private void initCartasViews() {
        textCartas = new TextView[]{
                findViewById(R.id.textQtdCarta5points),
                findViewById(R.id.textQtdCarta10points),
                findViewById(R.id.textQtdCarta15points),
                findViewById(R.id.textQtdCarta20points)
        };
    }

    private void initCanastrasViews() {
        textCanastras = new TextView[]{
                findViewById(R.id.textQtdCanastraSuja),
                findViewById(R.id.textQtdCanastraLimpa),
                findViewById(R.id.textQtdCanastraReal),
                findViewById(R.id.textQtdCanastraAs)
        };
    }

    private void initImages() {
        imageAddCartas = new ImageView[]{
                findViewById(R.id.imageAddCarta5points),
                findViewById(R.id.imageAddCarta10points),
                findViewById(R.id.imageAddCarta15points),
                findViewById(R.id.imageAddCarta20points),
                findViewById(R.id.imageAddCanastraSuja),
                findViewById(R.id.imageAddCanastraLimpa),
                findViewById(R.id.imageAddCanastraReal),
                findViewById(R.id.imageAddCanastraAs)
        };

        imageRemoveCartas = new ImageView[]{
                findViewById(R.id.imageRemoveCarta5points),
                findViewById(R.id.imageRemoveCarta10points),
                findViewById(R.id.imageRemoveCarta15points),
                findViewById(R.id.imageRemoveCarta20points),
                findViewById(R.id.imageRemoveCanastraSuja),
                findViewById(R.id.imageRemoveCanastraLimpa),
                findViewById(R.id.imageRemoveCanastraReal),
                findViewById(R.id.imageRemoveCanastraAs)
        };
    }

    /**
     * Configura textos estáticos da tela, como título e nome do jogador.
     */
    private void setupUITexts() {
        textAction.setText("ADICIONAR PONTOS");
        textAction.setTextColor(ContextCompat.getColor(this, R.color.text_action_add));
        buttonAction.setText("PRÓXIMO");

        String playerName = getIntent().getStringExtra("player");
        textPlayer.setText(playerName);
    }

    /**
     * Configura os listeners dos botões, switches e imagens.
     */
    private void setupListeners() {
        findViewById(R.id.imageInfo).setOnClickListener(v -> openScoreInfo());

        setupCartasListeners();
        setupButtonActionListener();
    }

    private void setupCartasListeners() {
        int[] maxValues = getMaxValuesForCards();

        for (int i = 0; i < imageAddCartas.length; i++) {
            TextView targetTextView = getCorrespondingTextView(i);
            int max = maxValues[i];

            ManagerCartaUtils.imageAddCartaListener(targetTextView, imageAddCartas[i], max);
            ManagerCartaUtils.imageRemoveCartaListener(targetTextView, imageRemoveCartas[i]);
        }
    }

    private int[] getMaxValuesForCards() {
        return new int[]{
                40, // Carta 5 pontos
                48, // Carta 10 pontos
                8,  // Carta 15 pontos
                12, // Carta 20 pontos
                48, // Canastra Suja
                48, // Canastra Limpa
                8,  // Canastra Real
                8   // Canastra Ás
        };
    }

    private TextView getCorrespondingTextView(int index) {
        if (index < textCartas.length) {
            return textCartas[index];
        } else {
            return textCanastras[index - textCartas.length];
        }
    }

    private void setupButtonActionListener() {
        buttonAction.setOnClickListener(v -> {
            Intent intent = new Intent(this, RemovePointsActivity.class);
            intent.putExtra("totalAddPoints", String.valueOf(calculateAddPoints()));
            intent.putExtra("player", textPlayer.getText().toString());
            startActivity(intent);
        });
    }

    /**
     * Calcula o total de pontos adicionados com base nas seleções do usuário.
     *
     * @return Total de pontos a adicionar.
     */
    private int calculateAddPoints() {
        int totalPoints = 0;

        totalPoints += getIntFromTextView(textCartas[0]) * PTS_5;
        totalPoints += getIntFromTextView(textCartas[1]) * PTS_10;
        totalPoints += getIntFromTextView(textCartas[2]) * PTS_15;
        totalPoints += getIntFromTextView(textCartas[3]) * PTS_20;

        totalPoints += getIntFromTextView(textCanastras[0]) * PTS_CANASTRA_SUJA;
        totalPoints += getIntFromTextView(textCanastras[1]) * PTS_CANASTRA_LIMPA;
        totalPoints += getIntFromTextView(textCanastras[2]) * PTS_CANASTRA_REAL;
        totalPoints += getIntFromTextView(textCanastras[3]) * PTS_CANASTRA_AS;

        if (switchBatida.isChecked()) totalPoints += PTS_BATIDA;
        if (switchMorto.isChecked()) totalPoints += PTS_MORTO;

        return totalPoints;
    }

    /**
     * Converte o valor textual de um TextView para inteiro.
     *
     * @param textView TextView contendo o número.
     * @return Valor inteiro ou zero se vazio.
     */
    private int getIntFromTextView(TextView textView) {
        String text = textView.getText().toString().trim();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    /**
     * Abre a tela com informações sobre a pontuação.
     */
    private void openScoreInfo() {
        startActivity(new Intent(this, ScoreInfoActivity.class));
    }
}
