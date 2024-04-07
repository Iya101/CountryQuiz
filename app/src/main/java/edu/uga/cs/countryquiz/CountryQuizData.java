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

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
    public class QuizAnswerDBWriter extends AsyncTask<Quiz, Quiz> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Quiz doInBackground( Quiz... quizzes ) {
            //CountryQuizData.storeQuiz( quizzes[0] );
            return quizzes[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( Quiz quiz ) {

            //the system should record the user’s answer to
            //the currently shown question.


            //jobLeadsList.add( jobLead );

            // Sync the originalValues list in the recycler adapter to the new updated list (JoLeadsList)
            //recyclerAdapter.sync();

            // Notify the adapter that an item has been inserted
            //recyclerAdapter.notifyItemInserted(jobLeadsList.size() - 1 );


           // Log.d( TAG, "lead saved: " + jobLead );

        }
    }

    public class StoreQuizResult extends AsyncTask<Quiz, Void> {
        private CountryQuizData countryQuizData;

        public StoreQuizResult(Context context) {
            this.countryQuizData = new CountryQuizData(context);
        }

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            countryQuizData.open();
            countryQuizData.storeQuiz(quizzes[0]);
            countryQuizData.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // Navigate to the results screen or update the UI as necessary
        }
    }


    public List<Country> retrieveAllCountries() {
        List<Country> countries = new ArrayList<>();
        Cursor cursor = db.query(countryDBHelper.TABLE_COUNTRIES,allColumnsCountries,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_COUNTRY_NAME);
                int continentIndex = cursor.getColumnIndex(countryDBHelper.COLUMN_CONTINENT);

                if (idIndex != -1 && nameIndex != -1 && continentIndex != -1) {
                    long id = cursor.getLong(idIndex);
                    String countryName = cursor.getString(nameIndex);
                    String continent = cursor.getString(continentIndex);

                    Country country = new Country(countryName, continent);
                    country.setId(id);
                    countries.add(country);
                    Log.d(DEBUG_TAG, "Retrieved Country: " + country);
                }
            } while (cursor.moveToNext());
        } else {
            Log.e(DEBUG_TAG, "No countries found in the database.");
        }
        cursor.close();
        return countries;
    }


    public List<String> getAllContinents() {
        List<String> continents = new ArrayList<>();

        // Corrected: Specify the table name and only the continent column.
        Cursor cursor = db.query(countryDBHelper.TABLE_COUNTRIES,
                new String[]{countryDBHelper.COLUMN_CONTINENT}, // Only fetch the continent column
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Directly fetch the continent from each row without checking column count
                String continent = cursor.getString(cursor.getColumnIndex(countryDBHelper.COLUMN_CONTINENT));
                if (!continents.contains(continent)) {
                    continents.add(continent); // Add unique continents to the list
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
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

    public Quiz storeQuiz( Quiz quiz) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();

        values.put( countryDBHelper.COLUMN_QUIZ_DATE, quiz.getQuizDate());
        values.put( countryDBHelper.COLUMN_QUIZ_RESULT, quiz.getQuizResult());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( countryDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored quiz with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }


    //maybe delete? idk

    public List<QuizResult> retrievePastQuizzes() {
        List<QuizResult> quizResults = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Open the database for reading
            db = countryDbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    countryDBHelper.COLUMN_ID,
                    countryDBHelper.COLUMN_QUIZ_DATE,
                    countryDBHelper.COLUMN_QUIZ_RESULT
            };

            // Perform a query on the quizzes table
            cursor = db.query(
                    countryDBHelper.TABLE_QUIZZES,   // The table to query
                    projection,             // The columns to return
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );

            while (cursor.moveToNext()) {
                long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(countryDBHelper.COLUMN_ID));
                String quizDate = cursor.getString(cursor.getColumnIndexOrThrow(countryDBHelper.COLUMN_QUIZ_DATE));
                int quizResult = cursor.getInt(cursor.getColumnIndexOrThrow(countryDBHelper.COLUMN_QUIZ_RESULT));
                QuizResult result = new QuizResult(itemId, quizDate, quizResult);
                quizResults.add(result);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return quizResults;
    }

}