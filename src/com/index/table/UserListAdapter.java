package com.index.table;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.index.table.to.Book;

public class UserListAdapter extends BaseAdapter {

	private static final String TAG = UserListAdapter.class.getName();
	private Activity activity;
	private Vector<Book> items;
	
	public UserListAdapter(Activity activity,
			Vector<Book> items) {
	    Log.i(TAG, TAG);
		this.activity = activity;
		this.items = items;
	}


	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listrow_user, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.nameTV);
			holder.headingLL = (LinearLayout)convertView.findViewById(R.id.headingLL);
			holder.headingTV = (TextView)convertView.findViewById(R.id.headingTV);
			holder.nameLL = (LinearLayout)convertView.findViewById(R.id.nameLL);

			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < items.size()) {

			final Book book = items.get(position);
			if (book != null
					&& (book.getTitle().length() == 1)) 
			{
				holder.nameLL.setVisibility(View.GONE);
				holder.headingLL.setVisibility(View.VISIBLE);
				holder.headingTV.setText(book.getTitle());
				holder.headingLL.setBackgroundColor(android.R.color.transparent);
			}
			else
			{
				holder.nameLL.setVisibility(View.VISIBLE);
				holder.headingLL.setVisibility(View.GONE);
				holder.name.setText(book.getTitle());
				
				View ll = (LinearLayout)holder.name.getParent();
                ll.setFocusable(true);
                ll.setSelected(true);
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity.getApplicationContext(), "Clicked on " + book.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, SelectedActivity.class);
                        intent.putExtra("SELECTED_ROW", book.getTitle());
                        activity.startActivity(intent);
                    }
                });
			}
		}
		

		return convertView;
	}

	private static class ViewHolder {
		TextView name,headingTV;
		LinearLayout nameLL,headingLL;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
