package com.example.gamestar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ListView list;
    GameAdapter adpt;
    FloatingActionButton fab;
    private ToggleButton btplaymusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GameStar);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.lv_games_mainactivity);
        fab = findViewById(R.id.fab_insertgame_mainactivity);
        btplaymusic = findViewById(R.id.btstartmusic);

        btplaymusic.setOnCheckedChangeListener(this);

        GameAdapter gameAdapter = new GameAdapter(MainActivity.this,R.layout.mostrargame,App.getGames());
        list.setAdapter(gameAdapter);

        fab.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Insert New game", Snackbar.LENGTH_LONG).setAction("OK", new Action(MainActivity.this)).show();
            }
        });

        adpt= new GameAdapter(this,R.layout.mostrargame,App.games);
        list=findViewById(R.id.lv_games_mainactivity);
        list.setAdapter(adpt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){

            try {

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
            catch (Exception error){

                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            startService(new Intent(this, Service.class));
        } else {

            stopService(new Intent(this, Service.class));
        }
    }
}