package edu.uga.cs.countryquiz;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class countryDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "CountryQuizDBHelper";
    private static final String DATABASE_NAME = "quizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_COUNTRIES = "countries";
    private static final String TABLE_QUIZZES = "quizzes";

    // Common column names
    private static final String COLUMN_ID = "_id";

    // Countries Table - column names
    private static final String COLUMN_COUNTRY_NAME = "name";
    private static final String COLUMN_CONTINENT = "continent";

    // Quizzes Table - column names
    private static final String COLUMN_QUIZ_DATE = "quiz_date";
    private static final String COLUMN_QUIZ_RESULT = "result";

    private static countryDBHelper helperInstance;

    // A Create table SQL statement to create a table for country quiz
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE "
            + TABLE_COUNTRIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COUNTRY_NAME + " TEXT,"
            + COLUMN_CONTINENT + " TEXT" +
            ")";


    // Quizzes table create statement
    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE "
            + TABLE_QUIZZES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUIZ_DATE + " TEXT,"
            + COLUMN_QUIZ_RESULT + " INTEGER"
            + ")";

    public countryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_QUIZZES);
        Log.d( DEBUG_TAG, "Table " + CREATE_TABLE_COUNTRIES + " created" );

    Log.d( DEBUG_TAG, "Table " + CREATE_TABLE_QUIZZES + " created" );
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

