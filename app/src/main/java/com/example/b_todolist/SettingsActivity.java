package com.example.b_todolist;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends FragmentActivity {
    private static final String PREF_FONT_SIZE = "font_size";
    private static final int DEFAULT_FONT_SIZE = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        migrateFontSizePreference();
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
    }

    private void migrateFontSizePreference() {
        Object fontSizeValue = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getAll()
                .get(PREF_FONT_SIZE);

        if (fontSizeValue instanceof String) {
            int fontSize = parseFontSize((String) fontSizeValue);
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(PREF_FONT_SIZE, fontSize)
                    .apply();
        }
    }

    private int parseFontSize(String fontSizeValue) {
        try {
            return Integer.parseInt(fontSizeValue);
        } catch (NumberFormatException exception) {
            return DEFAULT_FONT_SIZE;
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
}
