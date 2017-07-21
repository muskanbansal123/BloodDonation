package com.example.keshav.projecttcs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keshav on 17-07-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_IMAGE = "images";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_POTO = "poto";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase dbi, int oldVersion, int newVersion) {

        // Drop older table if existed
        dbi.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);

        // Create tables again
        onCreate(dbi);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addImages(Image image){
        SQLiteDatabase dbi = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, image.getFName());
        values.put(KEY_POTO, image.getImage() );


        dbi.insert(TABLE_IMAGE, null, values);
        dbi.close();
    }


    /**
     *Getting All Contacts
     **/

    public List<Image> getAllImages() {
        List<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

        SQLiteDatabase dbi = this.getWritableDatabase();
        Cursor cursori = dbi.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursori.moveToFirst()) {
            do {
                Image image = new Image();
                image.setID(Integer.parseInt(cursori.getString(0)));
                image.setFName(cursori.getString(1));
                image.setImage(cursori.getBlob(2));


                // Adding contact to list
                imageList.add(image);
            } while (cursori.moveToNext());
        }

        // return contact list
        return imageList;
    }


    /**
     *Updating single contact
     **/

    public int updateImage(Image image, int id) {
        SQLiteDatabase dbi = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, image.getFName());
        values.put(KEY_POTO, image.getImage());


        // updating row
        return dbi.update(TABLE_IMAGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     *Deleting single contact
     **/

    public void deleteContact(int Id) {
        SQLiteDatabase dbi = this.getWritableDatabase();
        dbi.delete(TABLE_IMAGE, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        dbi.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_IMAGE + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_FNAME +" TEXT,"
                + KEY_POTO  +" BLOB" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);

    }
}
