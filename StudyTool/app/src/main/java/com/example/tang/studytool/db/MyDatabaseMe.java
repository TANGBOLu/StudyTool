package com.example.tang.studytool.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tang on 2017/1/6.
 */

public class MyDatabaseMe {


    MyDatabaseHelper dbHelper;

    private Context mycontext;
    private SQLiteDatabase db;
    final String DB_NAME = "PlanStore.db";
    final String TABLE_NAME="plantable";

    public MyDatabaseMe(Context context) {
        this.mycontext = context;
    }

    public void ConnectDatabase() {
        dbHelper = new MyDatabaseHelper(mycontext, DB_NAME, null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public void insertData(PlanItem item) {
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("context", item.getContext());
        values.put("iscomp", item.getIsComp());
        db.insert(TABLE_NAME, null, values); // 插入第一条数据
        values.clear();
    }

    public void updataData(PlanItem item) {

        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("context", item.getContext());
        values.put("iscomp", item.getIsComp());
        db.update(TABLE_NAME, values, "id = ?", new String[] {Integer.toString(item.getId())});

    }

    //查询数据
    public List queryData() {

        db.beginTransaction();
        List items = new ArrayList<PlanItem>();
        try {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,"id"+" DESC");
            if (cursor.moveToFirst()) {
                do {
                    PlanItem item = new PlanItem();
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String context = cursor.getString(cursor.getColumnIndex("context"));
                    int iscomp = cursor.getInt(cursor.getColumnIndex("iscomp"));

                    item.setId(id);
                    item.setTitle(title);
                    item.setContext(context);
                    item.setIsComp(iscomp);

                    items.add(item);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful(); // 事务已经执行成功
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // 结束事务
        }
        return items;
    }

    public void delete(int itemid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",new String[]{Integer.toString(itemid)});

    }
}
