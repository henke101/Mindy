package se.chalmers.mindy.fragment;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DiaryDbAdapter extends ContentProvider{
	// The underlying database
	private SQLiteDatabase mDb;
	private DiaryDatabaseHelper mDbHelper;

	// column names
	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "row";

	private static final String DATABASE_NAME = "mindy.db";
	private static final String DATABASE_TABLE = "diary";
	private static final int DATABASE_VERSION = 1;
	
	public static final int TITLE_COLUMN = 1;
	public static final int BODY_COLUMN = 2;
	
	private final Context mCtx;
	
	@Override
	public boolean onCreate() {
		DiaryDatabaseHelper helper = new DiaryDatabaseHelper(getContext());
		mDb = helper.getWritableDatabase();
		return mDb != null;
	}
	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = String.format("create table %s (%s integer primary key autoincrement, %s text not null, %s text not null);",
			DATABASE_TABLE, KEY_ID, KEY_TITLE, KEY_BODY);

	private static class DiaryDatabaseHelper extends SQLiteOpenHelper {

		DiaryDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	public DiaryDbAdapter(Context ctx){
		this.mCtx = ctx;
        mDbHelper = new DiaryDatabaseHelper(mCtx);
	}
	public DiaryDbAdapter open() throws SQLException {
        mDbHelper = new DiaryDatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
	public long createNote(String title, String body) {
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_TITLE, title);
	initialValues.put(KEY_BODY, body);

	return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteNote(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	public Cursor fetchAllNotes() {

		return mDb.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_BODY }, null, null, null, null, null);
	}

	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_BODY }, KEY_ID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	 public boolean updateNote(long rowId, String title, String body) {
	 ContentValues args = new ContentValues();
	 args.put(KEY_TITLE, title);
	 args.put(KEY_BODY, body);
	
	 return mDb.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	 }
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
