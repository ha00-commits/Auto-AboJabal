package com.abojabal.auto;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.Bundle;

public class AutoService extends AccessibilityService {
    private boolean isRunning = false;
    private String[] words;
    private int currentIndex = 0;
    private int speed = 50;
    private boolean fawariz = false;
    private Handler handler = new Handler(Looper.getMainLooper());
    private long lastClickTime = 0;

    @Override
    public void onServiceConnected() {
        loadSettings();
    }

    private void loadSettings() {
        words = MyPrefs.getWords(this).split("\n");
        speed = MyPrefs.getSpeed(this);
        fawariz = MyPrefs.isFawarizOn(this);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getRepeatCount() == 0) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < 500) {
                    toggleAuto();
                }
                lastClickTime = currentTime;
            }
            return true;
        }
        return super.onKeyEvent(event);
    }

    private void toggleAuto() {
        isRunning = !isRunning;
        if (isRunning) {
            startTyping();
        }
    }

    private void startTyping() {
        if (!isRunning || words == null || words.length == 0) return;
        
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) {
            handler.postDelayed(this::startTyping, 100);
            return;
        }

        AccessibilityNodeInfo input = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (input != null) {
            String word = words[currentIndex];
            if (fawariz) {
                String[] f = {"~", "~+~", "~+", "~•~", "~~", "~#~", "#~#", "°~°", "•~"};
                String x = f[(int) (Math.random() * f.length)];
                word = x + word + x;
            }
            
            // محاولة الكتابة المباشرة
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, word + " ");
            input.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            
            currentIndex = (currentIndex + 1) % words.length;
            
            handler.postDelayed(this::startTyping, speed);
        } else {
            handler.postDelayed(this::startTyping, 100);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
    }
