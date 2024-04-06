package com.example.gamestar;

import android.content.Context;

import java.util.List;

public class Utils {

    public  static  void editGame(Game game){

        int i = 0;

        for(i = 0; i < App.games.size(); i++){

            if(App.games.get(i).id==game.id){

                App.games.get(i).setTitle(game.getTitle());
                App.games.get(i).setType(game.getType());
                App.games.get(i).setCover(game.getCover());
            }
        }
    }

    public static int getTypeOrd(String str){

        Context ctx = App.getAppContext();
        String[]type = ctx.getResources().getStringArray(R.array.Type);

        for(int i = 0; i < type.length; i++){
            if(type[i].equals(str)) return i;
        }

        return 0;
    }
}
