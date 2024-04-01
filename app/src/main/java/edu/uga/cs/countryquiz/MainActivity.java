package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rankingButton = findViewById( R.id.displayRankingButton );
        rankingButton.setOnClickListener(new ButtonClickListener());

        Button startQuizButton = findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                // Open the CSV data file in the assets folder
                InputStream in_s = getAssets().open( "country_continent.csv" );

                // get the TableLayout view
                TableLayout tableLayout = findViewById(R.id.table_main);

                // set up margins for each TextView in the table layout
                android.widget.TableRow.LayoutParams layoutParams =
                        new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT );
                layoutParams.setMargins(20, 0, 20, 0);

                // read the CSV data
                CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
                String[] nextRow;
                while( ( nextRow = reader.readNext() ) != null ) {

                    // nextRow[] is an array of values from the line

                    // create the next table row for the layout
                    TableRow tableRow = new TableRow( getBaseContext() );
                    for( int i = 0; i < nextRow.length; i++ ) {

                        // create a new TextView and set its text
                        TextView textView = new TextView( getBaseContext() );
                        // for all columns exept the SCHOOL, align right
                        if( i != 1 )
                            textView.setGravity(Gravity.RIGHT);
                        textView.setText( nextRow[i] );

                        // add the new TextView to the table row in the table supplying the
                        // layout parameters
                        tableRow.addView( textView, layoutParams );
                    }

                    // add the next row to the table layout
                    tableLayout.addView( tableRow );
                }
            } catch (Exception e) {
                Log.e( TAG, e.toString() );
            }
        }
    }
}