package edu.uga.cs.countryquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizResultsAdapter extends RecyclerView.Adapter<QuizResultsAdapter.ViewHolder> {

    private List<QuizResult> quizResults;

    // Constructor
    public QuizResultsAdapter(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView quizDateView;
        private final TextView quizScoreView;

        public ViewHolder(View view) {
            super(view);
            // Initialize your layout views
            quizDateView = view.findViewById(R.id.quizDate);
            quizScoreView = view.findViewById(R.id.quizScore);
        }

        public TextView getQuizDateView() { return quizDateView; }
        public TextView getQuizScoreView() { return quizScoreView; }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_result_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from your dataset at this position
        // Replace the contents of the view with that element
        QuizResult quizResult = quizResults.get(position);
        holder.getQuizDateView().setText(quizResult.getQuizDate());
        holder.getQuizScoreView().setText(String.valueOf(quizResult.getQuizScore()));
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return quizResults.size();
    }
}

