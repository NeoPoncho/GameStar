package com.example.gamestar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class Action implements View.OnClickListener {

    Context ctx;
    public Action(Context ctx){

        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {

        Intent it = new Intent(ctx, InsertGame.class);
        ctx.startActivity(it);
    }
}
