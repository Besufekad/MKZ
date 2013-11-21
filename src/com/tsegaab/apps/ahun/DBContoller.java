package com.tsegaab.apps.ahun;

import java.util.ArrayList;

import com.tsegaab.apps.ahun.data.Zenna;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBContoller extends SQLiteOpenHelper {

	public DBContoller(Context con) {
		super(con, Consts.Db.DB_SM, null, 1);
		Log.d(Consts.Z_TAG, Consts.Db.DB_SM + "CREATED");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.d(Consts.Z_TAG, "Creating table...");
		String quey = String
				.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ "%s TEXT, %s TEXT, %s VARCHAR(100), %s VARCHAR(100), %s VARCHAR(100))",
						Consts.Db.TABLE_SM, Consts.Db.TABLE_ID_COLM,
						Consts.Db.TABLE_TITLE_COLM,
						Consts.Db.TABLE_CONTENT_COLM, Consts.Db.TABLE_URL_COLM,
						Consts.Db.TABLE_CHANNEL_COLM,
						Consts.Db.TABLE_IMAGE_URL_COLM);
		db.execSQL(quey);

		Log.d(Consts.Z_TAG, "onCreate methode of DBController" + quey);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_old, int version_new) {
		Log.d(Consts.Z_TAG, "Updating database...");
		String query = "DROP TABLE IT EXISTS " + Consts.Db.TABLE_SM;
		db.execSQL(query);
		onCreate(db);
		Log.d(Consts.Z_TAG, query);
	}

	public ArrayList<Zenna> getZennawochFromDbTable() {

		Log.d(Consts.Z_TAG, "getZennawochFromDbTable");
		ArrayList<Zenna> zennawochFromDb = new ArrayList<Zenna>();
		Zenna z;
		String query = "SELECT * FROM " + Consts.Db.TABLE_SM;
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d(Consts.Z_TAG, "Excuting .. " + query);
		Cursor c = db.rawQuery(query, null);
		Log.d(Consts.Z_TAG, "Ecuting: " + query);
		if (c.moveToFirst()) {
			do {
				z = new Zenna(c.getString(1), c.getString(2), c.getString(3),
						c.getString(4), c.getString(5));
				zennawochFromDb.add(z);
			} while (c.moveToNext());
		}
		return zennawochFromDb;
	}

	public void zennaListToDb(ArrayList<Zenna> zennawoch) {

		Log.d(Consts.Z_TAG, "Adding Zenawoch to database....");
		Zenna z;
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < zennawoch.size(); i++) {
			z = zennawoch.get(i);
			String query = String
					.format("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
							Consts.Db.TABLE_SM, Consts.Db.TABLE_TITLE_COLM,
							Consts.Db.TABLE_CONTENT_COLM,
							Consts.Db.TABLE_URL_COLM,
							Consts.Db.TABLE_CHANNEL_COLM,
							Consts.Db.TABLE_IMAGE_URL_COLM, z.getTitle(),
							z.getFullContent(), z.getUrl(), z.getChannel(),
							z.getImageUrl());

			db.execSQL(query);
		}
	}
}
