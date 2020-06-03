package com.oleskiy.taboola.view.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oleskiy.taboola.view.MyApp;
import com.oleskiy.taboola.view.model.Item;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper = null;
    public static DBHelper getInstance(){
        if(dbHelper==null){
            dbHelper = new DBHelper(MyApp.getInstance().getBaseContext());
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, "taboolaDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table items ("
                + "id integer primary key ,"
                + "name text,"
                + "description text,"
                + "color integer,"
                + "thumbnail text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  }

    /*
    * Add new item or if item exist - update
    * @itemModel - item for add or update
    * */
    public void addOrUpdateData(Item itemModel){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String selectQuery = "SELECT  * FROM " + "items" + " WHERE "
                + "id" + " = " + itemModel.getId();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null&&c.getCount()>0){
            c.moveToFirst();

            if(!c.getString(c.getColumnIndex("description")).equals(itemModel.getDescription())){
                cv.put("description", itemModel.getDescription());
            }
            if(!c.getString(c.getColumnIndex("thumbnail")).equals(itemModel.getThumbnail())){
                cv.put("thumbnail", itemModel.getThumbnail());
            }
            if(!c.getString(c.getColumnIndex("name")).equals(itemModel.getName())){
                cv.put("name", itemModel.getName());
            }
            if(!cv.toString().isEmpty()) {
                long rowId = db.update("items", cv, "id=" + itemModel.getId(), null);
            }
        }else {
            cv.put("name", itemModel.getName());
            cv.put("description", itemModel.getDescription());
            cv.put("thumbnail", itemModel.getThumbnail());
            cv.put("color", itemModel.getColor());
            long rowID = db.insert("items", null, cv);
        }

        dbHelper.close();
    }


    /*
    * Get all items from DB
    * */
    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("items", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int name = c.getColumnIndex("name");
            int description = c.getColumnIndex("description");
            int thumbnail = c.getColumnIndex("thumbnail");
            int color = c.getColumnIndex("color");
            int id = c.getColumnIndex("id");
            do {
                Item item = new Item(c.getString(name),c.getString(description),c.getString(thumbnail),c.getInt(color));
                item.setId(c.getInt(id));
                items.add(item);

            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
        return items;
    }

/*
* Update item's background color
* */
    public int updateColor(int index, int color){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
       cv.put("color",color);
        int id = db.update("items", cv, "id="+index, null);
        return id;
    }

}