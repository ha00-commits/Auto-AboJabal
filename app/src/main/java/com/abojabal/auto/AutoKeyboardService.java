package com.abojabal.auto;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import java.util.Random;

public class AutoKeyboardService extends InputMethodService {

    private boolean isTyping = false;
    private String[] words;
    private int currentIndex = 0;
    private int speed = 50;
    private boolean fawariz = false;
    private boolean tatir = false;
    private boolean taklish = false;
    private Handler handler = new Handler(Looper.getMainLooper());
    private InputConnection inputConnection;

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.keyboard_view, null);
        
        loadSettings();

        // ربط الحروف
        int[] letterIds = {
            R.id.btn_dad, R.id.btn_sad, R.id.btn_thaa, R.id.btn_qaf, R.id.btn_faa,
            R.id.btn_ghain, R.id.btn_ain, R.id.btn_haa, R.id.btn_khaa, R.id.btn_haa2,
            R.id.btn_sheen, R.id.btn_seen, R.id.btn_yaa, R.id.btn_baa, R.id.btn_lam,
            R.id.btn_alif, R.id.btn_taa, R.id.btn_noon, R.id.btn_meem, R.id.btn_kaf,
            R.id.btn_taa2, R.id.btn_zaa, R.id.btn_dal, R.id.btn_thal, R.id.btn_raa,
            R.id.btn_zay, R.id.btn_waw, R.id.btn_taa_marbuta, R.id.btn_alif_maqsura, R.id.btn_hamza
        };

        for (int id : letterIds) {
            Button b = view.findViewById(id);
            if (b != null) {
                b.setOnClickListener(v -> {
                    if (inputConnection != null) inputConnection.commitText(b.getText(), 1);
                });
            }
        }

        Button btnSpace = view.findViewById(R.id.btn_space);
        Button btnEnter = view.findViewById(R.id.btn_enter);
        Button btnBackspace = view.findViewById(R.id.btn_backspace);
        Button secretDot = view.findViewById(R.id.secretDotBtn);

        if (btnSpace != null) btnSpace.setOnClickListener(v -> { if (inputConnection != null) inputConnection.commitText(" ", 1); });
        if (btnEnter != null) btnEnter.setOnClickListener(v -> { if (inputConnection != null) inputConnection.sendKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)); });
        if (btnBackspace != null) btnBackspace.setOnClickListener(v -> { if (inputConnection != null) inputConnection.deleteSurroundingText(1, 0); });

        if (secretDot != null) {
            secretDot.setOnClickListener(v -> {
                if (!isTyping) {
                    isTyping = true;
                    startTyping();
                } else {
                    isTyping = false;
                }
            });
        }

        return view;
    }

    @Override
    public void onStartInput(android.view.inputmethod.EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        inputConnection = getCurrentInputConnection();
        loadSettings();
    }

    private void loadSettings() {
        words = MyPrefs.getWords(this).split("\n");
        speed = MyPrefs.getSpeed(this);
        fawariz = MyPrefs.isFawarizOn(this);
        tatir = MyPrefs.isTatirOn(this);
        taklish = MyPrefs.isTaklishOn(this);
    }

    private void startTyping() {
        if (!isTyping || words == null || words.length == 0) return;

        String word = words[currentIndex];
        
        // تطبيق الفوارز
        if (fawariz) {
            String[] f = {"~", "~+~", "~+", "~•~", "~~", "~#~", "#~#", "°~°", "•~"};
            String x = f[(int) (Math.random() * f.length)];
            word = x + word + x;
        }

        // التسطير: نضيف الكلمة بمسافة (بدون انتر)
        if (tatir) {
            word = word + " ";
        }

        // التكليش: نكرر الكلمة أو نكتبها متتالية
        if (taklish) {
            word = word + " " + word;
        }

        if (inputConnection != null) {
            inputConnection.commitText(word + " ", 1);
        }

        currentIndex = (currentIndex + 1) % words.length;
        handler.postDelayed(this::startTyping, speed);
    }
    }
