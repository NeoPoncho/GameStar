package com.example.gamestar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="GameLibrary.db";
    public static final String TABLE_NAME = "Games";
    public static final String COLUMN_1 = "id";
    public static final String COLUMN_2 = "title";
    public static final String COLUMN_3 = "type";
    public static final String COLUMN_4 = "cover";

    public Context ctx;

    public DataBase(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String sql= "CREATE TABLE " + TABLE_NAME + " (";
            sql += " " + COLUMN_1 + "  INTEGER AUTO_INCREMENT PRIMARY KEY, ";
            sql += " " + COLUMN_2 + "  VARCHAR(120), ";
            sql += " " + COLUMN_3 + "  VARCHAR(120), ";
            sql += " " + COLUMN_4 + "  BLOB );";
            db.execSQL(sql);
        }
        catch(SQLException error){
            Toast.makeText(ctx,error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +" ;");
        onCreate(db);
    }

    public Boolean insertGame(int id, String title, String type, byte[] cover) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_1, id);
            cv.put(COLUMN_2, title);
            cv.put(COLUMN_3, type);
            cv.put(COLUMN_4, cover);
            long i = db.insert(TABLE_NAME, null, cv);

            return (i > 0) ? true : false;
        }
        catch (Exception error) {
            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    public boolean EditGame(String id ,String title, String type, byte[] cover){

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_2,title);
        cv.put(COLUMN_3,type);
        cv.put(COLUMN_4,cover);
        SQLiteDatabase db = this.getWritableDatabase();
        long i=db.update(TABLE_NAME,cv,COLUMN_1 + "=?", new String[]{id});

        return (i<=0)?false:true;
    }

    public boolean DeleteGame(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.delete(TABLE_NAME,COLUMN_1 + "=?", new String[]{id});

        return (i<=0)?false:true;
    }

    public Cursor getGames(){

        Cursor cursor = null;

        try{
            SQLiteDatabase database =getWritableDatabase();
            cursor = database.rawQuery("select  * from " + TABLE_NAME + ";",null);

            return cursor;
        }
        catch(Exception error){
            Toast.makeText(ctx,error.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return cursor;
    }
}
