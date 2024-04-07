package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {
    private CountryQuizData countryQuizData;
    private TextView questionTextView;
    private ViewPager2 viewPager;
    private Quiz newQuiz;

    //Private CountryDBHelper dBHelper;
    private RadioGroup optionsRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe); // Confirm this is the correct layout with ViewPager2

        viewPager = findViewById(R.id.viewpager);
        countryQuizData = new CountryQuizData(this);
        countryQuizData.open();
        newQuiz = generateQuiz(); // Adjust generateQuiz to return a Quiz object
        countryQuizData.close();

        // Setup ViewPager with the quiz questions
        CountryQuizPagerAdapter adapter = new CountryQuizPagerAdapter(getSupportFragmentManager(), getLifecycle(), newQuiz.getQuestions());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);
    }

    private Quiz generateQuiz() {
        List<Country> allCountries = countryQuizData.retrieveAllCountries();
        if (allCountries.isEmpty()) {
            Log.e("QuizActivity", "No countries found in the database.");
            return null;
        }

        Set<Country> selectedCountries = new HashSet<>();
        Random random = new Random();
        while (selectedCountries.size() < 6) {
            int randomIndex = random.nextInt(allCountries.size());
            selectedCountries.add(allCountries.get(randomIndex));
        }

        newQuiz = new Quiz();

        for (Country country : selectedCountries) {
            List<String> continents = new ArrayList<>(countryQuizData.getAllContinents());
            continents.remove(country.getContinent());
            Collections.shuffle(continents);

            // Select two incorrect options
            List<String> selectedOptions = new ArrayList<>(continents.subList(0, 2));
            selectedOptions.add(country.getContinent());
            Collections.shuffle(selectedOptions);

            // Create a Question object
            Question question = new Question();
            question.setCountryName(country.getCountryName());
            question.setCorrectContinent(country.getContinent());
            question.setOptions(selectedOptions);

            // Add the question to the quiz
            newQuiz.addQuestion(question);
            String correctContinent = question.getCorrectContinent();
            Log.d("QuizActivity", "The correct continent for " + question.getCountryName() + " is " + correctContinent);
        }

        return newQuiz;
    }

    public void updateScore(String selectedAnswer, int questionIndex) {
        Question currentQuestion = newQuiz.getQuestions().get(questionIndex);
        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCorrectContinent());

        Log.d("QuizActivity", "Selected Answer: " + selectedAnswer + ", Correct Answer: " + currentQuestion.getCorrectContinent());
        if (isCorrect) {
            newQuiz.correctAnswer();
            Log.d("QuizActivity", "Answer is correct. Incrementing score.");
        } else {
            newQuiz.wrongAnswer();
            Log.d("QuizActivity", "Answer is wrong. Score remains the same.");
        }

        Log.d("QuizActivity", "Current Score: " + newQuiz.getQuizResult() + ", Questions Answered: " + newQuiz.getQuestionsAnswered());
        // Proceed to the next question or show results if this was the last question
        if (questionIndex == newQuiz.getQuestions().size() - 1) {
            Log.d("QuizActivity", "Last question answered. Showing quiz results.");
            new  StoreQuizResult(this).execute(newQuiz);
            showQuizResults();
        } else {
            // Code to navigate to the next question, e.g., by updating ViewPager2's current item
            Log.d("QuizActivity", "Moving to the next question.");
            viewPager.setCurrentItem(questionIndex + 1, true);
            Log.d("QuizActivity","question index:" + questionIndex);
        }
    }


    private void showQuizResults() {
        Intent intent = new Intent(this, QuizResultsActivity.class);
        intent.putExtra("score", newQuiz.getQuizResult());
        intent.putExtra("totalQuestions", newQuiz.getQuestions().size());
        startActivity(intent);

    }

    // AsyncTask for saving quiz results
    public class StoreQuizResult extends AsyncTask<Quiz, Void> {
        private CountryQuizData countryQuizData;

        public StoreQuizResult(Context context) {
            this.countryQuizData = new CountryQuizData(context);
        }

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            Quiz quiz = quizzes[0];
            countryQuizData.open();
            countryQuizData.storeQuiz(quiz);
            countryQuizData.close();
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {

            // Here, you might want to update UI or navigate to another Activity to show the quiz results.
            // Since AsyncTask runs on a background thread, remember any UI updates must be run on the UI thread.
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("CurrentQuestionIndex", viewPager.getCurrentItem());
        editor.putInt("Score", newQuiz.getQuizResult());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        int savedIndex = prefs.getInt("CurrentQuestionIndex", -1);
        int savedScore = prefs.getInt("Score", 0);
        if (savedIndex != -1) {
            viewPager.setCurrentItem(savedIndex);
            newQuiz.setQuizResult(savedScore);
            // Add any additional logic needed to restore the quiz state
        }
    }

}
