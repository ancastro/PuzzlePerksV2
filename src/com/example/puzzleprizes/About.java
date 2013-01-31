package com.example.puzzleprizes;

import android.app.Activity;
import android.os.Bundle;
/* this simple activity mereley presents an About dialog.
 * It is defined is res/layout/about.xml, and the activity 
 * .About defined in AndroidManifest.xml
 */
public class About extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}
}
