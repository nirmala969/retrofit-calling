package app.tutorial.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

import app.tutorial.com.model.demoapp.Model;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user_table";
    public static final String TAG = "DatabaseHElper";
    public static final String COL1 = "ID";
    public static final String COL2 = "name";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String crateTable = " CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
            db.execSQL(crateTable);
     /*    db.execSQL(
            "create table "+ CONTACTS_TABLE_NAME +"(id INTEGER PRIMARY KEY, name text,salary text )"
         );*/
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, s);
//      contentValues.put("name", s1);
//      contentValues.put("isAccept", s2);
//      db.replace(CONTACTS_TABLE_NAME, null, contentValues);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

/*    public static void addScripInlist(Model model) {
        if (isScripAddedInlist(String.valueOf(model.getMid()), model.getName().getFirst())) {
            removeScripFromlist(String.valueOf(model.getMid()), model.getName().getFirst());
        }
        model.save();
    }

    public static void removeScripFromlist(String id, String Name) {
        String deleteQueryString = "DELETE FROM SAVED_MODEL WHERE TOKEN = ? AND _LIST_NAME = ?";
        Model.executeQuery(deleteQueryString, id, Name);
    }


    public static boolean isScripAddedInlist(String id, String name) {
        ArrayList<Model> savedList = (ArrayList<Model>) Model.find(Model.class,
                "ID = ? AND NAME = ?", id, name);
        return savedList != null && savedList.size() > 0;
    }*/

   /* public static boolean isStockExistInlist(Model addlistModel) {
     
        ArrayList<Model> models = (ArrayList<Model>) Model.find(Model.class,
                "ID = ? AND NAME = ? AND CAN_ACCEPT = ?",
                String.valueOf(addlistModel.getMid()), addlistModel.getName().getFirst(), "true");
       
 
    }*/


}