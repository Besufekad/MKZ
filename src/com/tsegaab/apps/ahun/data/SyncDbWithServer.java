package com.tsegaab.apps.ahun.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import com.tsegaab.apps.ahun.Consts;

import android.os.AsyncTask;
import android.util.Log;

public class SyncDbWithServer extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... urls) {
		String excutionState = null;
		try {
			excutionState = "Inside SyncDbWithServer try";
			Log.d(Consts.Z_TAG, excutionState);
			Xml2ZennaParser xml2z = new Xml2ZennaParser();
			Consts.controller.zennaListToDb(xml2z.parse(downloadUrl(urls[0])));
			excutionState = "success";
			return excutionState;
		} catch (IOException e) {
			Log.d(Consts.EZ_TAG, e.toString());
		} catch (XmlPullParserException e) {
			Log.d(Consts.EZ_TAG, e.toString());
		}
		
		return excutionState;
	}

	@Override
	protected void onPostExecute(String result) {
		// refresh the channel
	}
	
	private InputStream getLocalXml(String xmlPath) {
		InputStream inputStream = null;
		try {
			
			inputStream = Consts.context.getAssets().open(xmlPath) ;
			 
		} catch (FileNotFoundException e) {
			Log.d(Consts.EZ_TAG, e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(Consts.EZ_TAG, e.toString());
			e.printStackTrace();
		}
		return inputStream;
	}
	private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

}
