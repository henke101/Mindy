package se.chalmers.mindy.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MindyDatabaseAdapter {

	// Database properties
	private static final String DATABASE_NAME = "mindy_db";
	private static final int DATABASE_VERSION = 3;

	private static final String TABLE_TEST_QUESTIONS = "TestQuestions";
	private static final String TABLE_TEST_RESULTS = "TestResults";
	private static final String TABLE_EXERCISES = "Exercises";
	private static final String TABLE_THREE_POSITIVE = "ThreePositives";

	private static final String KEY_ROWID = "_id";

	// Test question table
	private static final String KEY_QUESTION_DESCRIPTION = "_question";
	private static final String KEY_QUESTION_TYPE = "_type";
	private static final String KEY_QUESTION_IMPORTANCE = "_importance";

	// Test result table
	private static final String KEY_QUESTION_ID = "_question_id";
	private static final String KEY_ANSWER = "_answer";
	private static final String KEY_DATE_ANSWERED = "_date_answered";

	// Exercise table
	private static final String KEY_EXERCISE_TYPE = "_type";
	private static final String KEY_EXERCISE_TITLE = "_title";
	private static final String KEY_EXERCISE_DESCRIPTION = "_description";
	private static final String KEY_RESOURCE_ID = "_resource_id";
	private static final String KEY_TIMES_PERFORMED = "_times_performed";
	private static final String KEY_TIMES_DISMISSED = "_times_dismissed";
	private static final String KEY_PERMANENTLY_DISMISSED = "_permanently_dismissed";

	// Three positive table
	private static final String KEY_THREE_POSITIVE_DATE = "_date";
	private static final String KEY_THREE_POSITIVE_FIRST = "_first";
	private static final String KEY_THREE_POSITIVE_SECOND = "_second";
	private static final String KEY_THREE_POSITIVE_THIRD = "_third";

	private static final String CREATE_TABLE_THREE_POSITIVE = "create table " + TABLE_THREE_POSITIVE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_THREE_POSITIVE_DATE + " DATETIME NOT NULL, " + KEY_THREE_POSITIVE_FIRST + " TEXT, "
			+ KEY_THREE_POSITIVE_SECOND + " TEXT, " + KEY_THREE_POSITIVE_THIRD + " TEXT);";

	private static final String TAG = "MindyDatabaseAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	/*
	private static final String CREATE_TABLE_ALARMS = "create table " + DATABASE_TABLE_ALARMS + " (" + KEY_ROWID + " INTEGER PRIMARY KEY, " + KEY_FROM
			+ " INTEGER NOT NULL, " + KEY_TO + " INTEGER NOT NULL, " + KEY_SUN + " BOOLEAN NOT NULL, " + KEY_MON + " BOOLEAN NOT NULL, " + KEY_TUE
			+ " BOOLEAN NOT NULL, " + KEY_WED + " BOOLEAN NOT NULL, " + KEY_THU + " BOOLEAN NOT NULL, " + KEY_FRI + " BOOLEAN NOT NULL, " + KEY_SAT
			+ " BOOLEAN NOT NULL, " + KEY_VIBRATION + " BOOLEAN NOT NULL, " + KEY_MEDIA + " BOOLEAN NOT NULL, " + KEY_LOCK + " BOOLEAN NOT NULL, "
			+ KEY_UNMUTE_ON_CALL + " BOOLEAN NOT NULL, " + KEY_DISABLE_NOTIFICATION_LIGHT + " BOOLEAN NOT NULL, " + KEY_BRIGHTNESS + " INTEGER NOT NULL);";
	 */
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

	public boolean isOpen() {
		return mDb != null && mDb.isOpen();
	}

	public boolean insertNewThreePositive(TempThreePos pos) {

		ContentValues args = new ContentValues();
		args.put(KEY_THREE_POSITIVE_FIRST, pos.getFirst());
		args.put(KEY_THREE_POSITIVE_SECOND, pos.getSecond());
		args.put(KEY_THREE_POSITIVE_THIRD, pos.getThird());

		if (pos.getDate() != null) {
			args.put(KEY_THREE_POSITIVE_DATE, Tools.getStringFromDate(pos.getDate()));
		} else {
			args.put(KEY_THREE_POSITIVE_DATE, Tools.getCurrentDateTime());
		}

		return mDb.insert(TABLE_THREE_POSITIVE, null, args) > 0;
	}

	public ArrayList<TempThreePos> fetchAllPositives() {
		ArrayList<TempThreePos> items = new ArrayList<TempThreePos>();

		Cursor threePosCursor = mDb.query(TABLE_THREE_POSITIVE, new String[] { KEY_ROWID, KEY_THREE_POSITIVE_FIRST, KEY_THREE_POSITIVE_SECOND,
				KEY_THREE_POSITIVE_THIRD, KEY_THREE_POSITIVE_DATE }, null, null, null, null, null);
		if (threePosCursor.moveToFirst()) {
			while (!threePosCursor.isAfterLast()) {

				items.add(new TempThreePos(threePosCursor.getString(1), threePosCursor.getString(2), threePosCursor.getString(3), Tools
						.getDateFromString(threePosCursor.getString(4))));
				threePosCursor.moveToNext();
			}
		}

		return items;
	}

	public TempThreePos fetchLatestPositive() {

		Cursor threePosCursor = mDb.query(TABLE_THREE_POSITIVE, new String[] { KEY_ROWID, KEY_THREE_POSITIVE_FIRST, KEY_THREE_POSITIVE_SECOND,
				KEY_THREE_POSITIVE_THIRD, KEY_THREE_POSITIVE_DATE }, KEY_THREE_POSITIVE_DATE + " in (SELECT MAX(" + KEY_THREE_POSITIVE_DATE + ") AS "
				+ KEY_THREE_POSITIVE_DATE + " FROM " + TABLE_THREE_POSITIVE + ")", null, null, null, null);

		if (threePosCursor.moveToFirst()) {
			return new TempThreePos(threePosCursor.getString(1), threePosCursor.getString(2), threePosCursor.getString(3),
					Tools.getDateFromString(threePosCursor.getString(4)));

		} else {
			return null;
		}
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
	public boolean insertNewTestResults() {
		return true;
	}

	/**
	 * Updates an existing set of test results in the database. Returns true if the data was updated.
	 * @return the success of the update
	 */
	public boolean updateTestResults() {
		return true;
	}

	public void deleteAllPositives() {
		if (isOpen()) {
			mDb.delete(TABLE_THREE_POSITIVE, null, null);
		}
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(final Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {

			db.execSQL(CREATE_TABLE_THREE_POSITIVE);

		}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
			Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_THREE_POSITIVE);
			onCreate(db);

		}
	}

}
