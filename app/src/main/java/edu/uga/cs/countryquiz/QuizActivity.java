package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private CountryQuizData countryQuizData;
    private TextView questionTextView;

    //Private CountryDBHelper dBHelper;
    private RadioGroup optionsRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        questionTextView =  findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        countryQuizData = new CountryQuizData(this);
        //List<String> allContinents = dbHelper.getAllContinents();

        countryQuizData.open();
        generateQuiz();

    }

    private void generateQuiz() {
        // Example pseudocode for fetching countries and generating questions
        List<Country> allCountries = countryQuizData.retrieveAllCountries();
        if (allCountries.isEmpty()) {
            Log.e("QuizActivity", "No countries found in the database.");
            // Handle the case when there are no countries, e.g., display a message to the user
            return;
        }
        Collections.shuffle(allCountries); // Now safe to shuffle

        Quiz newQuiz = new Quiz();
        for (int i = 0; i < 6; i++) {
            Country country = allCountries.get(i);

            // Copy of all continents to manipulate
            //
            // List<String> possibleContinents = new ArrayList<>(country.getContinent());
            List<String> possibleContinents = countryQuizData.getAllContinents();
            // Remove the correct continent to avoid selecting it as an incorrect option
            possibleContinents.remove(country.getContinent());

            // Shuffle and select the first two as incorrect options
            Collections.shuffle(possibleContinents);
            List<String> selectedOptions = possibleContinents.subList(0, 2);

            // Add the correct answer to the options
            selectedOptions.add(country.getContinent());

            // Ensure the options are shuffled so the correct answer isn't always last
            Collections.shuffle(selectedOptions);

            // Create a new Question object with these options
            Question question = new Question(country.getCountryName(), country.getContinent(), selectedOptions);
            newQuiz.addQuestion(question);
        }


        // Now display the first question
        displayQuestion(newQuiz.getQuestions().get(0));
    }

    private void displayQuestion(Question question) {
        // Update the question text view with the current question's text
        questionTextView.setText(question.getQuestionText());

        // Assuming the Question object has a method getOptions returning a List<String>
        // with the correct answer and incorrect options, and that you have exactly 4 RadioButtons for options
        List<String> options = question.getOptions();
        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            RadioButton optionButton = (RadioButton) optionsRadioGroup.getChildAt(i);
            // Set each RadioButton's text to each option
            // Ensure you have enough RadioButtons to match the number of options
            if (i < options.size()) {
                optionButton.setText(options.get(i));
                optionButton.setVisibility(View.VISIBLE);
            } else {
                // Hide any extra RadioButtons if there are fewer options than buttons
                optionButton.setVisibility(View.GONE);
            }
        }

        // Clear any previous selection in the RadioGroup
        optionsRadioGroup.clearCheck();
    }


}