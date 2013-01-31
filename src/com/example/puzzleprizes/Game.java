package com.example.puzzleprizes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
/* the actual game of Sudoku with letters substituted for the numbers.
 * Changes by Keith Gudger 2013, include:
 * a) pass merchant category and merchant from the list of coupon suppliers.
 * This will come from a web server in the future.  Right now there are only
 * 3 games embedded in this activity.  "Easy" is the Ambrosia game with only
 * 3 tiles left undone.  Medium is the Ambrosia game with proper highlighting.
 * Hard is a game from the original application.
 * b) AlphaSub is the number to letter substitution.  This will come from the 
 * server in the future.
 * c) ifFinished() reports to PuzzleView that the game is finished so it can
 * show the user the coupon.
 */
public class Game extends Activity {
	private static final String TAG = "Sudoku";
	
	public static final String KEY_DIFFICULTY = "com.example.puzzleprizes.difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM =1;
	public static final int DIFFICULTY_HARD = 2;

	public static final String KEY_CATEGORY = "com.example.puzzleprizes.category";
	public static final String KEY_ITEM     = "com.example.puzzleprizes.item";

	private int puzzle[] = new int[9 * 9];

	private PuzzleView puzzleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		
		int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		int cat = getIntent().getIntExtra(KEY_CATEGORY, 0);
		int item = getIntent().getIntExtra(KEY_ITEM, 0);
		puzzle = getPuzzle(diff,cat,item);
		calculateUsedTiles();
		
		String mystring = getResources().getString(R.string.game_title);
		setTitle(mystring + " Key " + getAlphaSub());
		
		puzzleView = new PuzzleView(this);
		setContentView(puzzleView);
		puzzleView.requestFocus();
	}
	
	protected void showKeypadOrError(int x, int y) {
		int tiles[] = getUsedTiles(x, y);
		if (tiles.length == 9) {
			Toast toast = Toast.makeText(this, R.string.no_moves_label, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
			Log.d(TAG, "showKeypad: used=" + toPuzzleString(tiles));
			Dialog v = new Keypad(this, tiles, puzzleView);
			v.show();
		}
	}

	protected boolean setTileIfValid(int x, int y, int value) {
		int tiles[] = getUsedTiles(x, y);
		if (value != 0) {
			for (int tile : tiles) {
				if (tile == value)
					return false;
			}
		}
		setTile(x, y, value);
		calculateUsedTiles();
		return true;
	}

	private final int used[][][] = new int[9][9][];

	protected int[] getUsedTiles(int x, int y) {
		return used[x][y];
	}
// Tells PuzzleView that the game is finished, it then shows coupon.	
	protected int isFinished() {
		int done = 0 ;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if ( puzzle[y * 9 + x] != 0)
					done++ ;
			}
		}
		return done ;
	}

	private void calculateUsedTiles() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				used[x][y] = calculateUsedTiles(x, y);
				// Log.d(TAG, "used[" + x + "][" + y + "] = "
				// + toPuzzleString(used[x][y]));
			}
		}
	}

	private int[] calculateUsedTiles(int x, int y) {
		int c[] = new int[9];
		// horizontal
		for (int i = 0; i < 9; i++) {
			if (i == y)
				continue;
			int t = getTile(x, i);
			if (t != 0)
				c[t - 1] = t;
		}
		// vertical
		for (int i = 0; i < 9; i++) {
			if (i == x)
				continue;
			int t = getTile(i, y);
			if (t != 0)
				c[t - 1] = t;
		}
		// same cell block
		int startx = (x / 3) * 3;
		int starty = (y / 3) * 3;
		for (int i = startx; i < startx + 3; i++) {
			for (int j = starty; j < starty + 3; j++) {
				if (i == x && j == y)
					continue;
				int t = getTile(i, j);
				if (t != 0)
					c[t - 1] = t;
			}
		}
		// compress
		int nused = 0;
		for (int t : c) {
			if (t != 0)
				nused++;
		}
		int c1[] = new int[nused];
		nused = 0;
		for (int t : c) {
			if (t != 0)
				c1[nused++] = t;
		}
		return c1;
	}

//	private final String easyPuzzle = "360000000004230800000004200" + "070460003820000014500013020" + "001900000007048300000000045" ;
//	private final String easyPuzzle = "075100304009805000000907008" + "204300600090000080507061009" + "050602040000509800008013720" ;
	private final String easyPuzzle = "075126394149835276326947518" + "214398657693704182587261439" + "751682943432579861968413720" ;
//	private final String easyPuzzle = "875126394149835276326947518" + "214398657693754182587261439" + "751682943432579861968413720" ;
//	private final String mediumPuzzle = "650000070000506000014000005" + "007009000002314700000700800" + "500000630000201000030000097" ;
	private final String mediumPuzzle = "075100304009805000000907008" + "204300600090000080507061009" + "050602040000509800008013720" ;
	private final String hardPuzzle = "009000000080605020501078000" + "000000700706040102004000000" + "000720903090301080000000600" ;
	private final String alphaSub = "ABIMORSTY" ;
	
	private int[] getPuzzle(int diff, int cat, int rest) {
		String puz;
		// TODO: Continue last game
		switch(diff) {
		case DIFFICULTY_HARD:
			puz = hardPuzzle;
			break;
		case DIFFICULTY_MEDIUM:
			puz = mediumPuzzle;
			break;
		case DIFFICULTY_EASY:
		default:
			puz = easyPuzzle;
			break;
		}
		return fromPuzzleString(puz);
	}

	static private String toPuzzleString(int[] puz) {
		StringBuilder buf = new StringBuilder();
		for (int element : puz) {
			buf.append(element);
		}
		return buf.toString();
	}

	static protected int[] fromPuzzleString(String string) {
		int[] puz = new int[string.length()];
		for (int i = 0; i < puz.length; i++) {
			puz[i] = string.charAt(i) - '0';
		}
		return puz;
	}

	private int getTile(int x, int y) {
		return puzzle[y * 9 + x];
	}

	private void setTile(int x, int y, int value) {
		puzzle[y * 9 + x] = value;
	}
// Returns alphabetic character associated with position x.	
	protected String getAlphaSub(int x) {
		return alphaSub.substring(x,x+1);
}
// Returns entire alphaSub string.	
	protected String getAlphaSub() {
		return alphaSub ;
	}

	protected String getTileString(int x, int y) {
		int v = getTile(x, y);
		if (v == 0)
			return "";
		else
//			return String.valueOf(v);
			return alphaSub.substring(v-1,v);
	}
}