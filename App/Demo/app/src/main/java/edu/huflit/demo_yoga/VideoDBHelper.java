package com.android.shopdt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class VideoDBHelper
{

//    private static  final String DATABASE_NAME = Utils.DATABASE_NAME;
//    private static  final int DATABASE_VERSION = 1;
//    private Context context;
//    public VideoDBHelper(Context context) {
//        super(context,DATABASE_NAME,null,DATABASE_VERSION);
//    }
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String query = " CREATE TABLE " + Utils.TABLE_VIDEO + " (" + Utils.COLUMN_VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Utils.COLUMN_VNAME + " TEXT, " + Utils.COLUMN_VURL + " TEXT, " + Utils.COLUMN_DES1 + " TEXT, " + Utils.COLUMN_DES2 + " TEXT, " + Utils.COLUMN_DESCRIPTION + " TEXT, " + Utils.COLUMN_YTID + " TEXT);";
//        db.execSQL(query);
////        db.execSQL("INSERT INTO TABLE_VIDEO VALUES(null," +
////                "'Seated Cat Cow'," +
////                "'https://drive.google.com/file/d/1c4K-ETXbaBXKVJoW0lEMBO5_3b4mJq1_/view'," +
////                "'Repeat 2 Times'," +
////                "'00:50'," +
////                "'Start in a lunge position, but with your right leg on the floor. Tuck your pelvis in and sink your hips. Raise your arms up by your ears with your palms facing each other. Look straight ahead. Hold this position. Take full and deep breaths. Lengthen your spine each time you exhale.'," +
////                "'4QszA4WXwNY')");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS  " + Utils.TABLE_VIDEO);
//        onCreate(db);
//    }
    String dbName = "VideoLists.db";
    Context context;
    SQLiteDatabase db;


    public VideoDBHelper(Context context){
        this.context = context;
    }
    public  SQLiteDatabase openDB(){
        return  context.openOrCreateDatabase(dbName,Context.MODE_PRIVATE,null);
    }
    public int countVideo(){
        String sql = "SELECT * FROM tblVIDEO";
        db = openDB();
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        return count;
    }
    public  void copyDatabase() {
        File dbFile = context.getDatabasePath(dbName);
         if (!dbFile.exists()){
            try {
                InputStream is = context.getAssets().open(dbName);
                OutputStream os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0) {
                    os.write(buffer);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<VideoList> getVideos() {
            ArrayList<VideoList> tmp = new ArrayList<>();
                db = openDB();
                String sql = "SELECT * FROM tblVIDEO";
                Cursor cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String vname = cursor.getString(1);
                    String url = cursor.getString(2);
                    String des1 = cursor.getString(3);
                    String des2 = cursor.getString(4);
                    String ytid = cursor.getString(5);
                    String description = cursor.getString(6);
                    VideoList videoList = new VideoList(id, vname, url, des1, des2, ytid, description);
                    tmp.add(videoList);
                }
                db.close();
        return tmp;
    }
    public String getID(String id) {
        Cursor cursor = null;
        String empName = "";
        try {
            cursor = db.rawQuery("SELECT YTID FROM tblVIDEO WHERE vID=?", new String[] {id + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("YTID"));
            }
            return empName;
        }finally {
            cursor.close();
        }
    }
}
