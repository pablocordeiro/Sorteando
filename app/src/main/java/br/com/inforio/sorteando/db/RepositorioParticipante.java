package br.com.inforio.sorteando.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.inforio.sorteando.model.Participante;

public class RepositorioParticipante {
    private SQLiteDatabase db;
    private static RepositorioParticipante instancia = new RepositorioParticipante();

    public static RepositorioParticipante getInstancia(Context context){
        if (instancia.db == null || instancia.db.isOpen()){
            if (instancia.db != null){
                if (instancia.db.isOpen())
                    instancia.db.close();
            }
            instancia.db = new DBHelper(context).getWritableDatabase();
        }
        return instancia;
    }

    public void inserir(Participante participante) {
        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("NOME", participante.getNome());

            if (db.insert("PARTICIPANTE", null, contentValues) > -1) {
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
    }

    public Participante getParticipante(int id) {
        Cursor cursor = db.rawQuery(" SELECT * FROM PARTICIPANTE " +
                " WHERE ID = ? ", new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Participante participante = new Participante();
            participante.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            participante.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

            return participante;
        }

        cursor.close();
        return null;
    }

    public List<Participante> getParticipantes() {
        Cursor cursor = db.rawQuery(" SELECT * FROM PARTICIPANTE ", new String[]{});

        List<Participante> listaParticipantes = new ArrayList<Participante>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {
                Participante participante = new Participante();
                participante.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                participante.setNome(cursor.getString(cursor.getColumnIndex("NOME")));

                listaParticipantes.add(participante);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return listaParticipantes;
    }


    public void deletar(int id) {
        db.beginTransaction();

        try {
            db.delete("PARTICIPANTE", " ID = ? ", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void alterar(Participante participante) {
        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", participante.getId());
            contentValues.put("NOME", participante.getNome());

            db.update("PARTICIPANTE", contentValues," ID = ? ", new String[]{String.valueOf(participante.getId())});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
