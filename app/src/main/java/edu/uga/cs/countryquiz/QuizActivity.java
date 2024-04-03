package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

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
        Quiz newQuiz = generateQuiz(); // Adjust generateQuiz to return a Quiz object
        countryQuizData.close();

        // Setup ViewPager with the quiz questions
        CountryQuizPagerAdapter adapter = new CountryQuizPagerAdapter(getSupportFragmentManager(), getLifecycle(), newQuiz.getQuestions());
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

        Quiz newQuiz = new Quiz(); // Assuming Quiz class has a proper constructor

        for (Country country : selectedCountries) {
            List<String> continents = new ArrayList<>(countryQuizData.getAllContinents());
            continents.remove(country.getContinent());
            Collections.shuffle(continents);


            // Select two incorrect options
            List<String> selectedOptions = new ArrayList<>(continents.subList(0, 2));
            selectedOptions.add(country.getContinent());
            Collections.shuffle(selectedOptions);

            // Create and add the question to the quiz
            Question question = new Question(country.getCountryName(), country.getContinent(), selectedOptions);
            newQuiz.addQuestion(question); // add Question into quiz
        }

        return newQuiz;
    }




}