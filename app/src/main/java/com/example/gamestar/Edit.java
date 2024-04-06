package com.example.gamestar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit extends AppCompatActivity {

    ImageView cover;
    EditText editid;
    EditText edittitle;
    Spinner spintype;
    Button button_save;
    Button button_cancel;

    DataBase db = new DataBase(Edit.this);

    void SHOWGAME(){

        try {
            Intent it = getIntent();
            Game game = (Game)it.getParcelableExtra("Game");

            editid.setText(String.valueOf(game.id));
            edittitle.setText(game.title);
            cover.setImageBitmap(game.getBitmap());
            spintype.setSelection(Utils.getTypeOrd(game.type));
        }
        catch (Exception error){
            AlertDialog.Builder cx = new AlertDialog.Builder(Edit.this);
            cx.setTitle("Error").setMessage(error.getMessage()).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        spintype = findViewById(R.id.spin_type_edit);
        ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(this,R.array.Type,R.layout.typespinner);
        adpt.setDropDownViewResource(R.layout.typespinner);
        spintype.setAdapter(adpt);

        cover = findViewById(R.id.img_cover_edit);
        editid = findViewById(R.id.edit_id_edit);
        edittitle = findViewById(R.id.edit_title_edit);
        button_save = findViewById(R.id.button_save_edit);
        button_cancel = findViewById(R.id.button_cancel_edit);

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(Intent.ACTION_GET_CONTENT);
                it1.setType("image/*");
                startActivityForResult(it1,2);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    String title = edittitle.getText().toString();
                    int id = Integer.parseInt(editid.getText().toString());
                    String type = spintype.getSelectedItem().toString();
                    Drawable drw = cover.getDrawable();
                    Bitmap bmp = ((BitmapDrawable) drw).getBitmap();
                    Game game = new Game(id,title,type,bmp);

                ContentValues cv = new ContentValues();
                cv.put(GameProvider.GameContract.COL_1,game.getId());
                cv.put(GameProvider.GameContract.COL_2, game.getTitle());
                cv.put(GameProvider.GameContract.COL_3, game.getType());
                cv.put(GameProvider.GameContract.COL_4,game.getCover());
                getApplicationContext().getContentResolver().update(GameProvider.GameContract.CONTENTURI,
                        cv,
                        GameProvider.GameContract.COL_1 +  "=?",
                        new String[]{String.valueOf(game.getId())}

                );

                    Utils.editGame(game);
                    db.EditGame(String.valueOf(id),game.title,game.type,game.cover);
                    Toast.makeText(Edit.this, "All Changes Successful", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(it, 0);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Edit.this, "No changes were made", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        SHOWGAME();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            Uri uri= Uri.parse(data.getData().toString());
            cover.setImageURI(uri);
        }
        catch(Exception error){
            AlertDialog.Builder cx = new AlertDialog.Builder(Edit.this);
            cx.setTitle("Error").setMessage(error.getMessage()).show();
        }
    }
}