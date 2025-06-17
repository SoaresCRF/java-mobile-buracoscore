package com.dev.soarescrf.buracoscore.data.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Match {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String playerA, playerB, date, pointsA, pointsB;

    public Match(String playerA, String pointsA, String playerB, String pointsB, String date) {
        this.playerA = playerA;
        this.pointsA = pointsA;
        this.playerB = playerB;
        this.pointsB = pointsB;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public String getDate() {
        return date;
    }

    public String getPointsA() {
        return pointsA;
    }

    public String getPointsB() {
        return pointsB;
    }
}


