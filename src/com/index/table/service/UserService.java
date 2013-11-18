package com.index.table.service;

import java.util.Collections;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;

import com.index.table.db.DatabaseAdapter;
import com.index.table.to.Book;

public class UserService {

	public static Vector<Book> getUserList(Context ctx) {
		Vector<Book> bookList = new Vector<Book>();

		Book book;

		DatabaseAdapter databaseAdapter = new DatabaseAdapter(ctx);
		databaseAdapter.open();

		// Get all sampleData from db
		Cursor cursor = databaseAdapter.getAllBooks();
		cursor.moveToFirst();
		
		if (cursor.getCount() != 0) {
			bookList = new Vector<Book>();
			do {
				book = new Book();
				
				book.setAuthor(cursor.getString(cursor.getColumnIndex("Author")));
				book.setCountry(cursor.getString(cursor
						.getColumnIndex("Country")));
				book.setLanguage(cursor.getString(cursor
						.getColumnIndex("Language")));
				book.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
				book.setYear(cursor.getString(cursor.getColumnIndex("Year")));
				bookList.add(book);
			} while (cursor.moveToNext());
		}

		// deactivate and closing cursor
		cursor.deactivate();
		cursor.close();

		// Closing db connection
		databaseAdapter.close();

		Collections.sort(bookList);
		return bookList;
	}

}
