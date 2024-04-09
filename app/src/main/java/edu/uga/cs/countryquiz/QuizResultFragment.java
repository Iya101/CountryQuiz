package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//delete? not using anymoreeee
public class QuizResultFragment extends Fragment {

    private static final String ARG_SCORE = "score";
    private static final String ARG_TOTAL_QUESTIONS = "totalQuestions";
    private CountryQuizData countryQuizData= null;

    public static QuizResultFragment newInstance(int score, int totalQuestions) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        args.putInt(ARG_TOTAL_QUESTIONS, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_quiz_result_fragment, container, false);
        TextView resultTextView = view.findViewById(R.id.resultTextView);

        if (getArguments() != null) {
            int score = getArguments().getInt(ARG_SCORE);
            int totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
            String resultText = "Score: " + score + "/" + totalQuestions;
            resultTextView.setText(resultText);
        }
        return view;
    }




}
