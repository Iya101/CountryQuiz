package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class CountryQuizFragment extends Fragment {

    // Define keys for argument bundle
    private static final String ARG_COUNTRY_NAME = "countryName";
    private static final String ARG_OPTIONS = "options";
    private static final String ARG_QUESTION_INDEX = "questionIndex";

    // Instance variables for UI components
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;


    public CountryQuizFragment() {
        // Required empty public constructor
    }

    public static CountryQuizFragment newInstance(String countryName, ArrayList<String> options, int questionIndex) {
        CountryQuizFragment fragment = new CountryQuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY_NAME, countryName);
        args.putStringArrayList(ARG_OPTIONS, options);
        args.putInt(ARG_QUESTION_INDEX, questionIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_quiz, container, false);

        // Initialize UI components
        questionTextView = view.findViewById(R.id.questionTextView);
        optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup);

        displayQuestion(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int questionIndex = getArguments().getInt(ARG_QUESTION_INDEX);

        // Set the listener for the radio group to detect answer selections
        optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedOption = view.findViewById(checkedId);
            if (selectedOption != null) {
                String selectedAnswer = selectedOption.getText().toString();
                Log.d("QuizFragment", "Answer selected for question index " + questionIndex + ": " + selectedAnswer);

                // Cast getActivity() to QuizActivity and call updateScore
                if (getActivity() instanceof QuizActivity) {
                    ((QuizActivity) getActivity()).updateScore(selectedAnswer, questionIndex);
                }
            }
        });
    }

    private void displayQuestion(View view) {
        if (getArguments() != null) {
            String countryName = getArguments().getString(ARG_COUNTRY_NAME);
            ArrayList<String> options = getArguments().getStringArrayList(ARG_OPTIONS);

            // Construct question text with the country name
            String questionText = "Which continent does " + countryName + " belong to?";
            questionTextView.setText(questionText);

            // Retrieve RadioButtons by their IDs and set options text
            RadioButton option1 = view.findViewById(R.id.option1RadioButton);
            RadioButton option2 = view.findViewById(R.id.option2RadioButton);
            RadioButton option3 = view.findViewById(R.id.option3RadioButton);


            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));

        }
    }



}
