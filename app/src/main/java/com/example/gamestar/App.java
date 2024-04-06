package com.example.gamestar;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class  App extends android.app.Application{

    private static Context ctx;
    public static List<Game> games = new ArrayList<>();

    @Override
    public void onCreate() {

        super.onCreate();
        ctx = getApplicationContext();
        crlist();
    }

    public static Context getCtx() {
        return ctx;
    }

    public static List<Game> getGames() {

        games.clear();
        crlist();

        return games;
    }

    public static void crlist(){

        ContentResolver rs = ctx.getContentResolver();
        Cursor cur = rs.query(GameProvider.GameContract.CONTENTURI,null,null,null,null);
        if(cur == null || cur.getCount() <=0)return;
        while(cur.moveToNext()){
            games.add(new Game(cur.getInt(0),cur.getString(1), cur.getString(2), cur.getBlob(3)));
        }
    }

    public static Context getAppContext() {

        return App.ctx;
    }
}
