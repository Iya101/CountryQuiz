package edu.uga.cs.countryquiz;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CountryQuizPagerAdapter extends FragmentStateAdapter {
    private final List<Question> questions;


    public CountryQuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Question> questions) {
        super(fragmentManager, lifecycle);
        this.questions = questions;
    }

    @Override
    public Fragment createFragment(int position) {
        // Fetch the current question based on the position
        Question currentQuestion = questions.get(position);
        // Log to track fragment creation for each question
        Log.d("CountryQuizPagerAdapter", "Creating fragment for question at position: " + position);

        // Extract the question text and options
        String questionText = currentQuestion.getCountryName();
        ArrayList<String> options = new ArrayList<>(currentQuestion.getOptions());

        // Use the newInstance method with the updated parameters
        return CountryQuizFragment.newInstance(questionText, options, position);
    }

    @Override
    public int getItemCount() {
        return questions.size(); // The number of pages is determined by the number of questions
    }
}
