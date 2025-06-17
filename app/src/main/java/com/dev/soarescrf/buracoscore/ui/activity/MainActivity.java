package com.dev.soarescrf.buracoscore.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dev.soarescrf.buracoscore.R;
import com.dev.soarescrf.buracoscore.data.database.AppDatabase;
import com.dev.soarescrf.buracoscore.data.model.Match;
import com.dev.soarescrf.buracoscore.data.preferences.PlayerNamePreferenceManager;
import com.dev.soarescrf.buracoscore.utils.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Tela principal do app.
 * Responsável por exibir o placar atual, permitir adicionar pontos,
 * editar nomes dos jogadores e salvar partidas.
 */
public class MainActivity extends AppCompatActivity {

    private PlayerNamePreferenceManager namePrefs;

    private TextView textPlayerA, textPlayerB, textPointsA, textPointsB;
    private Button buttonSaveScore;
    private ImageView imageQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        namePrefs = new PlayerNamePreferenceManager(this);

        applyWindowInsets();
        initViews();
        loadPlayerNames();
        handleScoreUpdate(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleScoreUpdate(intent);
    }

    /**
     * Trata as margens de sistema (status bar, navigation bar)
     */
    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa as Views da tela principal
     */
    private void initViews() {
        textPlayerA = findViewById(R.id.textPlayerA);
        textPlayerB = findViewById(R.id.textPlayerB);
        textPointsA = findViewById(R.id.textPointsA);
        textPointsB = findViewById(R.id.textPointsB);
        imageQuestion = findViewById(R.id.imageQuestion);
        buttonSaveScore = findViewById(R.id.buttonSaveScore);

        setupClickListeners();
    }

    /**
     * Define todos os listeners de clique da tela
     */
    private void setupClickListeners() {
        findViewById(R.id.imageStartA).setOnClickListener(v -> startAddPointsActivity(textPlayerA));
        findViewById(R.id.imageStartB).setOnClickListener(v -> startAddPointsActivity(textPlayerB));

        findViewById(R.id.imageEditPlayerNameA).setOnClickListener(v -> showChangeNameDialog(textPlayerA));
        findViewById(R.id.imageEditPlayerNameB).setOnClickListener(v -> showChangeNameDialog(textPlayerB));

        imageQuestion.setOnClickListener(v -> openRulesActivity());
        findViewById(R.id.imageMatchHistory).setOnClickListener(v -> openMatchHistory());

        buttonSaveScore.setOnClickListener(v -> showSaveMatchConfirmationDialog());
    }

    /**
     * Carrega os nomes dos jogadores salvos nas SharedPreferences
     */
    private void loadPlayerNames() {
        textPlayerA.setText(namePrefs.getPlayerNameA());
        textPlayerB.setText(namePrefs.getPlayerNameB());
    }

    /**
     * Trata o resultado de uma adição de pontos ao retornar de outra Activity
     */
    private void handleScoreUpdate(Intent intent) {
        String player = intent.getStringExtra("player");
        String score = intent.getStringExtra("finalScore");

        if (player == null || score == null) return;

        if (player.equals(textPlayerA.getText().toString())) {
            textPointsA.setText(score);
        } else if (player.equals(textPlayerB.getText().toString())) {
            textPointsB.setText(score);
        }
    }

    /**
     * Abre a Activity de adicionar pontos, passando o nome do jogador
     */
    private void startAddPointsActivity(TextView playerTextView) {
        Intent intent = new Intent(this, AddPointsActivity.class);
        intent.putExtra("player", playerTextView.getText().toString());
        startActivity(intent);
    }

    /**
     * Exibe a Activity de Regras
     */
    private void openRulesActivity() {
        startActivity(new Intent(this, RulesActivity.class));
    }

    /**
     * Exibe o Histórico de Partidas
     */
    private void openMatchHistory() {
        startActivity(new Intent(this, MatchHistoryActivity.class));
    }

    /**
     * Abre o dialog para alterar o nome de um jogador
     */
    private void showChangeNameDialog(TextView playerTextView) {
        Dialog dialog = createDialog(R.layout.custom_dialog_new_name);

        TextView title = dialog.findViewById(R.id.textDialogTitle);
        EditText inputName = dialog.findViewById(R.id.inputNewPlayerName);
        ImageView saveIcon = dialog.findViewById(R.id.imageConfirm);

        title.setText(playerTextView.getText().toString());

        saveIcon.setOnClickListener(v -> handleNameChange(inputName, playerTextView, dialog));

        dialog.show();
    }

    /**
     * Trata a lógica de alteração de nome de jogador
     */
    private void handleNameChange(EditText inputName, TextView playerTextView, Dialog dialog) {
        String newName = inputName.getText().toString().trim().replaceAll("\\s+", " ");

        if (newName.isEmpty()) {
            showToast("Preencha o campo!");
            return;
        }

        if (playerTextView == textPlayerA) {
            namePrefs.savePlayerNameA(newName);
        } else {
            namePrefs.savePlayerNameB(newName);
        }

        dialog.dismiss();
        recreate(); // Atualiza nomes na tela
    }

    /**
     * Exibe o dialog de confirmação antes de salvar a partida
     */
    private void showSaveMatchConfirmationDialog() {
        if (isBothScoresZero()) {
            showToast("Calcule a pontuação antes de salvar!");
            return;
        }

        Dialog dialog = createDialog(R.layout.custom_dialog_confirmation);

        TextView textDialogTitle = dialog.findViewById(R.id.textDialogTitle);
        TextView textMessage = dialog.findViewById(R.id.textMessage);
        ImageView imageCheck = dialog.findViewById(R.id.imageCheck);

        textDialogTitle.setText("Confirmação!");
        textMessage.setText("Deseja salvar a pontuação dessa partida?");
        TextUtils.configureJustifiedText(textMessage);

        imageCheck.setOnClickListener(v -> {
            saveMatchScore();
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * Verifica se ambas as pontuações estão zeradas
     */
    private boolean isBothScoresZero() {
        return textPointsA.getText().toString().equals("0") &&
                textPointsB.getText().toString().equals("0");
    }

    /**
     * Salva a partida no banco de dados
     */
    private void saveMatchScore() {
        String playerA = textPlayerA.getText().toString();
        String playerB = textPlayerB.getText().toString();
        String pointsA = textPointsA.getText().toString();
        String pointsB = textPointsB.getText().toString();

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Match match = new Match(playerA, pointsA, playerB, pointsB, getCurrentDate());
            db.matchDao().insertMatch(match);
        }).start();

        showToast("Dados da partida salvos com sucesso!");
        resetScores();
    }

    /**
     * Zera os placares após o salvamento
     */
    private void resetScores() {
        textPointsA.setText("0");
        textPointsB.setText("0");
    }

    /**
     * Retorna a data atual no formato dd/MM/yyyy com fuso horário de São Paulo
     */
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(new Date());
    }

    /**
     * Exibe um Toast de forma centralizada
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Cria e retorna um Dialog customizado com background transparente
     */
    private Dialog createDialog(int layoutResId) {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(layoutResId, null);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
