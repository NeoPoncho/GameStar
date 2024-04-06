package com.example.gamestar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import static android.view.View.*;

public class InsertGame extends AppCompatActivity {

    public static final int GETIMAGE = 1;

    Button btsave;
    Spinner spin;
    ImageView cover;
    EditText editid;
    EditText edittitle;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GETIMAGE  && resultCode==RESULT_OK){

            Uri uri = Uri.parse(data.getData().toString());
            cover.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_game);

        cover = findViewById(R.id.img_cover_insert);
        cover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it,GETIMAGE);
            }
        });

        btsave = findViewById(R.id.button_save);
        btsave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    int id = Integer.parseInt(editid.getText().toString());
                    String title = edittitle .getText().toString();
                    String type = spin.getSelectedItem().toString();
                    BitmapDrawable draw = (BitmapDrawable) cover.getDrawable();
                    Bitmap bmp = draw.getBitmap();
                    Game newgame = new Game(id,title,type,bmp);

                    ContentValues cv= new ContentValues();
                    cv.put(GameProvider.GameContract.COL_1,newgame.getId());
                    cv.put(GameProvider.GameContract.COL_2, newgame.title);
                    cv.put(GameProvider.GameContract.COL_3, newgame.getType());
                    cv.put(GameProvider.GameContract.COL_4,newgame.getCover());
                    ContentResolver rs = getApplicationContext().getContentResolver();
                    rs.insert(GameProvider.GameContract.CONTENTURI,cv);

                    App.crlist();
                    finish();

                    Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(refresh);

                    Toast.makeText(InsertGame.this,title + " added!", Toast.LENGTH_LONG).show();
                }
                catch (Exception error){

                    Toast.makeText(InsertGame.this,"Could'nt add game", Toast.LENGTH_LONG).show();
                }
            }
        });

        editid = findViewById(R.id.edit_id_insert);
        edittitle = findViewById(R.id.edit_title_insert);

        spin = findViewById(R.id.spin_type_insert);
        ArrayAdapter<CharSequence> adpt = ArrayAdapter.createFromResource(InsertGame.this,R.array.Type,R.layout.typespinner);
        adpt.setDropDownViewResource(R.layout.typespinner);
        spin.setAdapter(adpt);
    }
}