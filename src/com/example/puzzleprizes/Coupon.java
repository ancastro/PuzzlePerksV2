package com.example.puzzleprizes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/* Activity to display coupon.
 * Now uses WebView to grab the coupon from the web.
 * In the future, will need data about business from Game
 * so it can grab the correct coupon.
 */
public class Coupon extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.coupon);
/*		WebView webview = new WebView(this);
		setContentView(webview);
		webview.loadUrl(getString(R.string.coupon_url));*/
		Uri uri = Uri.parse(getString(R.string.coupon_url));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}
