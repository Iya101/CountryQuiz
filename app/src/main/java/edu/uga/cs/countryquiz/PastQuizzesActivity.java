package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class PastQuizzesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_quizzes);

        RecyclerView recyclerView = findViewById(R.id.pastQuizzesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Assuming you have a method in CountryQuizData to retrieve past quizzes
        CountryQuizData countryQuizData = new CountryQuizData(this);
        countryQuizData.open();
        List<QuizResult> pastQuizzes = countryQuizData.retrievePastQuizzes();
        countryQuizData.close();

        // Assuming you have a QuizResultsAdapter for your RecyclerView
        QuizResultsAdapter adapter = new QuizResultsAdapter(pastQuizzes);
        recyclerView.setAdapter(adapter);
    }
}
