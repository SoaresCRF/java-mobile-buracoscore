package com.dev.soarescrf.buracoscore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RemovePointsActivity extends AppCompatActivity {

    // UI Components
    private SwitchCompat switchMorto, switchBatida;
    private TextView textPlayer, textAction;
    private TextView textQtdCarta5points, textQtdCarta10points, textQtdCarta15points, textQtdCarta20points;
    private ImageView imageRemoveCarta5points, imageAddCarta5points;
    private ImageView imageRemoveCarta10points, imageAddCarta10points;
    private ImageView imageRemoveCarta15points, imageAddCarta15points;
    private ImageView imageRemoveCarta20points, imageAddCarta20points;
    private Button buttonAction;
    private LinearLayout linearLayoutCanastraLimpa, linearLayoutCanastraSuja, linearLayoutCanastraAs, linearLayoutCanastraReal;

    private String player;
    private int totalAddPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculate_score);

        setupWindowInsets();
        initializeViews();
        setupListeners();
        hideUnusedViews();
        configureUI();
        getIntentData();
        setupCalculateButton();
    }

    /**
     * Configura os insets de janela (status/nav bar)
     */
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa todas as views da tela
     */
    private void initializeViews() {
        textPlayer = findViewById(R.id.textPlayer);
        textAction = findViewById(R.id.textAction);
        textQtdCarta5points = findViewById(R.id.textQtdCarta5points);
        textQtdCarta10points = findViewById(R.id.textQtdCarta10points);
        textQtdCarta15points = findViewById(R.id.textQtdCarta15points);
        textQtdCarta20points = findViewById(R.id.textQtdCarta20points);

        switchMorto = findViewById(R.id.switchMorto);
        switchBatida = findViewById(R.id.switchBatida);

        buttonAction = findViewById(R.id.buttonAction);

        imageAddCarta5points = findViewById(R.id.imageAddCarta5points);
        imageAddCarta10points = findViewById(R.id.imageAddCarta10points);
        imageAddCarta15points = findViewById(R.id.imageAddCarta15points);
        imageAddCarta20points = findViewById(R.id.imageAddCarta20points);

        imageRemoveCarta5points = findViewById(R.id.imageRemoveCarta5points);
        imageRemoveCarta10points = findViewById(R.id.imageRemoveCarta10points);
        imageRemoveCarta15points = findViewById(R.id.imageRemoveCarta15points);
        imageRemoveCarta20points = findViewById(R.id.imageRemoveCarta20points);

        linearLayoutCanastraLimpa = findViewById(R.id.linearLayoutCanastraLimpa);
        linearLayoutCanastraSuja = findViewById(R.id.linearLayoutCanastraSuja);
        linearLayoutCanastraAs = findViewById(R.id.linearLayoutCanastraAs);
        linearLayoutCanastraReal = findViewById(R.id.linearLayoutCanastraReal);
    }

    /**
     * Configura os listeners dos botões e ícones
     */
    private void setupListeners() {
        setupCardQuantityListeners();
        setupInfoButton();
    }

    /**
     * Configura os listeners dos botões de adicionar/remover cartas
     */
    private void setupCardQuantityListeners() {
        ManagerCartaUtils.imageAddCartaListener(textQtdCarta5points, imageAddCarta5points, 40);
        ManagerCartaUtils.imageRemoveCartaListener(textQtdCarta5points, imageRemoveCarta5points);

        ManagerCartaUtils.imageAddCartaListener(textQtdCarta10points, imageAddCarta10points, 48);
        ManagerCartaUtils.imageRemoveCartaListener(textQtdCarta10points, imageRemoveCarta10points);

        ManagerCartaUtils.imageAddCartaListener(textQtdCarta15points, imageAddCarta15points, 8);
        ManagerCartaUtils.imageRemoveCartaListener(textQtdCarta15points, imageRemoveCarta15points);

        ManagerCartaUtils.imageAddCartaListener(textQtdCarta20points, imageAddCarta20points, 12);
        ManagerCartaUtils.imageRemoveCartaListener(textQtdCarta20points, imageRemoveCarta20points);
    }

    /**
     * Configura o botão de informações (ícone de ajuda)
     */
    private void setupInfoButton() {
        findViewById(R.id.imageInfo).setOnClickListener(v -> openScoreInfo());
    }

    /**
     * Esconde as views não utilizadas para esta tela
     */
    private void hideUnusedViews() {
        linearLayoutCanastraLimpa.setVisibility(View.GONE);
        linearLayoutCanastraSuja.setVisibility(View.GONE);
        linearLayoutCanastraAs.setVisibility(View.GONE);
        linearLayoutCanastraReal.setVisibility(View.GONE);
        switchMorto.setVisibility(View.GONE);
        switchBatida.setVisibility(View.GONE);
    }

    /**
     * Configura o título e estilo de ação da tela
     */
    private void configureUI() {
        textAction.setText("REMOVER PONTOS");
        textAction.setTextColor(ContextCompat.getColor(this, R.color.text_action_remove));
        buttonAction.setText("CALCULAR PONTUAÇÃO");
    }

    /**
     * Recupera os dados enviados pela intent (player e pontuação atual)
     */
    private void getIntentData() {
        Intent intent = getIntent();
        player = intent.getStringExtra("player");
        totalAddPoints = Integer.parseInt(intent.getStringExtra("totalAddPoints"));
        textPlayer.setText(player);
    }

    /**
     * Configura o clique no botão de calcular
     */
    private void setupCalculateButton() {
        buttonAction.setOnClickListener(v -> calculateAndReturnResult());
    }

    /**
     * Calcula a nova pontuação e retorna o resultado para a MainActivity
     */
    private void calculateAndReturnResult() {
        int totalRemovePoints = calculateRemovePoints();
        int finalScore = totalAddPoints - totalRemovePoints;

        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("finalScore", String.valueOf(finalScore));
        resultIntent.putExtra("player", player);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(resultIntent);
        finish();
    }

    /**
     * Calcula o total de pontos a remover
     */
    private int calculateRemovePoints() {
        return getPointsFromTextView(textQtdCarta5points, 5)
                + getPointsFromTextView(textQtdCarta10points, 10)
                + getPointsFromTextView(textQtdCarta15points, 15)
                + getPointsFromTextView(textQtdCarta20points, 20);
    }

    /**
     * Converte a quantidade de cartas para pontos (com multiplicador)
     */
    private int getPointsFromTextView(TextView textView, int multiplier) {
        try {
            int quantity = Integer.parseInt(textView.getText().toString().trim());
            return quantity * multiplier;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Abre a tela de informações de pontuação
     */
    private void openScoreInfo() {
        startActivity(new Intent(this, ScoreInfoActivity.class));
    }
}
