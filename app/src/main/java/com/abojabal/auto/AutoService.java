package com.abojabal.auto;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class AutoService extends AccessibilityService {

    private boolean isRunning = false;
    private String[] wordsToType;
    private int currentWordIndex = 0;
    private int speed = 50;
    private boolean fawarizOn = false;

    @Override
    public void onServiceConnected() {
        // تحميل الإعدادات من الذاكرة
        wordsToType = MyPrefs.getWords(this).split("\n");
        speed = MyPrefs.getSpeed(this);
        fawarizOn = MyPrefs.isFawarizOn(this);
        Toast.makeText(this, "خدمة الأوتو جاهزة", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // هنا نلتقط ضغطات زر الصوت (مثلاً لتفعيل/إيقاف)
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            // منطق تشغيل/إيقاف (تحتاج ربط مع زر الصوت)
        }
    }

    private void typeNextWord(AccessibilityNodeInfo root) {
        if (currentWordIndex >= wordsToType.length) {
            currentWordIndex = 0; // إعادة من البداية بدون تكرار
        }
        String word = wordsToType[currentWordIndex];
        
        // إذا الفوارز مفعلة، نضيف فوارز
        if (fawarizOn) {
            String[] fawarizList = {"~", "~+~", "~+", "~•~", "~~", "~#~", "#~#", "°~°", "•~"};
            int random = (int) (Math.random() * fawarizList.length);
            word = fawarizList[random] + word + fawarizList[random];
        }

        // البحث عن حقل الكتابة وكتابة الكلمة
        AccessibilityNodeInfo inputField = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (inputField != null) {
            inputField.setText(word + " "); // مسافة بدل سطر جديد
        }

        currentWordIndex++;
    }

    @Override
    public void onInterrupt() {
    }
}
