package com.example.gamestar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Game implements Parcelable {

    int id;
    String title;
    String type;
    byte[] cover;

    public Game(int id, String title, String type, byte[] cover) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.cover = cover;
    }

    public Game(int id, String title, String type, Bitmap bmp) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.cover = BitmapToArray(bmp);
    }

    public Bitmap getBitmap(){
        return BitmapFactory.decodeByteArray(cover, 0, cover.length);
    }

    public byte[] BitmapToArray(Bitmap bmp){

        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

//region Parcelableimp
    protected Game(Parcel in) {
        id = in.readInt();
        title = in.readString();
        type = in.readString();
        cover = in.createByteArray();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeByteArray(cover);
    }
//endregion
//region GettersSetters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }
    //endregion
}
