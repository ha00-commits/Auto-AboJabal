package com.abojabal.auto;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefs {
    private static final String PREF_NAME = "AutoPrefs";

    public static void saveWords(Context c, String v) { c.getSharedPreferences(PREF_NAME, 0).edit().putString("words", v).apply(); }
    public static String getWords(Context c) { return c.getSharedPreferences(PREF_NAME, 0).getString("words", ""); }
    public static void saveSpeed(Context c, int v) { c.getSharedPreferences(PREF_NAME, 0).edit().putInt("speed", v).apply(); }
    public static int getSpeed(Context c) { return c.getSharedPreferences(PREF_NAME, 0).getInt("speed", 50); }
    public static void saveFawariz(Context c, boolean v) { c.getSharedPreferences(PREF_NAME, 0).edit().putBoolean("fawariz", v).apply(); }
    public static boolean isFawarizOn(Context c) { return c.getSharedPreferences(PREF_NAME, 0).getBoolean("fawariz", false); }
    public static void saveTatir(Context c, boolean v) { c.getSharedPreferences(PREF_NAME, 0).edit().putBoolean("tatir", v).apply(); }
    public static boolean isTatirOn(Context c) { return c.getSharedPreferences(PREF_NAME, 0).getBoolean("tatir", false); }
    }
