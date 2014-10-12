package com.fstrise.androidexample.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fstrise.androidexample.model.itemList;

public class MySQLiteHelper extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 32;
	// Database Name
	private static final String DATABASE_NAME = "libdev";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// favorite table name
	private static final String TABLE_Item = "item";
	// favorite Columns names
	private static final String KEY_ID_ = "id_";
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_TITLE = "title";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_URLIMAGE = "urlimage";
	private static final String KEY_AUTHOR = "author";
	private static final String KEY_IMAGEAUTHOR = "imgauthor";
	private static final String KEY_LINKDOWNLOAD = "linkdownload";
	private static final String KEY_VERSION = "version";
	private static final String KEY_LICENSE = "license";
	private static final String KEY_ISFAVORITE = "isfavorite";
	private static final String KEY_ISVIEW = "isview";

	//
	private static final String[] COLUMNS = { KEY_ID_, KEY_ID, KEY_TYPE,
			KEY_TITLE, KEY_DESCRIPTION, KEY_URLIMAGE, KEY_AUTHOR,
			KEY_IMAGEAUTHOR, KEY_LINKDOWNLOAD, KEY_VERSION, KEY_LICENSE,
			KEY_ISFAVORITE, KEY_ISVIEW };

	private static final String TABLE_SNIPPET = "snippets";
	private static final String SNP_ID_ = "id_";
	private static final String SNP_POSITION = "position";
	private static final String SNP_NAME = "name";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_ITEM_TABLE = "CREATE TABLE item ( "
				+ "id_ INTEGER PRIMARY KEY AUTOINCREMENT, " + "id INTEGER, "
				+ "type INTEGER, " + "title TEXT, " + "description TEXT, "
				+ "urlimage TEXT, " + "author TEXT, " + "imgauthor TEXT, "
				+ "linkdownload TEXT, " + "version INTEGER, "
				+ "license TEXT, " + "isfavorite TEXT, " + "isview INTEGER  )";
		// create table
		db.execSQL(CREATE_ITEM_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS item");
		// Create tables again
		onCreate(db);
	}

	public void deleteTableFavorite() {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.delete(TABLE_Item, null, null);
			db.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (db.isOpen())
				db.close();
		}
	}

	public int updateFavorite(itemList obj) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ISFAVORITE, obj.getIsFav()); //
		// 3. updating row
		int i = db.update(TABLE_Item, // table
				values, // column/value
				KEY_ID + " = ? ", // selections
				new String[] { String.valueOf(obj.getId()) });
		db.close();

		return i;

	}

	public int isView(itemList obj) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ISVIEW, obj.getIsView()); //
		// 3. updating row
		int i = db.update(TABLE_Item, // table
				values, // column/value
				KEY_ID + " = ? ", // selections
				new String[] { String.valueOf(obj.getId()) });
		db.close();

		return i;

	}

	// Favorite
	public void addItem(itemList itemL) {
		try {
			Log.d("addFav", TABLE_Item.toString());
			// 1. get reference to writable DB
			SQLiteDatabase db = this.getWritableDatabase();
			// 2. create ContentValues to add key "column"/value
			ContentValues values = new ContentValues();
			values.put(KEY_ID, itemL.getId());
			values.put(KEY_TYPE, itemL.getId_menu());
			values.put(KEY_TITLE, itemL.getTitle());
			values.put(KEY_DESCRIPTION, itemL.getDescription());
			values.put(KEY_URLIMAGE, itemL.getImages());
			values.put(KEY_AUTHOR, itemL.getAuthor());
			values.put(KEY_IMAGEAUTHOR, itemL.getImg_author());
			values.put(KEY_LINKDOWNLOAD, itemL.getLink());
			values.put(KEY_VERSION, itemL.getVersion());
			values.put(KEY_LICENSE, itemL.getLicense());
			values.put(KEY_ISFAVORITE, itemL.getIsFav());
			values.put(KEY_ISVIEW, itemL.getIsView());

			// 3. insert
			db.insert(TABLE_Item, // table
					null, // nullColumnHack
					values); // key/value -> keys = column names/ values =
								// column values
			// 4. close
			db.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public itemList getItem(int id) {
		// type: 1 live, 2 movie
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		// 2. build query
		Cursor cursor = db.query(TABLE_Item, // a. table
				COLUMNS, // b. column names
				"  id = ?", // c. selections
				new String[] { String.valueOf(id) }, // d.
														// selections
														// args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit
		// 3. if we got results get the first one
		itemList obj = null;
		if (cursor.moveToFirst()) {
			do {
				obj = new itemList();
				obj.setId(cursor.getInt(1));
				obj.setId_menu(cursor.getInt(2));
				obj.setTitle(cursor.getString(3));
				obj.setDescription(cursor.getString(4));
				obj.setImages(cursor.getString(5));
				obj.setAuthor(cursor.getString(6));
				obj.setImg_author(cursor.getString(7));
				obj.setLink(cursor.getString(8));
				obj.setVersion(cursor.getInt(9));
				obj.setLicense(cursor.getString(10));
				obj.setIsFav(cursor.getInt(11));
				obj.setIsView(cursor.getInt(12));
			} while (cursor.moveToNext());
		}
		if (db.isOpen()) {
			cursor.close();
			db.close();
		}
		return obj;
	}

	public ArrayList<itemList> getAllItem(int type) {
		ArrayList<itemList> arrItem = new ArrayList<itemList>();
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_Item + " WHERE type = " + type;
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// 3. go over each row, build fav and add it to list
		itemList obj = null;
		if (cursor.moveToFirst()) {
			do {
				obj = new itemList();
				obj.setId(cursor.getInt(1));
				obj.setId_menu(cursor.getInt(2));
				obj.setTitle(cursor.getString(3));
				obj.setDescription(cursor.getString(4));
				obj.setImages(cursor.getString(5));
				obj.setAuthor(cursor.getString(6));
				obj.setImg_author(cursor.getString(7));
				obj.setLink(cursor.getString(8));
				obj.setVersion(cursor.getInt(9));
				obj.setLicense(cursor.getString(10));
				obj.setIsFav(cursor.getInt(11));
				obj.setIsView(cursor.getInt(12));
				arrItem.add(obj);
			} while (cursor.moveToNext());
		}
		if (db.isOpen()) {
			cursor.close();
			db.close();
		}
		return arrItem;
	}

	public ArrayList<itemList> getAllItem() {
		ArrayList<itemList> arrItem = new ArrayList<itemList>();
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_Item;
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// 3. go over each row, build fav and add it to list
		itemList obj = null;
		if (cursor.moveToFirst()) {
			do {
				obj = new itemList();
				obj.setId(cursor.getInt(1));
				obj.setId_menu(cursor.getInt(2));
				obj.setTitle(cursor.getString(3));
				obj.setDescription(cursor.getString(4));
				obj.setImages(cursor.getString(5));
				obj.setAuthor(cursor.getString(6));
				obj.setImg_author(cursor.getString(7));
				obj.setLink(cursor.getString(8));
				obj.setVersion(cursor.getInt(9));
				obj.setLicense(cursor.getString(10));
				obj.setIsFav(cursor.getInt(11));
				obj.setIsView(cursor.getInt(12));
				arrItem.add(obj);
			} while (cursor.moveToNext());
		}
		if (db.isOpen()) {
			cursor.close();
			db.close();
		}
		return arrItem;
	}

	public ArrayList<itemList> searchByKW(String kw) {
		ArrayList<itemList> arrItem = new ArrayList<itemList>();
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_Item + " WHERE title LIKE '%"
				+ kw + "%'";
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// 3. go over each row, build fav and add it to list
		itemList obj = null;
		if (cursor.moveToFirst()) {
			do {
				obj = new itemList();
				obj.setId(cursor.getInt(1));
				obj.setId_menu(cursor.getInt(2));
				obj.setTitle(cursor.getString(3));
				obj.setDescription(cursor.getString(4));
				obj.setImages(cursor.getString(5));
				obj.setAuthor(cursor.getString(6));
				obj.setImg_author(cursor.getString(7));
				obj.setLink(cursor.getString(8));
				obj.setVersion(cursor.getInt(9));
				obj.setLicense(cursor.getString(10));
				obj.setIsFav(cursor.getInt(11));
				obj.setIsView(cursor.getInt(12));
				arrItem.add(obj);
			} while (cursor.moveToNext());
		}
		if (db.isOpen()) {
			cursor.close();
			db.close();
		}
		return arrItem;
	}

	public ArrayList<itemList> getAllItemFav() {
		ArrayList<itemList> arrItem = new ArrayList<itemList>();
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_Item + " WHERE "
				+ KEY_ISFAVORITE + " = 1";
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// 3. go over each row, build fav and add it to list
		itemList obj = null;
		if (cursor.moveToFirst()) {
			do {
				obj = new itemList();
				obj.setId(cursor.getInt(1));
				obj.setId_menu(cursor.getInt(2));
				obj.setTitle(cursor.getString(3));
				obj.setDescription(cursor.getString(4));
				obj.setImages(cursor.getString(5));
				obj.setAuthor(cursor.getString(6));
				obj.setImg_author(cursor.getString(7));
				obj.setLink(cursor.getString(8));
				obj.setVersion(cursor.getInt(9));
				obj.setLicense(cursor.getString(10));
				obj.setIsFav(cursor.getInt(11));
				obj.setIsView(cursor.getInt(12));
				Log.i("LogPro", "Get Fav:  " + obj.getTitle());
				arrItem.add(obj);
			} while (cursor.moveToNext());
		}
		if (db.isOpen()) {
			cursor.close();
			db.close();
		}
		return arrItem;
	}

	public void deleteTableSnippet() {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.delete(TABLE_SNIPPET, null, null);
			db.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (db.isOpen())
				db.close();
		}
	}

}
