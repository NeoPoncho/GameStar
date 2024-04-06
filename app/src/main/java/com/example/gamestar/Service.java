package com.example.gamestar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class Service extends android.app.Service {
    private MediaPlayer player;
    private MediaPlayer player2;
    private MediaPlayer player3;
    private MediaPlayer player4;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Random random = new Random();

        player = MediaPlayer.create(this, R.raw.the_game_is_on);
        player2 = MediaPlayer.create(this, R.raw.hazardous_environments);
        player3 = MediaPlayer.create(this, R.raw.minecraft);
        player4 = MediaPlayer.create(this, R.raw.save_room);

        int r = random.nextInt(4);

        if(r==0){

            player.start();
            player.setLooping(true);

            Toast.makeText(getApplicationContext(), "The Game is On", Toast.LENGTH_SHORT).show();
        }

        if(r==1){

            player2.start();
            player2.setLooping(true);

            Toast.makeText(getApplicationContext(), "Hazardous Environments", Toast.LENGTH_SHORT).show();
        }

        if(r==2){

            player3.start();
            player3.setLooping(true);

            Toast.makeText(getApplicationContext(), "C418 - Minecraft", Toast.LENGTH_SHORT).show();
        }

        if(r==3){

            player4.start();
            player4.setLooping(true);

            Toast.makeText(getApplicationContext(), "Save Room", Toast.LENGTH_SHORT).show();
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();

        player.stop();
        player2.stop();
        player3.stop();
        player4.stop();

        Toast.makeText(getApplicationContext(), "Music Stopped", Toast.LENGTH_SHORT).show();
    }
}
