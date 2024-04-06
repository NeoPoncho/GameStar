package com.example.gamestar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GameAdapter extends ArrayAdapter<Game>{

    public List<Game>games;
    public int LayoutID;
    public Context ctx;
    public GameAdapter gameAdapter;
    DataBase dataBase;

    public GameAdapter(@NonNull Context context, int resource, @NonNull List<Game> objects) {

        super(context, resource, objects);
        this.games = objects;
        this.ctx = context;
        this.LayoutID = resource;
        dataBase = new DataBase(ctx);
    }

    class Handler{
        TextView id;
        TextView title;
        TextView type;
        ImageView cover;
        Button button_delete;
        Button button_edit;
        Button watch_video;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {

        Handler handler = new Handler();
        Game game = games.get(position);

        if(v==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            v = inflater.inflate(LayoutID,parent,false);
            handler.id = v.findViewById(R.id.txt_id_mostrargame);
            handler.title = v.findViewById(R.id.txt_title_mostrargame);
            handler.type = v.findViewById(R.id.txt_type_mostrargame);
            handler.cover = v.findViewById(R.id.img_cover_mostrargame);
            handler.button_delete = v.findViewById(R.id.button_delete);
            handler.button_edit = v.findViewById(R.id.button_edit);
            handler.watch_video = v.findViewById(R.id.button_watch_video);

            v.setTag(handler);
        }
        else{
            handler = (Handler)v.getTag();
        }
        handler.id.setText(String.valueOf(game.id));
        handler.title.setText(game.title);
        handler.type.setText(game.type);
        handler.cover.setImageBitmap(game.getBitmap());

        handler.button_delete.setTag(game);
        handler.button_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Game game = (Game) v.getTag();
                getContext().getContentResolver().delete(GameProvider.GameContract.CONTENTURI,
                        GameProvider.GameContract.COL_1 + "=?",
                        new String[]{String.valueOf(game.getId())});

                App.crlist();

                //gameAdapter.clear();
                //gameAdapter.addAll(App.getGames());
                //gameAdapter.notifyDataSetChanged();

                App.getGames().remove(game);
                Intent it = new Intent(ctx, MainActivity.class);
                ctx.startActivity(it);
            }
        });

        handler.watch_video.setTag(game);
        handler.watch_video.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.button_watch_video) {

                    Intent it = new Intent(ctx, VideoActivity.class);

                    ctx.startActivity(it);
                }
            }
        });

        handler.button_edit.setTag(game);
        handler.button_edit.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                Game game = (Game) v.getTag();
                Intent it = new Intent(ctx, Edit.class);
                it.putExtra("Game", game);
                ((MainActivity) ctx).startActivityForResult(it, GETEDIT);
            }
        });

        return v;
    }
    public static final int GETEDIT=100;
}
