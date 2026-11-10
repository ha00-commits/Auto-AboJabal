package com.abojabal.auto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText wordsInput, speedInput;
    Switch fawarizSwitch;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsInput = findViewById(R.id.wordsInput);
        speedInput = findViewById(R.id.speedInput);
        fawarizSwitch = findViewById(R.id.fawarizSwitch);
        startButton = findViewById(R.id.startButton);

        // تحميل الإعدادات المحفوظة (عشان ما تنحذف)
        wordsInput.setText(MyPrefs.getWords(this));
        speedInput.setText(String.valueOf(MyPrefs.getSpeed(this)));
        fawarizSwitch.setChecked(MyPrefs.isFawarizOn(this));

        startButton.setOnClickListener(v -> {
            // حفظ الإعدادات قبل التشغيل
            MyPrefs.saveWords(this, wordsInput.getText().toString());
            MyPrefs.saveSpeed(this, Integer.parseInt(speedInput.getText().toString().isEmpty() ? "50" : speedInput.getText().toString()));
            MyPrefs.saveFawariz(this, fawarizSwitch.isChecked());

            // طلب صلاحية الكتابة فوق الشاشة
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "الأوتو جاهز! اضغط زر الصوت مرتين للتشغيل", Toast.LENGTH_LONG).show();
                // تشغيل الخدمة
                startService(new Intent(this, AutoService.class));
            }
        });
    }
}
