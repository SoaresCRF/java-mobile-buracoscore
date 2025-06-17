package com.dev.soarescrf.buracoscore.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.soarescrf.buracoscore.R;
import com.dev.soarescrf.buracoscore.data.model.Match;

import java.util.List;

/**
 * Adapter responsável por exibir a lista de partidas (Match) no RecyclerView.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Match> matchList;  // Lista de partidas a ser exibida.

    /**
     * Construtor que recebe a lista inicial de partidas.
     */
    public MatchAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    /**
     * Cria novas instâncias de ViewHolder quando necessário.
     */
    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout XML de um item de partida.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    /**
     * Faz o binding dos dados da partida com as views do ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);
        bindMatchData(holder, match);
    }

    /**
     * Retorna o número total de partidas na lista.
     */
    @Override
    public int getItemCount() {
        return matchList != null ? matchList.size() : 0;
    }

    /**
     * Atualiza a lista de partidas e notifica o RecyclerView.
     */
    public void setMatches(List<Match> matchList) {
        this.matchList = matchList;
        notifyDataSetChanged();
    }

    /**
     * Preenche os dados de uma partida nas views do ViewHolder.
     */
    private void bindMatchData(MatchViewHolder holder, Match match) {
        holder.textPlayerA.setText(match.getPlayerA());
        holder.textPlayerB.setText(match.getPlayerB());
        holder.textPointsA.setText(match.getPointsA());
        holder.textPointsB.setText(match.getPointsB());
        holder.textGameDate.setText(match.getDate());

        try {
            // Tenta converter os pontos para inteiro para determinar o vencedor.
            int pointsA = Integer.parseInt(match.getPointsA());
            int pointsB = Integer.parseInt(match.getPointsB());
            applyResultColors(holder, pointsA, pointsB);
        } catch (NumberFormatException e) {
            // Caso os pontos não sejam numéricos, aplica cores padrão.
            applyDefaultColors(holder);
        }
    }

    /**
     * Aplica a cor nos textos dos jogadores de acordo com o resultado (vitória, derrota ou empate).
     */
    private void applyResultColors(MatchViewHolder holder, int pointsA, int pointsB) {
        int winColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark);
        int loseColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_dark);
        int drawColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.darker_gray);

        if (pointsA > pointsB) {
            setPlayerColors(holder, winColor, loseColor);  // Jogador A venceu
        } else if (pointsB > pointsA) {
            setPlayerColors(holder, loseColor, winColor);  // Jogador B venceu
        } else {
            setPlayerColors(holder, drawColor, drawColor);  // Empate
        }
    }

    /**
     * Aplica a cor padrão (preta) caso os pontos não sejam numéricos.
     */
    private void applyDefaultColors(MatchViewHolder holder) {
        int defaultColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black);
        setPlayerColors(holder, defaultColor, defaultColor);
    }

    /**
     * Define as cores nos textos de jogadores e suas pontuações.
     */
    private void setPlayerColors(MatchViewHolder holder, int colorA, int colorB) {
        holder.textPlayerA.setTextColor(colorA);
        holder.textPointsA.setTextColor(colorA);
        holder.textPlayerB.setTextColor(colorB);
        holder.textPointsB.setTextColor(colorB);
    }

    /**
     * ViewHolder responsável por armazenar as views de cada item de partida.
     */
    static class MatchViewHolder extends RecyclerView.ViewHolder {
        final TextView textPlayerA;
        final TextView textPlayerB;
        final TextView textPointsA;
        final TextView textPointsB;
        final TextView textGameDate;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            // Mapeamento das views do layout item_match.xml
            textPlayerA = itemView.findViewById(R.id.textPlayerA);
            textPlayerB = itemView.findViewById(R.id.textPlayerB);
            textPointsA = itemView.findViewById(R.id.textPointsA);
            textPointsB = itemView.findViewById(R.id.textPointsB);
            textGameDate = itemView.findViewById(R.id.textGameDate);
        }
    }
}
