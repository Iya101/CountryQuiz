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

        //up button back to main screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the score and total questions from the intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Find the TextView by its ID and update its text to show the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        String resultsText = "You scored " + score + " out of " + totalQuestions + "!";
        scoreTextView.setText(resultsText);


        // see past quizzes button
        Button seePastQuizzesButton = findViewById(R.id.seePastQuizzesButton);
        seePastQuizzesButton.setOnClickListener(view -> {
            // Navigate to the PastQuizzesActivity
            Intent intent = new Intent(QuizResultsActivity.this, PastQuizzesActivity.class);
            startActivity(intent);
        });


    }


}