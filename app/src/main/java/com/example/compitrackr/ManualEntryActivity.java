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

public class ManualEntryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_manual_entry);

        // Match IDs from XML (initialize views first)
        contestNameEditText = findViewById(R.id.editTextContestName);
        rankEditText = findViewById(R.id.editTextRank);
        oldRatingEditText = findViewById(R.id.editTextOldRating);
        newRatingEditText = findViewById(R.id.editTextNewRating);
        problemsSolvedEditText = findViewById(R.id.editTextProblemsSolved);
        platformSpinner = findViewById(R.id.spinnerPlatform);
        saveButton = findViewById(R.id.buttonSave);

        // Optional: Setup platform spinner
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

        // After initializing the views, get extras and set values
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contestNameEditText.setText(extras.getString("contestName", ""));
            rankEditText.setText(String.valueOf(extras.getInt("rank", 0)));
            oldRatingEditText.setText(String.valueOf(extras.getInt("oldRating", 0)));
            newRatingEditText.setText(String.valueOf(extras.getInt("newRating", 0)));

            // Optional: if you added platform
            String platform = extras.getString("platform", "Codeforces");
            ArrayAdapter<String> platformAdapter = (ArrayAdapter<String>) platformSpinner.getAdapter();
            int spinnerPosition = platformAdapter.getPosition(platform);
            platformSpinner.setSelection(spinnerPosition);
        }

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

                    Toast.makeText(ManualEntryActivity.this, "Contest log saved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManualEntryActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ManualEntryActivity.this, "No logs found", Toast.LENGTH_SHORT).show();
                } else {
                    // Set up the adapter to show logs in RecyclerView
                    ContestLogAdapter adapter = new ContestLogAdapter(logs);
                    recyclerViewLogs.setAdapter(adapter);
                }
            }
        });
    }
}
