package com.example.abdurrahman.footballapps.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.abdurrahman.footballapps.model.Favorite
import org.jetbrains.anko.db.*

class MyDatabase(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: MyDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context) : MyDatabase {
            if (instance == null) {
                instance = MyDatabase(ctx.applicationContext)
            }
            return instance as MyDatabase
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Favorite.TABLE_FAVORITE_MATCH, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.EVENT_ID to TEXT + UNIQUE,
                Favorite.TEAM_HOME_ID to TEXT,
                Favorite.TEAM_AWAY_ID to TEXT,
                Favorite.TEAM_HOME_NAME to TEXT,
                Favorite.TEAM_AWAY_NAME to TEXT,
                Favorite.TEAM_HOME_SCORE to TEXT,
                Favorite.TEAM_AWAY_SCORE to TEXT,
                Favorite.TEAM_HOME_GOAL to TEXT,
                Favorite.TEAM_AWAY_GOAL to TEXT,
                Favorite.TEAM_HOME_SHOTS to TEXT,
                Favorite.TEAM_AWAY_SHOTS to TEXT,
                Favorite.TEAM_AWAY_GOALKEEPER to TEXT,
                Favorite.TEAM_HOME_GOALKEEPER to TEXT,
                Favorite.TEAM_HOME_DEFENSE to TEXT,
                Favorite.TEAM_AWAY_DEFENSE to TEXT,
                Favorite.TEAM_HOME_MIDFIELD to TEXT,
                Favorite.TEAM_AWAY_MIDFIELD to TEXT,
                Favorite.TEAM_HOME_FORWARD to TEXT,
                Favorite.TEAM_AWAY_FORWARD to TEXT,
                Favorite.TEAM_HOME_SUBTITUTIS to TEXT,
                Favorite.TEAM_AWAY_SUBTITUTIS to TEXT,
                Favorite.TEAM_HOME_FORMATION to TEXT,
                Favorite.TEAM_AWAY_FORMATION to TEXT,
                Favorite.TEAM_BADGE to TEXT,
                Favorite.TEAM_DATE_EVENT to TEXT)

        db?.createTable(Favorite.TABLE_FAVORITE_TEAMS, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.TEAMS_ID to TEXT + UNIQUE,
                Favorite.TEAM_NAME to TEXT,
                Favorite.TEAM_DESC to TEXT,
                Favorite.TEAM_YEAR to TEXT,
                Favorite.TEAM_STADIUM to TEXT,
                Favorite.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE_MATCH, true)
        db?.dropTable(Favorite.TABLE_FAVORITE_TEAMS, true)
    }
}

val Context.database : MyDatabase
    get() = MyDatabase.getInstance(applicationContext)