package com.example.pustikom.adapterplay.com.example.pustikom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import com.example.pustikom.adapterplay.R;
import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

/**
 * Created by Sony Center on 11-Nov-16.
 */

public class StudentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "college.db";
    private static final int DATABASE_VERSION=1;

    public StudentDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE " + StudentContract.TABLE_NAME + " " +
                StudentContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentContract.COLUMN_STUDENT_NIM + "TEXT NOT NULL," +
                StudentContract.COLUMN_STUDENT_NAME + "TEXT NOT NULL," +
                StudentContract.COLUMN_STUDENT_GENDER + "INTEGER," +
                StudentContract.COLUMN_STUDENT_MAIL + "TEXT," +
                StudentContract.COLUMN_STUDENT_PHONE + "TEXT";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }

    public void insertStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_STUDENT_NIM,student.getNoreg());
        values.put(StudentContract.COLUMN_STUDENT_NAME,student.getName());
        values.put(StudentContract.COLUMN_STUDENT_GENDER,student.getGender());
        values.put(StudentContract.COLUMN_STUDENT_MAIL,student.getMail());
        values.put(StudentContract.COLUMN_STUDENT_PHONE,student.getPhone());
        db.insert(StudentContract.TABLE_NAME, null, values);
    }

    public void updateStudent(SQLiteDatabase wdb, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_STUDENT_NIM,student.getNoreg());
        values.put(StudentContract.COLUMN_STUDENT_NAME,student.getName());
        values.put(StudentContract.COLUMN_STUDENT_GENDER,student.getGender());
        values.put(StudentContract.COLUMN_STUDENT_MAIL,student.getMail());
        values.put(StudentContract.COLUMN_STUDENT_PHONE,student.getPhone());
        String condition = StudentContract._ID + "= ? , ";
        String[] conditionArgs = {student.getId() + ""};
        wdb.update(StudentContract.TABLE_NAME, values, condition, conditionArgs);
    }

    public void readStudent(SQLiteDatabase rdb, Student student){
        String[] projection = {StudentContract._ID,
                StudentContract.COLUMN_STUDENT_NIM,
                StudentContract.COLUMN_STUDENT_NAME,
                StudentContract.COLUMN_STUDENT_GENDER,
                StudentContract.COLUMN_STUDENT_MAIL,
                StudentContract.COLUMN_STUDENT_PHONE};
        Cursor cursor = rdb.query(StudentContract.TABLE_NAME, projection, null, null, null, null, null);

        int id = cursor.getColumnIndex(StudentContract._ID);
        int nim = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_NIM);
        int name = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_NAME);
        int gender = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_GENDER);
        int mail = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_MAIL);
        int phone = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_PHONE);

        // Iterate through all the returned rows in the cursor
        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(id);
            int currentNim = cursor.getInt(nim);
            String currentName = cursor.getString(name);
            int currentGender = cursor.getInt(gender);
            String currentMail = cursor.getString(mail);
            String currentPhone = cursor.getString(phone);
        }

        cursor.moveToFirst();
        cursor.getLong(cursor.getColumnIndexOrThrow(StudentContract._ID));

    }
    public void deleteStudent(SQLiteDatabase deletedb, Student student){
        String selection = StudentContract.TABLE_NAME + " LIKE ?";
        String[] selectionArgs = { student.getId() + "" };
        deletedb.delete(StudentContract.TABLE_NAME, selection, selectionArgs);
    }
}
