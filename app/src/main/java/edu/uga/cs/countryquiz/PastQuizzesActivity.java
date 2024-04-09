package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PastQuizzesActivity extends AppCompatActivity {
    private CountryQuizData countryQuizData;
    private RecyclerView recyclerView;
    public static final String DEBUG_TAG = "PastQuizzesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_quizzes);

        recyclerView = findViewById(R.id.pastQuizzesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database variable
        countryQuizData = new CountryQuizData(this);

        //up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Execute the AsyncTask to load quizzes from the database
        new QuizDBReader().execute();
    }

    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        @Override
        protected List<Quiz> doInBackground(Void... params) {
            countryQuizData.open();
            Log.d(DEBUG_TAG, "Database opened, now retrieving past quizzes.");
            List<Quiz> pastQuizzes = countryQuizData.retrievePastQuizzes();
            countryQuizData.close();
            return pastQuizzes;
        }

        @Override
        protected void onPostExecute(List<Quiz> pastQuizzes) {
            //super.onPostExecute(pastQuizzes);
            Log.d(DEBUG_TAG, "Fetched past quizzes. Size: " + pastQuizzes.size());
            // Now set the adapter with the fetched quizzes
            QuizResultsAdapter adapter = new QuizResultsAdapter(pastQuizzes);
            recyclerView.setAdapter(adapter);
        }
    }
}

