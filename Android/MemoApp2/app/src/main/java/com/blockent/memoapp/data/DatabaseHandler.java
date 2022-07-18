package com.blockent.memoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioFocusRequest;
import android.util.Log;

import androidx.annotation.Nullable;

import com.blockent.memoapp.model.Memo;
import com.blockent.memoapp.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성
        String CREATE_MEMO_TABLE = "create table " + Util.TABLE_NAME+
                "(" +
                Util.KEY_ID + " integer primary key, " +
                Util.KEY_TITLE + " text, " +
                Util.KEY_CONTENT + " text )";

        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고,
        String DROP_TABLE = "drop table " + Util.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        // 새롭게 테이블을 다시 만든다.
        onCreate(sqLiteDatabase);
    }

    public void addMemo(Memo memo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "insert into memo (title, content) " +
                "values ( ? , ? )";
        db.execSQL(query, new String[]{ memo.title, memo.content });
        db.close();
    }

    public ArrayList<Memo> getAllMemo(){
        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from memo", null);

        ArrayList<Memo> memoList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Memo memo = new Memo(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                memoList.add(memo);
            }while(cursor.moveToNext());
        }

        db.close();
        return memoList;
    }

    public void updateMemo(int id, String title, String content) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update memo " +
                " set title = ? , content = ? " +
                " where id = ? ";

        db.execSQL(query, new String[]{ title, content, id+"" });
        db.close();

    }
}



