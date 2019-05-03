package com.example.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBContacts extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBContacts.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CONTACTS_TABLE_NAME = "T_contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";

    public DBContacts(Context context) {

        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table T_contacts " +
                        "(id integer primary key, name text unique , phone text, email text, street text, place text)"
        );
        Log.i("DATABASE", "onCreate invoked");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS T_contacts");
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from T_contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean insertContact (String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insertOrThrow("T_contacts", null, contentValues);
        return true;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("T_contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("T_contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<ListContacts> getAllContacts() {
        ArrayList<ListContacts> array_list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect={CONTACTS_COLUMN_ID, CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_PHONE, CONTACTS_COLUMN_EMAIL, CONTACTS_COLUMN_STREET, CONTACTS_COLUMN_CITY};
        qb.setTables(CONTACTS_TABLE_NAME);
        Cursor res = qb.query(db, sqlSelect, null, null, null, null, "name asc");
        res.moveToFirst();
        while(!res.isAfterLast() ){
            ListContacts list = new ListContacts (res.getInt(0), res.getString(1),
                    res.getString(2), res.getString(3), res.getString(4), res.getString(5));
            array_list.add(list);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

}