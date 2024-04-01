package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseInitializer {

    private final Context context;

    public DatabaseInitializer(Context context) {
        this.context = context.getApplicationContext();
    }

    public void execute() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            boolean success = initializeDatabase();

            handler.post(() -> {
                if (success) {
                    Toast.makeText(context, "Database initialized successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Database already initialized or failed to initialize.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean initializeDatabase() {
        CountryQuizData countryQuizData = new CountryQuizData(context);
        countryQuizData.open();

        try (InputStream in_s = context.getAssets().open("country_continent.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(in_s))) {
            String[] nextLine;
            boolean hasInserted = false;
            while ((nextLine = reader.readNext()) != null) {
                Country country = new Country(nextLine[0], nextLine[1]);
                countryQuizData.storeCountry(country);
                hasInserted = true;
            }
            return hasInserted; // True if at least one country was inserted
        } catch (Exception e) {
            Log.e("DatabaseInitializer", "Error loading CSV", e);
            return false; // Return false if an error occurred
        } finally {
            countryQuizData.close();
        }
    }
}

