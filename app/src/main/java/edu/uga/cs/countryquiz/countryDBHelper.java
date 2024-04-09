package edu.uga.cs.countryquiz;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class countryDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "CountryQuizDBHelper";
    private static final String DATABASE_NAME = "quizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_QUIZZES = "quizzes";

    // COLUMN ID
    public static final String COLUMN_ID = "_id";

    // Countries column names
    public static final String COLUMN_COUNTRY_NAME = "name";
    public static final String COLUMN_CONTINENT = "continent";

    // Quizzes column names
    public static final String COLUMN_QUIZ_DATE = "quiz_date";
    public static final String COLUMN_QUIZ_RESULT = "result";

    private static countryDBHelper helperInstance; //helper instance

    // A Create table SQL statement to create a table for country quiz
    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE "
            + TABLE_COUNTRIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COUNTRY_NAME + " TEXT,"
            + COLUMN_CONTINENT + " TEXT" +
            ")";


    // Quizzes table creation
    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE "
            + TABLE_QUIZZES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUIZ_DATE + " TEXT,"
            + COLUMN_QUIZ_RESULT + " INTEGER"
            + ")";

    private countryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized countryDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new countryDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_QUIZZES);

        Log.d(DEBUG_TAG, "Table " + TABLE_COUNTRIES + " created");
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZZES + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        // create new tables
        onCreate(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES+ " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES+ " upgraded" );
    }

}