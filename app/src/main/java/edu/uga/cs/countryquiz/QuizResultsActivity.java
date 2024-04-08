package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class QuizResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the score and total questions from the intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Find the TextView by its ID and update its text to show the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        String resultsText = "You scored " + score + " out of " + totalQuestions + "!";
        scoreTextView.setText(resultsText);



        Button seePastQuizzesButton = findViewById(R.id.seePastQuizzesButton);
        seePastQuizzesButton.setOnClickListener(view -> {
            // Navigate to the PastQuizzesActivity
            Intent intent = new Intent(QuizResultsActivity.this, PastQuizzesActivity.class);
            startActivity(intent);
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}