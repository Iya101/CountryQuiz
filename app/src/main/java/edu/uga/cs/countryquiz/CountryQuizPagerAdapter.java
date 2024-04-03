package edu.uga.cs.countryquiz;

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

        // Extract the question text and options
        String questionText = currentQuestion.getCountryName(); // Assuming this is what you want to display as the question
        ArrayList<String> options = new ArrayList<>(currentQuestion.getOptions()); // Convert List to ArrayList if necessary

        // Use the newInstance method with the updated parameters
        return CountryQuizFragment.newInstance(questionText, options);
    }

    @Override
    public int getItemCount() {
        return questions.size(); // The number of pages is determined by the number of questions
    }
}
