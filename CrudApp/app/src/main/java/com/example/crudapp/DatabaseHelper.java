package com.example.crudapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Employees.db";
    private static final String TABLE_NAME = "Employees";

    public DatabaseHelper(Context context)
    {
        //We will use this to create the database
        //it accepts the context, database name, factory (leave null), and version number
        //if your database becomes corrupts or the information in the database is wrong
        //change the version number
        //super is used to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended (DatabaseHelper)

        super(context, DATABASE_NAME, null, 3); //change version here to change table
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table in the database
        //execute the sql statement on the database that was passed to the function onCreate called db

        //username is primary key
        //age is an INT, all else string/TEXT
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (firstname TEXT, lastname TEXT, username TEXT PRIMARY KEY NOT NULL, password TEXT, email TEXT, age INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop current table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");

        //create a new table once the old table has been deleted.
        onCreate(db);
    }

    public boolean initializeDefaultInfo(){
        if(numberOfRowsInTable() == 0){
            //get writable db
            SQLiteDatabase db = this.getWritableDatabase();

            //add dummy data
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Bradley','Hanson','goblinman617','supercool','bhanson@gmail.com','22');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('Mitchell','Brobbins','wilfrey','bhopLegend231','wilfdog101@gmail.com','21');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES('John','Charles','JC9213','JRPGLOVER9213','JC23FootballDog@hotmail.com','44');");

            db.close(); //#neverForget (to close the database)



            return true;
        }else{
            return false;

        }
    }

    public int numberOfRowsInTable(){
        //get readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        db.close();

        return numRows;
    }

    //needed for something
    @SuppressLint("Range")
    public ArrayList<Employee> getAllRows() {
        ArrayList<Employee> employeeList = new ArrayList<>();

        //query to get all rows and attributes from our table
        //select * means get all attributes
        //order by username
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY username;";

        //get an instance of a readable database and store it in db
        SQLiteDatabase db = this.getReadableDatabase();

        //exec the query. Cursor will be used to cycle through the results
        Cursor cursor = db.rawQuery(selectQuery, null);

        //used to store attributes
        String fname;
        String lname;
        String uname;
        String pword;
        String email;
        int age;

        if(cursor.moveToFirst()) {
            do {
                fname = cursor.getString(cursor.getColumnIndex("firstname"));

                lname = cursor.getString(cursor.getColumnIndex("lastname"));

                uname = cursor.getString(cursor.getColumnIndex("username"));

                pword = cursor.getString(cursor.getColumnIndex("password"));

                email = cursor.getString(cursor.getColumnIndex("email"));

                age = cursor.getInt(cursor.getColumnIndex("age"));

                //add the returned results to my list
                employeeList.add(new Employee(fname,lname,uname,pword,email,age));
            }
            while(cursor.moveToNext()); //while you did move to next entry
        }

        //close db
        db.close();
        //return the created list of users sorted by username
        return employeeList;
    }

    public void addEmployee(Employee e){
        SQLiteDatabase db = this.getWritableDatabase();

        //pretty sure this is right. double check later if it fricks up
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES ('" + e.getFirstname() + "','" + e.getLastname() + "','" + e.getUsername() + "','"
                + e.getPassword() + "','" + e.getEmail() + "','" + e.getAge() + "');");

        db.close();//why is this not in example??? reported
    }

    public void deleteEmployee(String uname){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE username = '" + uname + "';");

        db.close();
    }

    public void updateEmployee(Employee e){
        SQLiteDatabase db = this.getWritableDatabase();

        //SET all values WHERE username = username
        db.execSQL("UPDATE " + TABLE_NAME + " SET firstname = '" + e.getFirstname() + "', lastname = '" + e.getLastname() + "', password = '"
                + e.getPassword() + "', email = '" + e.getEmail() + "', age = '" + e.getAge() + "' WHERE username = '" + e.getUsername() + "';");

        db.close();
    }
}
