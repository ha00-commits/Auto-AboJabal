package com.abojabal.auto;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText wordsInput = findViewById(R.id.wordsInput);
        EditText speedInput = findViewById(R.id.speedInput);
        Switch fawarizSwitch = findViewById(R.id.fawarizSwitch);
        Switch tatirSwitch = findViewById(R.id.tatirSwitch);
        Button btnSelect = findViewById(R.id.btnSelectKeyboard);
        Button saveBtn = findViewById(R.id.saveButton);

        wordsInput.setText(MyPrefs.getWords(this));
        speedInput.setText(String.valueOf(MyPrefs.getSpeed(this)));
        fawarizSwitch.setChecked(MyPrefs.isFawarizOn(this));
        tatirSwitch.setChecked(MyPrefs.isTatirOn(this));

        btnSelect.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) imm.showInputMethodPicker();
        });

        saveBtn.setOnClickListener(v -> {
            MyPrefs.saveWords(this, wordsInput.getText().toString());
            try { MyPrefs.saveSpeed(this, Integer.parseInt(speedInput.getText().toString())); }
            catch (Exception e) { MyPrefs.saveSpeed(this, 50); }
            MyPrefs.saveFawariz(this, fawarizSwitch.isChecked());
            MyPrefs.saveTatir(this, tatirSwitch.isChecked());
            Toast.makeText(this, "تم الحفظ!", Toast.LENGTH_SHORT).show();
        });
    }
    }
