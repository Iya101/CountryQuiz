
package edu.uga.cs.countryquiz;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class countryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_COUNTRIES = "countries";
    private static final String TABLE_NEIGHBORS = "neighbors";
    private static final String TABLE_QUIZZES = "quizzes";

    // Common column names
    private static final String COLUMN_ID = "_id";

    // Countries Table - column names
    private static final String COLUMN_COUNTRY_NAME = "name";
    private static final String COLUMN_CONTINENT = "continent";

    // Neighbors Table - column names
    private static final String COLUMN_COUNTRY_ID = "country_id";
    private static final String COLUMN_NEIGHBOR_ID = "neighbor_id";

    // Quizzes Table - column names
    private static final String COLUMN_QUIZ_DATE = "quiz_date";
    private static final String COLUMN_QUIZ_RESULT = "result";

    // Table Create Statements
    // Countries table create statement
    private static final String CREATE_TABLE_COUNTRIES = "CREATE TABLE "
            + TABLE_COUNTRIES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COUNTRY_NAME + " TEXT," + COLUMN_CONTINENT + " TEXT" + ")";

    // Neighbors table create statement
    private static final String CREATE_TABLE_NEIGHBORS = "CREATE TABLE "
            + TABLE_NEIGHBORS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COUNTRY_ID + " INTEGER," + COLUMN_NEIGHBOR_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_COUNTRY_ID + ") REFERENCES " + TABLE_COUNTRIES + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY(" + COLUMN_NEIGHBOR_ID + ") REFERENCES " + TABLE_COUNTRIES + "(" + COLUMN_ID + "))";

    // Quizzes table create statement
    private static final String CREATE_TABLE_QUIZZES = "CREATE TABLE "
            + TABLE_QUIZZES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUIZ_DATE + " TEXT," + COLUMN_QUIZ_RESULT + " INTEGER" + ")";

    public countryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_NEIGHBORS);
        db.execSQL(CREATE_TABLE_QUIZZES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEIGHBORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        // create new tables
        onCreate(db);
    }
}
