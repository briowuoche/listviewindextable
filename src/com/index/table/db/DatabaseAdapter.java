package com.index.table.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {

	private static final String TAG = "DataBaseAdapter";

	private static final String DATABASE_NAME = "book.sqlite";

	private static final int DATABASE_VERSION = 1;

	private static final String BOOK = "BOOK";

	private final Context context;

	private DatabaseHelper dBHelper;

	private SQLiteDatabase db;

	public DatabaseAdapter(final Context ctx) {
		this.context = ctx;
		dBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static String DB_PATH;

		private final Context myContext;

		DatabaseHelper(final Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.myContext = context;
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases";
			Log.i("DataBaseAdapter", "DATABASE_NAME " + DATABASE_NAME);
			try {
				createDataBase();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void createDataBase() throws IOException {

			boolean dbExist = checkDataBase();
			Log.i(TAG, "dbExist " + dbExist);

			if (dbExist) {
				// do nothing - database already exist
			} else {

				// By calling this method and empty database will be created
				// into the default system path of your application so we are
				// gonna be able to overwrite that database with our database.
				this.getReadableDatabase();

				try {
					copyDataBase();
				} catch (IOException e) {
					throw new Error("Error copying database");
				}
			}
		}

		private boolean checkDataBase() {

			SQLiteDatabase checkDB = null;

			try {
				String myPath = DB_PATH + "/" + DATABASE_NAME;
				Log.i(TAG, "myPath " + myPath);
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READONLY);
			} catch (SQLiteException e) {
				// database does't exist yet.
			}

			if (checkDB != null) {
				checkDB.close();
			}

			return checkDB != null ? true : false;
		}

		private void copyDataBase() throws IOException {

			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

			// Path to the just created empty db
			String outFileName = DB_PATH + "/" + DATABASE_NAME;
			Log.i(TAG, "outFileName " + outFileName);
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

	// ---opens the database---
	public DatabaseAdapter open() throws SQLException {
		Log.i(TAG, "open myPath ");
		try {
			String myPath = "/data/data/" + context.getPackageName()
					+ "/databases" + "/" + DATABASE_NAME;
			Log.i(TAG, "open myPath " + myPath);
			db = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
			Log.i(TAG, "Db opened " + db);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	// ---closes the database---
	public void close() {
		db.close();
	}

	public Cursor getAllBooks() {
		return db.query(BOOK, null, null, null, null, null, null);
	}
}
