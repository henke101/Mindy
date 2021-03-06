package se.chalmers.mindy.util;

import java.util.ArrayList;
import java.util.Calendar;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.view.DiaryItem;
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
	private static final int DATABASE_VERSION = 12;

	private static final String TABLE_EVALUATION_QUESTIONS = "TestQuestions";
	private static final String TABLE_EVALUATION_RESULTS = "TestResults";
	private static final String TABLE_EXERCISES = "Exercises";
	private static final String TABLE_THREE_POSITIVE = "ThreePositives";
	private static final String TABLE_DIARY = "diary";

	public static final String KEY_ROWID = "_id";

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

	// Diary table
	public static final String KEY_DIARY_TITLE = "_title";
	public static final String KEY_DIARY_BODY = "_body";
	public static final String KEY_DIARY_DATE = "_date";

	private static final String CREATE_TABLE_THREE_POSITIVE = "create table " + TABLE_THREE_POSITIVE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_THREE_POSITIVE_DATE + " INTEGER NOT NULL, " + KEY_THREE_POSITIVE_FIRST + " TEXT, "
			+ KEY_THREE_POSITIVE_SECOND + " TEXT, " + KEY_THREE_POSITIVE_THIRD + " TEXT);";

	private static final String CREATE_TABLE_EVALUATION_QUESTIONS = "create table " + TABLE_EVALUATION_QUESTIONS + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESTION_DESCRIPTION + " TEXT NOT NULL, " + KEY_QUESTION_TYPE + " INTEGER NOT NULL, "
			+ KEY_QUESTION_IMPORTANCE + " INTEGER NOT NULL);";

	private static final String CREATE_TABLE_EVALUATION_ANSWERS = "create table " + TABLE_EVALUATION_RESULTS + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ANSWER + " TEXT NOT NULL, " + KEY_DATE_ANSWERED + " INTEGER NOT NULL, " + KEY_QUESTION_ID
			+ " INTEGER, " + " FOREIGN KEY (" + KEY_QUESTION_ID + ") REFERENCES " + TABLE_EVALUATION_QUESTIONS + " (" + KEY_ROWID + "));";

	private static final String CREATE_TABLE_DIARY = "create table " + TABLE_DIARY + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_DIARY_TITLE + " TITLE NOT NULL, " + KEY_DIARY_BODY + " TEXT NOT NULL, " + KEY_DIARY_DATE + " INTEGER NOT NULL);";

	private static final String TAG = "MindyDatabaseAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;

	public MindyDatabaseAdapter(final Context ctx) {
		mContext = ctx;
	}

	/**
	 * Opens the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public MindyDatabaseAdapter open() throws SQLException {
		if (!isOpen()) {
			mDbHelper = new DatabaseHelper(mContext);
			mDb = mDbHelper.getWritableDatabase();
		}
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

	public boolean insertNewThreePositive(ThreePosItem pos) {

		ContentValues args = new ContentValues();
		args.put(KEY_THREE_POSITIVE_FIRST, pos.getPositiveOne());
		args.put(KEY_THREE_POSITIVE_SECOND, pos.getPositiveTwo());
		args.put(KEY_THREE_POSITIVE_THIRD, pos.getPositiveThree());

		if (pos.getDate() != null) {
			args.put(KEY_THREE_POSITIVE_DATE, pos.getDate().getTimeInMillis());
		} else {
			args.put(KEY_THREE_POSITIVE_DATE, Calendar.getInstance().getTimeInMillis());
		}

		return mDb.insert(TABLE_THREE_POSITIVE, null, args) > 0;
	}

	public ArrayList<ThreePosItem> fetchAllPositives() {
		ArrayList<ThreePosItem> items = new ArrayList<ThreePosItem>();

		Cursor threePosCursor = mDb.query(TABLE_THREE_POSITIVE, new String[] { KEY_ROWID, KEY_THREE_POSITIVE_FIRST, KEY_THREE_POSITIVE_SECOND,
				KEY_THREE_POSITIVE_THIRD, KEY_THREE_POSITIVE_DATE }, null, null, null, null, KEY_THREE_POSITIVE_DATE + " DESC");
		if (threePosCursor.moveToFirst()) {
			while (!threePosCursor.isAfterLast()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(threePosCursor.getLong(4));

				items.add(new ThreePosItem(calendar, "1. " + threePosCursor.getString(1), "2. " + threePosCursor.getString(2), "3. "
						+ threePosCursor.getString(3)));
				threePosCursor.moveToNext();
			}
		}

		threePosCursor.close();
		return items;
	}

	public ThreePosItem fetchLatestPositive() {

		Cursor threePosCursor = mDb.query(TABLE_THREE_POSITIVE, new String[] { KEY_ROWID, KEY_THREE_POSITIVE_FIRST, KEY_THREE_POSITIVE_SECOND,
				KEY_THREE_POSITIVE_THIRD, KEY_THREE_POSITIVE_DATE }, KEY_THREE_POSITIVE_DATE + " in (SELECT MAX(" + KEY_THREE_POSITIVE_DATE + ") AS "
				+ KEY_THREE_POSITIVE_DATE + " FROM " + TABLE_THREE_POSITIVE + ")", null, null, null, null);

		if (threePosCursor.moveToFirst()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(threePosCursor.getLong(4));

			ThreePosItem result = new ThreePosItem(calendar, "1. " + threePosCursor.getString(1), "2. " + threePosCursor.getString(2), "3. "
					+ threePosCursor.getString(3));

			threePosCursor.close();
			return result;

		} else {
			// Table is empty

			return new ThreePosItem(mContext.getString(R.string.index_threepos_default_first), mContext.getString(R.string.index_threepos_default_second),
					mContext.getString(R.string.index_threepos_default_third));
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

	public void deleteAllPositives() {
		if (isOpen()) {
			mDb.delete(TABLE_THREE_POSITIVE, null, null);
		}
	}

	public long createNote(DiaryItem note) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DIARY_TITLE, note.getTitle());
		initialValues.put(KEY_DIARY_BODY, note.getBody());
		initialValues.put(KEY_DIARY_DATE, note.getDate().getTimeInMillis());
		return mDb.insert(TABLE_DIARY, null, initialValues);
	}

	public boolean deleteNote(long rowId) {

		return mDb.delete(TABLE_DIARY, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public ArrayList<Integer> fetchAllNoteIds() {
		ArrayList<Integer> items = new ArrayList<Integer>();

		Cursor entryCursor = mDb.query(TABLE_DIARY, new String[] { KEY_ROWID }, null, null, null, null, KEY_DIARY_DATE + " DESC");

		if (entryCursor.moveToFirst()) {
			while (!entryCursor.isAfterLast()) {
				items.add(entryCursor.getInt(0));
				entryCursor.moveToNext();
			}
		}

		return items;
	}

	public ArrayList<DiaryItem> fetchAllNotes() {
		ArrayList<DiaryItem> items = new ArrayList<DiaryItem>();

		Cursor entryCursor = mDb.query(TABLE_DIARY, new String[] { KEY_ROWID, KEY_DIARY_TITLE, KEY_DIARY_BODY, KEY_DIARY_DATE }, null, null, null, null,
				KEY_DIARY_DATE + " DESC");

		if (entryCursor.moveToFirst()) {
			Calendar cal = Calendar.getInstance();
			while (!entryCursor.isAfterLast()) {
				cal.setTimeInMillis(entryCursor.getLong(3));

				items.add(new DiaryItem(mContext, entryCursor.getString(1), entryCursor.getString(2), cal));

				entryCursor.moveToNext();
			}
		} else {
			items.add(new DiaryItem(mContext, mContext.getString(R.string.diary_no_entries), mContext.getString(R.string.diary_no_entries_desc), null));
		}

		return items;
	}

	public DiaryItem fetchNote(long rowId) {

		return fetchNoteWhere(KEY_ROWID + "=" + rowId);
	}

	public DiaryItem fetchLatestNote() {

		return fetchNoteWhere(KEY_DIARY_DATE + " in (SELECT MAX(" + KEY_DIARY_DATE + ") AS " + KEY_DIARY_DATE + " FROM " + TABLE_DIARY + ")");

	}

	public DiaryItem fetchNoteWhere(String where) {
		Cursor entryCursor = mDb.query(true, TABLE_DIARY, new String[] { KEY_ROWID, KEY_DIARY_TITLE, KEY_DIARY_BODY, KEY_DIARY_DATE }, where, null, null, null,
				null, null);

		DiaryItem item;
		if (entryCursor.moveToFirst()) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(entryCursor.getLong(3));

			item = new DiaryItem(mContext, entryCursor.getString(1), entryCursor.getString(2), cal);
		} else {
			item = new DiaryItem(mContext, "", "", Calendar.getInstance());
		}

		return item;
	}

	public boolean updateNote(long rowId, DiaryItem note) {
		ContentValues args = new ContentValues();
		args.put(KEY_DIARY_TITLE, note.getTitle());
		args.put(KEY_DIARY_BODY, note.getBody());

		return mDb.update(TABLE_DIARY, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(final Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {

			db.execSQL(CREATE_TABLE_THREE_POSITIVE);
			db.execSQL(CREATE_TABLE_EVALUATION_QUESTIONS);
			db.execSQL(CREATE_TABLE_EVALUATION_ANSWERS);
			db.execSQL(CREATE_TABLE_DIARY);

		}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
			Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_THREE_POSITIVE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATION_QUESTIONS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATION_RESULTS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
			onCreate(db);

		}
	}

}
