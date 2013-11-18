package com.index.table;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedActivity extends Activity{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        
        
        TextView selectedText = (TextView) findViewById(R.id.selectedRowTxt);
        selectedText.setText(getIntent().getExtras().getString("SELECTED_ROW"));
    } 
}
