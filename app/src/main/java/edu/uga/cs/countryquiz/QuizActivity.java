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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        // Retrieve all countries from the database
        List<Country> allCountries = countryQuizData.retrieveAllCountries();
        if (allCountries.isEmpty()) {
            Log.e("QuizActivity", "No countries found in the database.");
            // Consider showing a message to the user
            return;
        }

        // Use a Set to ensure unique countries are selected
        Set<Country> selectedCountries = new HashSet<>();
        Random random = new Random();
        while (selectedCountries.size() < 6) {
            int randomIndex = random.nextInt(allCountries.size());
            selectedCountries.add(allCountries.get(randomIndex));
        }

        // Initialize a new Quiz
        Quiz newQuiz = new Quiz();

        // For each selected country, prepare a question
        for (Country country : selectedCountries) {
            List<String> continents = countryQuizData.getAllContinents(); // Assume this gets all possible continents
            continents.remove(country.getContinent()); // Remove the correct answer
            Collections.shuffle(continents); // Shuffle the remaining continents

            // Select two incorrect options
            List<String> selectedOptions = continents.subList(0, 2);
            selectedOptions.add(country.getContinent()); // Add the correct answer
            Collections.shuffle(selectedOptions); // Shuffle to randomize positions

            // Create and add the question to the quiz
            Question question = new Question(country.getCountryName(), country.getContinent(), selectedOptions);
            newQuiz.addQuestion(question);
        }

        // Assuming displayQuestion() is properly implemented to show a question and its options
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