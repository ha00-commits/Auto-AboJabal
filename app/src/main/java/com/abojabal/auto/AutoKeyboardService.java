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
    private Handler handler = new Handler(Looper.getMainLooper());
    private InputConnection inputConnection;

    @Override
    public View onCreateInputView() {
        View view = getLayoutInflater().inflate(R.layout.keyboard_view, null);
        
        // تحميل الإعدادات
        words = MyPrefs.getWords(this).split("\n");
        speed = MyPrefs.getSpeed(this);
        fawariz = MyPrefs.isFawarizOn(this);

        // ربط أزرار الحروف (كل زر يكتب الحرف ماله)
        int[] buttonIds = {
            R.id.btnSpace, R.id.btnEnter, R.id.btnBackspace // الأزرار الخاصة
        };
        
        // ربط أزرار الحروف العادية (نستخدم الـ ID من الـ XML)
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof android.widget.LinearLayout) {
                android.widget.LinearLayout row = (android.widget.LinearLayout) child;
                for (int j = 0; j < row.getChildCount(); j++) {
                    View btnView = row.getChildAt(j);
                    if (btnView instanceof Button && btnView.getId() != R.id.secretDotBtn) {
                        Button btn = (Button) btnView;
                        btn.setOnClickListener(v -> {
                            if (inputConnection != null) {
                                inputConnection.commitText(btn.getText(), 1);
                            }
                        });
                    }
                }
            }
        }

        // أزرار التحكم
        Button btnSpace = view.findViewById(R.id.btnSpace);
        Button btnEnter = view.findViewById(R.id.btnEnter);
        Button btnBackspace = view.findViewById(R.id.btnBackspace);
        Button secretDot = view.findViewById(R.id.secretDotBtn);

        if (btnSpace != null) btnSpace.setOnClickListener(v -> { if (inputConnection != null) inputConnection.commitText(" ", 1); });
        if (btnEnter != null) btnEnter.setOnClickListener(v -> { if (inputConnection != null) inputConnection.sendKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)); });
        if (btnBackspace != null) btnBackspace.setOnClickListener(v -> { if (inputConnection != null) inputConnection.deleteSurroundingText(1, 0); });

        // النقطة السرية (تشغل الأوتو)
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
    }

    private void startTyping() {
        if (!isTyping || words == null || words.length == 0) return;

        String word = words[currentIndex];
        
        if (fawariz) {
            String[] f = {"~", "~+~", "~+", "~•~", "~~", "~#~", "#~#", "°~°", "•~"};
            String x = f[(int) (Math.random() * f.length)];
            word = x + word + x;
        }

        if (inputConnection != null) {
            inputConnection.commitText(word + " ", 1);
        }

        currentIndex = (currentIndex + 1) % words.length;
        handler.postDelayed(this::startTyping, speed);
    }
  }
