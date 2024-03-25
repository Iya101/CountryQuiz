package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class countryDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "jobLeads.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_JOB_LEADS = "job_leads";

    // Column Names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_COMPANY_NAME = "company_name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_COMMENTS = "comments";

    // Creating table query
    private static final String CREATE_TABLE_JOB_LEADS = "CREATE TABLE "
            + TABLE_JOB_LEADS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COMPANY_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_URL + " TEXT,"
            + COLUMN_COMMENTS + " TEXT" + ")";

    public countryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_JOB_LEADS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database version upgrades here
    }
}

