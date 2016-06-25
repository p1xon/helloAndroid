package com.example.administrator.chabaike.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.chabaike.beans.Info;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add infos
     *
     * @param info
     */
    public void add(Info info, String table) {

        ContentValues values = new ContentValues();
        values.put("title", info.getTitle());
        values.put("_id", info.getId());
        System.out.println("add===="+info.getTitle());
        db.insert(table, null, values);
    }


    /**
     * query all infos, return list
     *
     * @return List<Info>
     */
    public List<Info> query(String table) {
        ArrayList<Info> infos = new ArrayList<Info>();
        Cursor c = queryTheCursor(table);
        while (c.moveToNext()) {
            Info info = new Info();
            info.setId(c.getLong(c.getColumnIndex("_id")));
            info.setTitle(c.getString(c.getColumnIndex("title")));
            System.out.println("query"+info.getTitle());
            infos.add(info);
        }
        c.close();
        return infos;
    }

    /**
     * query all infos, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor(String table) {
        Cursor c = db.rawQuery("SELECT * FROM " + table + "", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
