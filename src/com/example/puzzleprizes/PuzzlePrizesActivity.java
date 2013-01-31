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
/* Starting Activity, derived from Sudoku app.
 * Changes by Keith Gudger 2013 include:
 * a)  Changed buttons to reflect new app's categories as
 * enumerated in "Category" below.
 * b)  Buttons now call openCategoryDialog to get the category
 * of the users choice and which store within choice.
 * Eventually the main activity needs to contact the server
 * and get the alphaSub string, puzzles and coupons associated
 * with the merchants who have signed up with the service.
 * In PuzzleView the server needs to supply the highlighted squares.
 */

public class PuzzlePrizesActivity extends Activity implements OnClickListener {
		/** Called when the activity is first created. */
	private static final String TAG = "Sudoku";
	public enum Category { RESTAURANT, EVENT, PERSONAL, HOTEL, OTHER } ;
	 
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
    	    		openCategoryDialog(Category.OTHER);
                }
    });
	        View restaurantButton = findViewById(R.id.restaurant_button);
	        restaurantButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openCategoryDialog(Category.RESTAURANT);
                }
    });
	        View eventsButton = findViewById(R.id.events_button);
	        eventsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openCategoryDialog(Category.EVENT);
                }
    });
	        View personalButton = findViewById(R.id.personal_button);
	        personalButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openCategoryDialog(Category.PERSONAL);
                }
    });
	        View hotelButton = findViewById(R.id.hotel_button);
	        hotelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
    	    		openCategoryDialog(Category.HOTEL);
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
	    	case R.id.exit_button:
	    		finish();
	    		break;
	    	case R.id.continue_button:
	    		
	    		break;
	    	}
	    }

	    private void openNewGameDialog(final Category cat, final int item) {
	    	new AlertDialog.Builder(this)
	    		.setTitle(R.string.new_game_title)
	    		.setItems(R.array.difficulty,
	    			new DialogInterface.OnClickListener() {
			    		public void onClick(DialogInterface dialoginterface, int i) {
			    			startGame(i,cat,item);
			    		}
	    	}).show();
	    }

	    private void startGame(int i, Category cat, int item) {
	    	Log.d(TAG, "clicked on " + i);
	    	Intent intent = new Intent(PuzzlePrizesActivity.this, Game.class);
	    	intent.putExtra(Game.KEY_DIFFICULTY, i);
	    	intent.putExtra(Game.KEY_CATEGORY, cat);
	    	intent.putExtra(Game.KEY_ITEM, item);
	    	startActivity(intent);
	    }
	    
	    private void openCategoryDialog(final Category cat) {
	    	Log.d(TAG, "clicked on Category Button");
	    	int label ;
	    	int type_array ;
	    	switch (cat) {
	    	case RESTAURANT :
	    		label = R.string.restaurant_label ;
	    		type_array = R.array.restaurants ;
	    		break ;
	    	case EVENT :
	    		label = R.string.events_label ;
	    		type_array = R.array.events ;
	    		break ;
	    	case PERSONAL :
	    		label = R.string.personal_label ;
	    		type_array = R.array.personal ;
	    		break ;
	    	case HOTEL :
	    		label = R.string.hotel_label ;
	    		type_array = R.array.hotels ;
	    		break ;
	    	case OTHER :
	    	default :
	    		label = R.string.other_label ;
	    		type_array = R.array.others ;
	    		break ;
	    	}
	    	new AlertDialog.Builder(this)
    		.setTitle(label)
    		.setItems(type_array,
    			new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialoginterface, int item) {
		    			openNewGameDialog(cat, item);
		    		}
    	}).show();
	    }

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
}

