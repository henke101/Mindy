package se.chalmers.mindy.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDbAdapter {
	// The underlying database
	private SQLiteDatabase mDb;
	private DiaryDatabaseHelper mDbHelper;

	// column names
	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "row";

	private static final String DATABASE_NAME = "mindy.dbTest";
	private static final String DATABASE_TABLE = "diary";
	private static final int DATABASE_VERSION = 2;
	private static final String TAG = "DiaryDbAdapter";

	public static final int TITLE_COLUMN = 1;
	public static final int BODY_COLUMN = 2;

	private final Context mCtx;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE =
			"create table diary (_id integer primary key autoincrement, "
					+ "title text not null, body text not null);";

	private static class DiaryDatabaseHelper extends SQLiteOpenHelper {

		DiaryDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("inside: ","Database onCreate");
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS diary");
			onCreate(db);
		}
	}
	public DiaryDbAdapter(Context ctx){
		this.mCtx = ctx;
		//mDbHelper = new DiaryDatabaseHelper(mCtx);
	}
	public boolean isOpen() {
        return mDb != null && mDb.isOpen();
    }
	public DiaryDbAdapter open() throws SQLException {
		if(!this.isOpen()){
			mDbHelper = new DiaryDatabaseHelper(mCtx);
			mDb = mDbHelper.getWritableDatabase();
			Log.i("inside: ","Database open");
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
//		if(mDb != null && mDb.isOpen())
//	        mDb.close();
	}
	public long createNote(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_BODY, body);
		Log.i("inside: ","createNote");
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteNote(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	public Cursor fetchAllNotes() {
		Log.i("inside: ","fetchAllNotes");
//		Cursor r = mDb.rawQuery(DATABASE_TABLE, new String [] {KEY_ID, KEY_TITLE,
//				KEY_BODY});
		Cursor r = mDb.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_BODY }, null, null, null, null, null);
		r.close();
		return r;
	}

	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

				mDb.query(true, DATABASE_TABLE, new String[] {KEY_ID, KEY_TITLE,
						KEY_BODY }, KEY_ID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		mCursor.close();
		return mCursor;
	}

	public boolean updateNote(long rowId, String title, String body) {
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);

		return mDb.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}
}
