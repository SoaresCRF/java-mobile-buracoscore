package com.dev.soarescrf.buracoscore.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.soarescrf.buracoscore.R;
import com.dev.soarescrf.buracoscore.data.database.AppDatabase;
import com.dev.soarescrf.buracoscore.data.model.Match;
import com.dev.soarescrf.buracoscore.ui.adapter.MatchAdapter;
import com.dev.soarescrf.buracoscore.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class MatchHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMatch;
    private MatchAdapter matchAdapter;
    private ImageView imageDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match_history);

        applyWindowInsets();
        initViews();
        setupRecyclerView();
        loadMatchesFromDatabase();
        updateDeleteAllVisibility();
        setupDeleteAllClickListener();
    }

    /**
     * Trata os insets de sistema (status bar, navigation bar etc)
     */
    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa as views da tela
     */
    private void initViews() {
        recyclerViewMatch = findViewById(R.id.recyclerViewMatch);
        imageDeleteAll = findViewById(R.id.imageDeleteAll);
    }

    /**
     * Configura o adapter e a RecyclerView
     */
    private void setupRecyclerView() {
        matchAdapter = new MatchAdapter(new ArrayList<>());
        recyclerViewMatch.setAdapter(matchAdapter);
    }

    /**
     * Busca as partidas salvas no banco e exibe na RecyclerView
     */
    private void loadMatchesFromDatabase() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<Match> matches = db.matchDao().getAllMatch();
            runOnUiThread(() -> handleMatchesResult(matches));
        }).start();
    }

    /**
     * Atualiza a visibilidade do botão de deletar tudo com base nos dados
     */
    private void updateDeleteAllVisibility() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<Match> matches = db.matchDao().getAllMatch();
            runOnUiThread(() -> imageDeleteAll.setVisibility(matches.isEmpty() ? View.GONE : View.VISIBLE));
        }).start();
    }

    /**
     * Configura o clique no botão de deletar todas as partidas
     */
    private void setupDeleteAllClickListener() {
        imageDeleteAll.setOnClickListener(v -> showDeleteAllConfirmationDialog());
    }

    /**
     * Exibe o diálogo de confirmação antes de apagar tudo
     */
    private void showDeleteAllConfirmationDialog() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_confirmation, null);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        configureDeleteDialogTexts(view);
        setupDialogConfirmationClick(dialog, view);

        dialog.show();
    }

    /**
     * Configura os textos do diálogo de confirmação
     */
    private void configureDeleteDialogTexts(View view) {
        TextView textDialogTitle = view.findViewById(R.id.textDialogTitle);
        TextView textMessage = view.findViewById(R.id.textMessage);

        textDialogTitle.setText("Atenção!");
        textMessage.setText("Essa ação irá apagar todo o histórico de partidas. Deseja continuar?");
        TextUtils.configureJustifiedText(textMessage);
    }

    /**
     * Configura o clique de confirmação no diálogo
     */
    private void setupDialogConfirmationClick(Dialog dialog, View view) {
        ImageView imageCheck = view.findViewById(R.id.imageCheck);
        imageCheck.setOnClickListener(v -> {
            deleteAllMatchesFromDatabase();
            showToast("Todo o histórico de partidas foi excluído com sucesso!");
            dialog.dismiss();
            recreate();
        });
    }

    /**
     * Executa a exclusão de todas as partidas no banco
     */
    private void deleteAllMatchesFromDatabase() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            db.matchDao().deleteAllMatches();
        }).start();
    }

    /**
     * Atualiza a UI com o resultado da busca de partidas
     */
    private void handleMatchesResult(List<Match> matches) {
        matchAdapter.setMatches(matches);
        if (matches.isEmpty()) {
            showToast("Nenhuma partida encontrada.");
        }
    }

    /**
     * Exibe um Toast com a mensagem passada
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
