package com.example.keshav.projecttcs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;

/**
 * Created by keshav on 21-06-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME="contacts.db";

    private static final String TABLE_NAME="contacts";

    private static final String TABLE_UPDATEDB="updates";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PINCODE = "pincode";

    public static final String KEY_UPDATEDB = "upd";

    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_LDATE = "ldate";
    public static final String COLUMN_UAGE = "uage";
    public static final String COLUMN_UPHONE = "uphone";


    static SQLiteDatabase db;

    //write query

    private static final String TABLE_CREATE = "create table contacts(id integer primary key not null ," +
            "name text not null, email text not null, password text not null, city text not null, age text not null, pincode text not null, gender text not null, phone text not null);";

    private static final String TABLE_CREATE_UPDATE = "create table if not exists updates( upd integer primary key not null," +
            " height text not null, weight text not null, ldate text not null, uage text not null, uphone text not null);";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    /*public void Createdb(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE_UPDATE);
        //this.db = db;
    }*/


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_UPDATE);

    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);

        //onCreate(db);


        String query1 = "DROP TABLE IF EXISTS" + TABLE_UPDATEDB;
        db.execSQL(query1);

        onCreate(db);

    }


    public void insertContact(Contact c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from contacts";    //get count of contacts
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        values.put(COLUMN_ID, count);

        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_AGE, c.getAge());
        values.put(COLUMN_GENDER, c.getGender());
        values.put(COLUMN_CITY, c.getCity());
        values.put(COLUMN_PHONE,c.getPhone());
        values.put(COLUMN_EMAIL, c.getEmail_add());
        values.put(COLUMN_PASS, c.getPassword());
        values.put(COLUMN_PINCODE, c.getPincode());

        db.insert(TABLE_NAME, null, values);

        //Profile.name.setText(cursor.getString(1));
        //db.close();
    }

    public void insertContact1(UpdateDB c1)
    {

        db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();

        String query1 = "select * from updates";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query1,null);

        int count1 = cursor.getCount();
        values1.put(KEY_UPDATEDB, count1);

        values1.put(COLUMN_HEIGHT, c1.getHeight());
        values1.put(COLUMN_WEIGHT, c1.getWeight());
        values1.put(COLUMN_LDATE, c1.getLdate());
        values1.put(COLUMN_UAGE, c1.getUage());
        values1.put(COLUMN_UPHONE, c1.getUphone());
        db.insert(TABLE_UPDATEDB, null, values1);
        db.close();
    }


    public Cursor get_info()         //Cursor class provides random read write interface
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        //cursor.moveToFirst();
        //int m;
        //m=0;

        //while (loginn.str.equals(a))
        //{
         //   m = m+1;
        //}

       cursor.moveToPosition(pos);

        Profile.name.setText(cursor.getString(1));

        Profile.age.setText(cursor.getString(5));

        Cursor res = db.rawQuery("select * from "+TABLE_UPDATEDB, null);

        return res;

    }

    public Cursor get_city()
    {
        requestpage rp = new requestpage();
        //EditText cityy = rp.city;
        //String city1 = cityy.getText().toString();
        String city1 = rp.strcity;
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor don = db.rawQuery("SELECT * FROM contacts WHERE city = '"+city1+"'  ", null);
        // Cursor don = db.rawQuery("SELECT * FROM contacts WHERE city = ?", +city1+, null);

        // Cursor don = db.rawQuery("SELECT * FROM contacts WHERE city = 'Fbd' ", null);

        String query2 = "SELECT * FROM contacts WHERE city = 'Fbd'";
        Cursor don = db.rawQuery(query2,null);

        Log.d("city1: "+city1, "city taken");
        return don;
    }



    /*public List<Contact> getAllBeneficiary() {
        // array of columns to fetch
        String[] columns = {
                //BeneficiaryContract.BeneficiaryEntry._ID,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS,
                //BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY
        };
        // sorting orders
        String sortOrder =
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME + " ASC";
        List<Contact> beneficiaryList = new ArrayList<Contact>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        String query1 = "select * from contacts";

        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query1,null);

        // Traversing through all rows and adding to list
        //if (cursor.moveToFirst()) {
            do {
                Contact beneficiary = new Contact();
                ///beneficiary.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry._ID))));
                beneficiary.setName(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME)));
                beneficiary.setEmail_add(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL)));
                beneficiary.setCity(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS)));
                //beneficiary.setCountry(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY)));
                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return beneficiaryList;
    }*/



    public static String a,b = null;
    public static int pos = 0;
    //b = "not found";
    public String searchPass(String email)
    {
        db = this.getWritableDatabase();
        String query = "select email, password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        //String a,b = null;
        b = "not found";
        if (cursor.moveToFirst())
        {
            do {
                Log.d("Error: ","Email matched");
                //Log.d("Cursor " + cursor," ");
                a = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

                if (a.equals(email))
                {
                    //Log.d("Error: ","Email matched");
                    b = cursor.getString(cursor.getColumnIndex(COLUMN_PASS));
                    pos = cursor.getPosition();
                    break;
                }

            }while (cursor.moveToNext());
        }
        return b;
    }

}
