package com.abojabal.auto;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText wordsInput, speedInput;
    Switch fawarizSwitch, tatirSwitch, taklishSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsInput = findViewById(R.id.wordsInput);
        speedInput = findViewById(R.id.speedInput);
        fawarizSwitch = findViewById(R.id.fawarizSwitch);
        tatirSwitch = findViewById(R.id.tatirSwitch);
        taklishSwitch = findViewById(R.id.taklishSwitch);
        
        Button btnSelectKeyboard = findViewById(R.id.btnSelectKeyboard);
        Button saveButton = findViewById(R.id.saveButton);

        // تحميل الإعدادات المحفوظة
        wordsInput.setText(MyPrefs.getWords(this));
        speedInput.setText(String.valueOf(MyPrefs.getSpeed(this)));
        fawarizSwitch.setChecked(MyPrefs.isFawarizOn(this));
        tatirSwitch.setChecked(MyPrefs.isTatirOn(this));
        taklishSwitch.setChecked(MyPrefs.isTaklishOn(this));

        // زر اختيار الكيبورد
        btnSelectKeyboard.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showInputMethodPicker();
            }
        });

        // زر الحفظ
        saveButton.setOnClickListener(v -> {
            MyPrefs.saveWords(this, wordsInput.getText().toString());
            try {
                MyPrefs.saveSpeed(this, Integer.parseInt(speedInput.getText().toString()));
            } catch (Exception e) {
                MyPrefs.saveSpeed(this, 50);
            }
            MyPrefs.saveFawariz(this, fawarizSwitch.isChecked());
            MyPrefs.saveTatir(this, tatirSwitch.isChecked());
            MyPrefs.saveTaklish(this, taklishSwitch.isChecked());

            Toast.makeText(this, "تم حفظ الإعدادات بنجاح!", Toast.LENGTH_SHORT).show();
        });
    }
            }
