package com.example.gamestar;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GameProvider extends ContentProvider {

    public static final String DATABASENAME = "GameLibrary.db";
    public static final int VERSION = 1;
    public static String AUTHORITY = "com.app.games";
    public static final Uri BASECONTENTURI = Uri.parse("content://" + AUTHORITY);
    public static final int GAMES = 0;
    public static final int GAME = 1;
    public static UriMatcher uriMatcher;
    SQLiteDatabase db;

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, GameContract.PATH, GAMES);
        uriMatcher.addURI(AUTHORITY, GameContract.PATH + "/#", GAME);
    }

    public static class GameContract implements BaseColumns {

        public static final String PATH = "games";
        public static final Uri CONTENTURI = BASECONTENTURI.buildUpon().appendPath(PATH).build();
        public static final String TABLENAME = "games";
        public static final String COL_1 = "id";
        public static final String COL_2 = "title";
        public static final String COL_3 = "type";
        public static final String COL_4 = "cover";

        public static String CreateTable() {

            String mysql = "CREATE TABLE " + TABLENAME + " (";
            mysql += COL_1 + " INTEGER  PRIMARY KEY AUTOINCREMENT, ";
            mysql += COL_2 + "    VARCHAR (80),";
            mysql += COL_3 + " VARCHAR (80),";
            mysql += COL_4 + "   BLOB );";

            return mysql;
        }

        public static String DeleteTable() {

            String mysql = "DROP TABLE IF EXISTS " + TABLENAME + ";";

            return mysql;
        }
    }


    public class MyBD extends SQLiteOpenHelper {

        public MyBD(@Nullable Context context) {
            super(context, DATABASENAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(GameContract.CreateTable());

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            db.execSQL(GameContract.DeleteTable());
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        MyBD myBD = new MyBD(getContext());
        db= myBD.getWritableDatabase();
        return  (db==null)?false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)){

            case GAMES:
                cursor = db.query(GameContract.TABLENAME,null,null,null,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

                return  cursor;

            case GAME:
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                long id = ContentUris.parseId(uri);
                qb.appendWhere(GameContract.COL_1 + "=" + id);
                cursor = qb.query(db,null,null,null,null,null,null);
                cursor.setNotificationUri(getContext().getContentResolver(),uri);

                return  cursor;

            default:
                throw  new IllegalArgumentException("INVALID URI");

        }


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues cv) {

        switch (uriMatcher.match(uri)){

            case GAMES:
                long novo = db.insert(GameContract.TABLENAME,null,cv );
                Uri _uri = ContentUris.withAppendedId(uri, novo);
                getContext().getContentResolver().notifyChange(_uri,null);

                return  _uri;

            default:
                throw  new IllegalArgumentException("INSERT ERROR");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {

        switch (uriMatcher.match(uri)){

            case GAMES:
                int total = db.delete(GameContract.TABLENAME,where,whereArgs);
                if(total > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }

                return  total;

            default:
                throw  new IllegalArgumentException("DELETE ERROR");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues cv, @Nullable String where, @Nullable String[] whereArgs) {

        switch (uriMatcher.match(uri)){

            case GAMES:
                int total = db.update(GameContract.TABLENAME,cv,where,whereArgs);
                if(total > 0){
                    getContext().getContentResolver().notifyChange(uri,null);
                }

                return  total;

            default:
                throw  new IllegalArgumentException("UPDATE ERROR");
        }
    }
}
