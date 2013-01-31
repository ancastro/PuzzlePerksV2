package com.example.puzzleprizes;

import android.os.Bundle;
import android.preference.PreferenceActivity;
// Original preference window / activity for "hints" settings.

public class Prefs extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
