package br.com.inforio.sorteando.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NOME_DO_BANCO = "sorteando";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context ctx) {
        super(ctx, NOME_DO_BANCO, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(" CREATE TABLE PARTICIPANTE (" +
                   "   ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "   NOME VARCHAR(60)" +
                ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
