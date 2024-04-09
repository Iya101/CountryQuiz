package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button to start quiz
        Button startQuizButton = findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        // Button to view past quizzes
        Button viewPastQuizzesButton = findViewById(R.id.displayRankingButton);
        viewPastQuizzesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PastQuizzesActivity.class);
            startActivity(intent);
        });

        new DatabaseInitializer(this).execute(); //load database from csv
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Clear any saved quiz state to ensure starting fresh for a new quiz
        getSharedPreferences("QuizPrefs", MODE_PRIVATE).edit().clear().apply();
    }

}