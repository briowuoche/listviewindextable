package com.index.table;

import java.util.Vector;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.index.table.service.UserService;
import com.index.table.to.Book;

public class IndexTableActivity extends CustomListIndex 
{
    private ListView booksLV;
    
    private UserListAdapter userListAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        booksLV = (ListView)findViewById(R.id.booksLV);
        selectedIndex = (TextView) findViewById(R.id.selectedIndex);
        userVector = UserService.getUserList(this);
        
        Vector<Book> subsidiesList = getIndexedBooks(userVector);
        totalListSize = subsidiesList.size();
        
        userListAdapter = new UserListAdapter(this, subsidiesList);
        booksLV.setAdapter(userListAdapter);
        
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.setOnClickListener(onClicked);
        sideIndexHeight = sideIndex.getHeight();
        mGestureDetector = new GestureDetector(this,
                new ListIndexGestureListener());
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getDisplayListOnChange();
    }
    
    
    private Vector<Book> getIndexedBooks(
            Vector<Book> booksVector) {
        
        //Retrieve it from DB in shorting order
        
        Vector<Book> v = new Vector<Book>();
        //Add default item
      
        String idx1 = null;
        String idx2 = null;
        for (int i = 0; i < booksVector.size(); i++) {
            Book temp = booksVector.elementAt(i);
            //Insert the alphabets
            idx1 = (temp.getTitle().substring(0, 1)).toLowerCase();
            if(!idx1.equalsIgnoreCase(idx2))
            {
                v.add(new Book(idx1.toUpperCase(), "", "" , "" , ""));
                idx2 = idx1;
                dealList.add(i);
            }
            v.add(temp);
        }
        
        return v;
    }
    
    /**
     * ListIndexGestureListener method gets the list on scroll.
     */
    private class ListIndexGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {

            /**
             * we know already coordinates of first touch we know as well a
             * scroll distance
             */
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            /**
             * when the user scrolls within our side index, we can show for
             * every position in it a proper item in the list
             */
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}