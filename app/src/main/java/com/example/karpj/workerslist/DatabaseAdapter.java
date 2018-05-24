package com.example.karpj.workerslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FIRSTNAME, DatabaseHelper.COLUMN_LASTNAME, DatabaseHelper.COLUMN_SEX, DatabaseHelper.COLUMN_DATE};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Person> getPersons() throws ParseException {
        ArrayList<Person> persons = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));
                int sex_ = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SEX));
                boolean sex = (sex_ != 0);
                String date_ = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                Date date = new Date(date_);
                //Date date = new SimpleDateFormat("dd-MM-yyyy").parse(date_);
                persons.add(new Person(id, firstName, lastName, sex, date ));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  persons;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Person getPerson(int id) throws ParseException {
        Person person = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));
            int sex_ = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SEX));
            String date_ = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            boolean sex = (sex_ != 0);
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(date_);
            person = new Person(id, firstName, lastName, sex, date);
        }
        cursor.close();
        return  person;
    }

    public long insert(Person person){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FIRSTNAME, person.getFirstName());
        cv.put(DatabaseHelper.COLUMN_LASTNAME, person.getLastName());
        cv.put(DatabaseHelper.COLUMN_SEX,person.isSex());
        cv.put(DatabaseHelper.COLUMN_DATE, person.getDate().toString());
        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(int personId){

        String whereClause = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(personId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Person person){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(person.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FIRSTNAME, person.getFirstName());
        cv.put(DatabaseHelper.COLUMN_LASTNAME, person.getLastName());
        cv.put(DatabaseHelper.COLUMN_SEX, person.isSex());
        cv.put(DatabaseHelper.COLUMN_DATE, person.getDate().toString());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}

