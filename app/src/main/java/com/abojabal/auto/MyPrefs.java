package com.abojabal.auto;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefs {
    private static final String PREF_NAME = "AutoPrefs";

    public static void saveWords(Context context, String words) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString("words", words).apply();
    }

    public static String getWords(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString("words", "");
    }

    public static void saveSpeed(Context context, int speed) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();
    }

    public static int getSpeed(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt("speed", 50);
    }

    public static void saveFawariz(Context context, boolean isOn) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean("fawariz", isOn).apply();
    }

    public static boolean isFawarizOn(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean("fawariz", false);
    }

    public static void saveTatir(Context context, boolean isOn) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean("tatir", isOn).apply();
    }

    public static boolean isTatirOn(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean("tatir", false);
    }

    public static void saveTaklish(Context context, boolean isOn) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean("taklish", isOn).apply();
    }

    public static boolean isTaklishOn(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean("taklish", false);
    }
    }
