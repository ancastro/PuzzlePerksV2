package com.example.puzzleprizes;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.puzzleprizes.R;
/* Starting Activity, derived from Sudoku app.
 * Changes by Keith Gudger 2013 include:
 * a)  Changed buttons to reflect new app's categories as
 * enumerated in "Category" below.
 * b)  Buttons now call openCategoryDialog to get the category
 * of the users choice and which business within choice.
 * Eventually the main activity needs to contact the server
 * and get the alphaSub string, puzzles and coupons associated
 * with the merchants who have signed up with the service.
 * In PuzzleView the server needs to supply the highlighted squares.
 */

public class PuzzlePrizesActivity extends Activity implements OnClickListener {
		/** Called when the activity is first created. */
	private static final String TAG = "Sudoku";
	public enum Category { RESTAURANT, EVENT, PERSONAL, HOTEL, OTHER } ;
	public String[] finalRestList, finalHotList, 
					finalEventList, finalPersonalList, finalOtherList ;
	 
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
            String[] restaurantList = {""};  // used by background thread to get list

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
            /*
             * Spawn a GetListTask thread. This thread will get the data from the
             * server in the background, so as not to block our main (UI) thread.
             */
            (new GetListTask()).execute((Object)null);
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

	    private void openNewGameDialog(final Category cat, final int item, final String[] business) {
	    	new AlertDialog.Builder(this)
	    		.setTitle(R.string.new_game_title)
	    		.setItems(R.array.difficulty,
	    			new DialogInterface.OnClickListener() {
			    		public void onClick(DialogInterface dialoginterface, int i) {
			    			startGame(i,business[item]);
			    		}
	    	}).show();
	    }

	    private void startGame(int i, String business) {
	    	Log.d(TAG, "clicked on " + i);
	    	Intent intent = new Intent(PuzzlePrizesActivity.this, Game.class);
	    	intent.putExtra(Game.KEY_DIFFICULTY, i);
	    	intent.putExtra(Game.KEY_BUSINESS, business) ;
	    	startActivity(intent);
	    }
	    
	    private void openCategoryDialog(final Category cat) {
	    	Log.d(TAG, "clicked on Category Button");
	    	int label ;
	    	String[] charSequenceItems = null ;
	    	switch (cat) {
	    	case RESTAURANT :
	    		label = R.string.restaurant_label ;
	    		if ( finalRestList != null) {
	    			charSequenceItems = new String[finalRestList.length] ;
	    			System.arraycopy( finalRestList, 0, charSequenceItems, 0, finalRestList.length );
	    		}
	    		else { 
	    			charSequenceItems = new String[1] ;
	    			charSequenceItems[0] = "" ;
	    		}
	    		break ;
	    	case EVENT :
	    		label = R.string.events_label ;
	    		if ( finalEventList != null) {
	    			charSequenceItems = new String[finalEventList.length] ;
	    			System.arraycopy( finalEventList, 0, charSequenceItems, 0, finalEventList.length );
	    		}
	    		else { 
	    			charSequenceItems = new String[1] ;
	    			charSequenceItems[0] = "" ;
	    		}
	    		break ;
	    	case PERSONAL :
	    		label = R.string.personal_label ;
	    		if ( finalPersonalList != null) {
	    			charSequenceItems = new String[finalPersonalList.length] ;
	    			System.arraycopy( finalPersonalList, 0, charSequenceItems, 0, finalPersonalList.length );
	    		}
	    		else { 
	    			charSequenceItems = new String[1] ;
	    			charSequenceItems[0] = "" ;
	    		}
	    		break ;
	    	case HOTEL :
	    		label = R.string.hotel_label ;
	    		if ( finalHotList != null) {
	    			charSequenceItems = new String[finalHotList.length] ;
	    			System.arraycopy( finalHotList, 0, charSequenceItems, 0, finalHotList.length );
	    		}
	    		else { 
	    			charSequenceItems = new String[1] ;
	    			charSequenceItems[0] = "" ;
	    		}
	    		break ;
	    	case OTHER :
	    	default :
	    		label = R.string.other_label ;
	    		if ( finalOtherList != null) {
	    			charSequenceItems = new String[finalOtherList.length] ;
	    			System.arraycopy( finalOtherList, 0, charSequenceItems, 0, finalOtherList.length );
	    		}
	    		else { 
	    			charSequenceItems = new String[1] ;
	    			charSequenceItems[0] = "" ;
	    		}
	    		break ;
	    	}
	    	final String business[] = charSequenceItems;
	    	new AlertDialog.Builder(this)
    		.setTitle(label)
//    		.setItems(type_array,
    		.setItems(charSequenceItems,
    			new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialoginterface, int item) {
		    			openNewGameDialog(cat, item, business);
		    		}
    	}).show();
	    }

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
		
        /**
         * Used to implement an asynchronous retrieval of the list from the web.
         * This uses the AsyncTask class as a starting point. For more info, see
         * http://android-developers.blogspot.com/2009/05/painless-threading.html.
         */
        @SuppressWarnings("unchecked")
        private class GetListTask extends AsyncTask<Object, Object, Object> {

                /**
                 * Let's make the http request and return the result as a String.
                 */
                protected String doInBackground(Object... args) {                       
                        return ServerInterface.getBusinessList(getString(R.string.server_url));
                }

                /**
                 * Parse the String result, and create a new array adapter for the list
                 * view.
                 */
                protected void onPostExecute(Object objResult) {
                        // check to make sure we're dealing with a string
                        if(objResult != null && objResult instanceof String) {                          
                                String result = (String) objResult;
                                // this is used to hold the string array, after tokenizing
                                String[] responseList, businessList;

                                // we'll use a string tokenizer, with "," (comma) as the delimiter
                                StringTokenizer tk1 = new StringTokenizer(result, ";");

                                // now we know how long the string array is
                                responseList = new String[tk1.countTokens()];
                                int j = 0 ;
                                while (tk1.hasMoreTokens()) {
                                	responseList[j] = tk1.nextToken();
                                    StringTokenizer tk2 = new StringTokenizer(responseList[j++], ",");
                                    businessList = new String[tk2.countTokens()];
                                    int i = 0;
                                    while(tk2.hasMoreTokens()) {
                                            businessList[i++] = tk2.nextToken();
                                    }
                                    
                                    if (businessList[0].equals("Restaurants")) {
                                    	finalRestList = new String[i - 1];
                                        System.arraycopy(businessList, 1, finalRestList, 0, i-1);
                                    }
                                    else if (businessList[0].equals("Hotels")) {
                                    	finalHotList = new String[i - 1];
                                        System.arraycopy(businessList, 1, finalHotList, 0, i-1);
                                    }
                                    else if (businessList[0].equals("Other")) {
                                    	finalOtherList = new String[i - 1];
                                        System.arraycopy(businessList, 1, finalOtherList, 0, i-1);
                                    }
                                    else if (businessList[0].equals("Events")) {
                                    	finalEventList = new String[i - 1];
                                        System.arraycopy(businessList, 1, finalEventList, 0, i-1);
                                    }
                                    else if (businessList[0].equals("Personal Items")) {
                                    	finalPersonalList = new String[i - 1];
                                        System.arraycopy(businessList, 1, finalPersonalList, 0, i-1);
                                    }
                                }
                        } else {
                            Toast.makeText(getApplicationContext(), "Internet NOT Available, Try Later.", Toast.LENGTH_SHORT).show();

                        }
                }

        }

}

