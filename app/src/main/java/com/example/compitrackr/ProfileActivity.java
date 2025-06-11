package com.example.compitrackr;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText, handleEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        nameEditText = findViewById(R.id.editTextName);
        handleEditText = findViewById(R.id.editTextHandle);
        saveButton = findViewById(R.id.buttonSaveProfile);

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // Load existing values
        nameEditText.setText(sharedPreferences.getString("name", ""));
        handleEditText.setText(sharedPreferences.getString("handle", ""));

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String handle = handleEditText.getText().toString();

            // Save data to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("handle", handle);
            editor.apply();

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        });
    }
}
