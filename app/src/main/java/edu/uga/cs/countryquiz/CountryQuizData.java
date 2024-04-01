package edu.uga.cs.countryquiz;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class CountryQuizData {

    public static final String DEBUG_TAG = "CountryQuizData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase db;
    private Cursor cursor = null;
    private SQLiteOpenHelper countryDbHelper;

    private static final String[] allColumns = {
            countryDBHelper.COLUMN_ID,
            countryDBHelper.COLUMN_COUNTRY_NAME,
            countryDBHelper.COLUMN_CONTINENT,
            countryDBHelper.COLUMN_QUIZ_DATE,
            countryDBHelper.COLUMN_QUIZ_RESULT,
    };

    private static final String[] allColumnsCountries = {
            countryDBHelper.COLUMN_ID,
            countryDBHelper.COLUMN_COUNTRY_NAME,
            countryDBHelper.COLUMN_CONTINENT
    };

    private static final String[] allColumnsQuizzes = {
            countryDBHelper.COLUMN_ID,
            countryDBHelper.COLUMN_QUIZ_DATE,
            countryDBHelper.COLUMN_QUIZ_RESULT
    };



    public CountryQuizData(Context context) {
        this.countryDbHelper = countryDBHelper.getInstance(context);
    }

    public void open() {
        db = countryDbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "CountryQuizData: db open");
    }

    public void close() {
        if (countryDbHelper != null) {
            countryDbHelper.close();
            Log.d(DEBUG_TAG, "CountryQuizData: db closed");
        }
    }

    public boolean isDBOpen() {
        return db.isOpen();
    }

    public List<Country> retrieveAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        //Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query(countryDBHelper.TABLE_COUNTRIES, allColumnsCountries,
                    null, null, null, null, null);


            // collect all job leads into a List
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_ID);
                        long id = cursor.getLong(columnIndex);
                        columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_COUNTRY_NAME);
                        String countryName = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_CONTINENT);
                        String continent = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_QUIZ_DATE);
                        String quizDate = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_QUIZ_RESULT);
                        String quizResult = cursor.getString(columnIndex);


                        // create a new JobLead object and set its state to the retrieved values
                        Country country = new Country(countryName, continent);
                        country.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        countries.add(country);
                        Log.d(DEBUG_TAG, "Retrieved Country: " + country);
                    }
                }
            }
            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved countries
        return countries;
    }

    public List<String> getAllContinents() {
        List<String> continents = new ArrayList<>();
        int columnIndex;
        cursor = db.query(countryDBHelper.COLUMN_CONTINENT, allColumns,
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                if (cursor.getColumnCount() >= 5) {
                    columnIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_CONTINENT);
                    String continent = cursor.getString(columnIndex);
                    continents.add(continent);
                }
            }
        }



        if(cursor != null) {
            cursor.close();
        }
        //db.close();
        return continents;
    }



    // Store a new job lead in the database.
    public Country storeCountry( Country country) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put( countryDBHelper.COLUMN_COUNTRY_NAME, country.getCountryName());
        values.put( countryDBHelper.COLUMN_CONTINENT, country.getContinent());
       // values.put( countryDBHelper.COLUMN_QUIZ_DATE, country.getPhone());
        //values.put( countryDBHelper.COLUMN_QUIZ_RESULT, country.getUrl());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( countryDBHelper.TABLE_COUNTRIES, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        country.setId( id );

        Log.d( DEBUG_TAG, "Stored country with id: " + String.valueOf( country.getId() ) );

        return country;
    }
}
