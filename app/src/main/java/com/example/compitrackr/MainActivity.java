package com.example.compitrackr;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    // UI components
    private EditText contestNameEditText, rankEditText, oldRatingEditText, newRatingEditText, problemsSolvedEditText;
    private Spinner platformSpinner;
    private Button saveButton;

    // Database helper instance (you’ll need to create this class separately)
    private DatabaseHelper databaseHelper;

    // RecyclerView and viewLogsButton
    private RecyclerView recyclerViewLogs;
    private Button viewLogsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Match IDs from XML
        contestNameEditText = findViewById(R.id.editTextContestName);
        rankEditText = findViewById(R.id.editTextRank);
        oldRatingEditText = findViewById(R.id.editTextOldRating);
        newRatingEditText = findViewById(R.id.editTextNewRating);
        problemsSolvedEditText = findViewById(R.id.editTextProblemsSolved);
        platformSpinner = findViewById(R.id.spinnerPlatform);
        saveButton = findViewById(R.id.buttonSave);

        // Setup platform spinner with values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.platforms_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platformSpinner.setAdapter(adapter);

        // Initialize DatabaseHelper (you'll build this)
        databaseHelper = new DatabaseHelper(this);

        // Initialize RecyclerView
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(this));

        // Save button action
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String contestName = contestNameEditText.getText().toString();
                    int rank = Integer.parseInt(rankEditText.getText().toString());
                    int oldRating = Integer.parseInt(oldRatingEditText.getText().toString());
                    int newRating = Integer.parseInt(newRatingEditText.getText().toString());
                    int problemsSolved = Integer.parseInt(problemsSolvedEditText.getText().toString());
                    String platform = platformSpinner.getSelectedItem().toString();

                    // Create ContestLog object
                    ContestLog contestLog = new ContestLog(platform, contestName, new Date(), rank, oldRating, newRating, problemsSolved);

                    // Save to database
                    databaseHelper.insertContestLog(contestLog);

                    Toast.makeText(MainActivity.this, "Contest log saved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // View Logs button action
        viewLogsButton = findViewById(R.id.buttonViewLogs);
        viewLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContestLog> logs = databaseHelper.getAllContestLogs();

                if (logs.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No logs found", Toast.LENGTH_SHORT).show();
                } else {
                    // Set up the adapter to show logs in RecyclerView
                    ContestLogAdapter adapter = new ContestLogAdapter(logs);
                    recyclerViewLogs.setAdapter(adapter);
                }
            }
        });
    }
}
