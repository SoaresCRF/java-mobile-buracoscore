package com.dev.soarescrf.buracoscore.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dev.soarescrf.buracoscore.data.model.Match;

import java.util.List;

@Dao
public interface MatchDao {

    @Insert
    void insertMatch(Match match);

    @Query("DELETE FROM `match`")
    void deleteAllMatches();

    @Query("SELECT * FROM `match` ORDER BY id DESC")
    List<Match> getAllMatch();
}

