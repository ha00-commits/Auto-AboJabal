package com.abojabal.auto;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefs {
    private static final String PREF_NAME = "AutoPrefs";
    private static final String KEY_WORDS = "words";
    private static final String KEY_SPEED = "speed";
    private static final String KEY_FAWARIZ = "fawariz";

    public static void saveWords(Context context, String words) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_WORDS, words).apply();
    }

    public static String getWords(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_WORDS, "");
    }

    public static void saveSpeed(Context context, int speed) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_SPEED, speed).apply();
    }

    public static int getSpeed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_SPEED, 50);
    }

    public static void saveFawariz(Context context, boolean isOn) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_FAWARIZ, isOn).apply();
    }

    public static boolean isFawarizOn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_FAWARIZ, false);
    }
  }
