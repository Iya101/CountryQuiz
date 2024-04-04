package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QuizResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        // Retrieve the score and total questions from the intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Find the TextView by its ID and update its text to show the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        String resultsText = "You scored " + score + " out of " + totalQuestions + "!";
        scoreTextView.setText(resultsText);
    }
}