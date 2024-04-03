package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

    // Instance variables for UI components
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitAnswerButton;

    public CountryQuizFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of this fragment with question and options
    public static CountryQuizFragment newInstance(String countryName, ArrayList<String> options) {
        CountryQuizFragment fragment = new CountryQuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY_NAME, countryName); // New argument for country name
        args.putStringArrayList(ARG_OPTIONS, options);
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
        submitAnswerButton = view.findViewById(R.id.submitAnswerButton);

        // Setup question and options
        displayQuestion(view);

        // Handle submit button click
        submitAnswerButton.setOnClickListener(v -> {
            // Implement action on submit answer here
        });

        return view;
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


            // Assuming there are always exactly 4 options, otherwise check for null or options size
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));

        }
    }

    // You may need additional methods for handling answer submission and interaction with the Activity
}
