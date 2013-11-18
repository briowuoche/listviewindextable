package com.index.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.index.table.to.Book;

public class CustomListIndex extends Activity {

	private static final String TAG = CustomListIndex.class.getSimpleName();

	protected GestureDetector mGestureDetector;

	protected Vector<Book> userVector;

	protected int totalListSize = 0;

	/**
	 * list with items for side index
	 */
	private ArrayList<Object[]> indexList = null;

	/**
	 * list with row number for side index
	 */
	protected List<Integer> dealList = new ArrayList<Integer>();

	/**
	 * height of left side index
	 */
	protected int sideIndexHeight;

	/**
	 * number of items in the side index
	 */
	private int indexListSize;

	/**
	 * x and y coordinates within our side index
	 */
	protected static float sideIndexX;
	protected static float sideIndexY;

	protected TextView selectedIndex;

	private List<String> alphaList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, TAG);
	}

	private int listLocation;

	/**
	 * displayListItem is method used to display the row from the list on scrool
	 * or touch.
	 */
	public void displayListItem() {

		// compute number of pixels for every side index item
		double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

		// compute the item index for given event position belongs to
		int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

		if (itemPosition < 0) {
			itemPosition = 0;
		} else if (itemPosition >= dealList.size()) {
			itemPosition = dealList.size() - 1;
		}

		selectedIndex.setText(alphaList.get(itemPosition));

		ListView listView = (ListView) findViewById(R.id.booksLV);

		listLocation = dealList.get(itemPosition) + itemPosition;

		if (listLocation > totalListSize) {
			listLocation = totalListSize;
		}

		listView.setSelection(listLocation);
	}

	/**
	 * getListArrayIndex consists of index details, to navigate through out the
	 * index.
	 * 
	 * @param strArr
	 *            , index values
	 * @return ArrayList object
	 */
	private ArrayList<Object[]> getListArrayIndex(String[] strArr) {
		ArrayList<Object[]> tmpIndexList = new ArrayList<Object[]>();
		Object[] tmpIndexItem = null;

		int tmpPos = 0;
		String tmpLetter = "";
		String currentLetter = null;

		for (int j = 0; j < strArr.length; j++) {
			currentLetter = strArr[j];

			// every time new letters comes
			// save it to index list
			if (!currentLetter.equalsIgnoreCase(tmpLetter)) {
				tmpIndexItem = new Object[3];
				tmpIndexItem[0] = tmpLetter;
				tmpIndexItem[1] = tmpPos - 1;
				tmpIndexItem[2] = j - 1;

				tmpLetter = currentLetter;
				tmpPos = j + 1;
				tmpIndexList.add(tmpIndexItem);
			}
		}

		// save also last letter
		tmpIndexItem = new Object[3];
		tmpIndexItem[0] = tmpLetter;
		tmpIndexItem[1] = tmpPos - 1;
		tmpIndexItem[2] = strArr.length - 1;
		tmpIndexList.add(tmpIndexItem);

		// and remove first temporary empty entry
		if (tmpIndexList != null && tmpIndexList.size() > 0) {
			tmpIndexList.remove(0);
		}

		return tmpIndexList;
	}

	/**
	 * getDisplayListOnChange method display the list from the index.
	 * 
	 * @param displayScreen
	 *            , specify which screen it belongs
	 */
	public void getDisplayListOnChange() {
		LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
		sideIndexHeight = sideIndex.getHeight();

		if (sideIndexHeight == 0) {
			Display display = getWindowManager().getDefaultDisplay();
			sideIndexHeight = display.getHeight();
			// Navigation Bar + Tab space comes approximately 80dip
		}

		sideIndex.removeAllViews();

		/**
		 * temporary TextView for every visible item
		 */
		TextView tmpTV = null;

		/**
		 * we will create the index list
		 */
		String[] strArr = new String[userVector.size()];

		int j = 0;
		for (Book user : userVector) {
			strArr[j++] = user.getTitle().substring(0, 1);
		}

		indexList = getListArrayIndex(strArr);

		/**
		 * number of items in the index List
		 */
		indexListSize = indexList.size();

		/**
		 * maximal number of item, which could be displayed
		 */
		int indexMaxSize = (int) Math.floor(sideIndexHeight / 25);

		int tmpIndexListSize = indexListSize;

		/**
		 * handling that case when indexListSize > indexMaxSize, if true display
		 * the half of the side index otherwise full index.
		 */
		while (tmpIndexListSize > indexMaxSize) {
			tmpIndexListSize = tmpIndexListSize / 2;
		}

		/**
		 * computing delta (only a part of items will be displayed to save a
		 * place, without compact
		 */
		double delta = indexListSize / tmpIndexListSize;

		String tmpLetter = null;
		Object[] tmpIndexItem = null;

		for (double i = 1; i <= indexListSize; i = i + delta) {
			tmpIndexItem = indexList.get((int) i - 1);
			tmpLetter = tmpIndexItem[0].toString();
			tmpTV = new TextView(this);
			tmpTV.setText(tmpLetter);
			tmpTV.setGravity(Gravity.CENTER);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 1);
			tmpTV.setLayoutParams(params);
			alphaList.add(tmpLetter);
			sideIndex.addView(tmpTV);
		}

		sideIndex.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sideIndexX = event.getX();
				sideIndexY = event.getY();

				displayListItem();

				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					selectedIndex.setText("");
				}
				return false;
			}
		});
	}

	protected OnClickListener onClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
		}
	};
}
