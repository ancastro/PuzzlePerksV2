package com.example.puzzleprizes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.puzzleprizes.R;


public class PuzzlePrizesActivity extends Activity implements OnClickListener {
		/** Called when the activity is first created. */
	private static final String TAG = "Sudoku";
//	public static final CharSequence alphaSub = "ABCDEFGHIK";
	 
	    @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_puzzle_prizes);
			ImageView image = (ImageView) findViewById(R.id.imageView1);
	        
	        //Set up click listeners for all the buttons
/*	        View continueButton = findViewById(R.id.continue_button);
	        continueButton.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View view) {
                 }
     });*/
//	        continueButton.setOnClickListener((android.view.View.OnClickListener) this);

	        View otherButton = findViewById(R.id.other_button);
	        otherButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openNewGameDialog(0);
                }
    });
	        View restaurantButton = findViewById(R.id.restaurant_button);
	        restaurantButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openRestaurantDialog();
                }
    });
	        View eventsButton = findViewById(R.id.events_button);
	        eventsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
					openNewGameDialog(0);
                }
    });
	        View personalButton = findViewById(R.id.personal_button);
	        personalButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openNewGameDialog(0);
                }
    });
	        View hotelButton = findViewById(R.id.hotel_button);
	        hotelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openNewGameDialog(0);
                }
    });
//	        newButton.setOnClickListener((android.view.View.OnClickListener) this);
	        View aboutButton = findViewById(R.id.about_button);
	        aboutButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                	startActivity(new Intent(PuzzlePrizesActivity.this, About.class));
                }
    });
/*	        View exitButton = findViewById(R.id.exit_button);
	        exitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		finish();
                }
    });*/
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	super.onCreateOptionsMenu(menu);
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.activity_puzzle_prizes, menu);
//	    	inflater.inflate(R.menu.menu, menu);
	    	return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch(item.getItemId()) {
	    	case R.id.settings:
	    		startActivity(new Intent(this, Prefs.class));
	    		return true;
	    	}
	    	return false;
	    }
	    
	    public void onClick(View v) {
	    	switch (v.getId()) {
	    	case R.id.about_button:
	    		Intent i = new Intent(this, About.class);
	    		startActivity(i);
	    		break;
	    	case R.id.new_button:
	    		openNewGameDialog(0);
	    		break;
	    	case R.id.exit_button:
	    		finish();
	    		break;
	    	case R.id.continue_button:
	    		
	    		break;
	    	}
	    }

	    private void openNewGameDialog(final int h) {
	    	new AlertDialog.Builder(this)
	    		.setTitle(R.string.new_game_title)
	    		.setItems(R.array.difficulty,
	    			new DialogInterface.OnClickListener() {
			    		public void onClick(DialogInterface dialoginterface, int i) {
			    			startGame(i,h);
			    		}
	    	}).show();
	    }

	    private void startGame(int i, int h) {
	    	Log.d(TAG, "clicked on " + i);
	    	Intent intent = new Intent(PuzzlePrizesActivity.this, Game.class);
	    	intent.putExtra(Game.KEY_DIFFICULTY, i);
	    	startActivity(intent);
	    }
	    
	    private void openRestaurantDialog() {
	    	Log.d(TAG, "clicked on Restaurant");
	    	new AlertDialog.Builder(this)
    		.setTitle(R.string.restaurant_label)
    		.setItems(R.array.restaurants,
    			new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialoginterface, int h) {
		    			openNewGameDialog(h);
		    		}
    	}).show();
	    }

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
}

