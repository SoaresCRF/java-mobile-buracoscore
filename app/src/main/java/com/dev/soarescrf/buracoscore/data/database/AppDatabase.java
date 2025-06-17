package com.dev.soarescrf.buracoscore.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dev.soarescrf.buracoscore.data.model.Match;

@Database(entities = {Match.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract MatchDao matchDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "matches_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
