package com.example.puzzleprizes;

import android.app.Activity;
import android.os.Bundle;
/* this simple activity presents the coupon dialog.
 * It is defined is res/layout/coupon.xml, and the activity 
 * .About defined in AndroidManifest.xml
 * In the long run, this needs to do 2 things:
 * Display the appropriate coupon (related to the game)
 * and save the coupon in some way on the SD card. 
 */

public class Coupon extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon);
	}
}
