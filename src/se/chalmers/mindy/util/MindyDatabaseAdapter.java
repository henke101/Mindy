package se.chalmers.mindy.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MindyDatabaseAdapter {

	// Database properties
	private static final String DATABASE_NAME = "mindy_db";
	private static final int DATABASE_VERSION = 1;

	private static final String TAG = "MindyDatabaseAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	public MindyDatabaseAdapter(final Context ctx) {
		mCtx = ctx;
	}

	/**
	 * Opens the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public MindyDatabaseAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();

		return this;
	}

	/**
	 * Closes the database.
	 */
	public void close() {
		mDbHelper.close();
	}

	/**
	 * Fetches test results from the database and returns it as a TestResult instance
	 * @return a TestResult instance containing the test results
	 */
	public EvaluationResult fetchTestResults() {
		return new EvaluationResult();
	}

	/**
	 * Inserts a new set of test results in the database. Returns true if the data was inserted.
	 * @return the success of the insertion
	 */
	public boolean insertNewTestResults(EvaluationResult result) {
		return true;
	}

	/**
	 * Updates an existing set of test results in the database. Returns true if the data was updated.
	 * @return the success of the update
	 */
	public boolean updateTestResults(EvaluationResult result) {
		return true;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(final Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {

			// db.execSQL(CREATE_TABLE_ALARMS);

		}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			// db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ALARMS);
			onCreate(db);

		}
	}

}
