package com.tsegaab.apps.ahun;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsegaab.apps.ahun.data.SyncDbWithServer;
import com.tsegaab.apps.ahun.data.Zenna;

public class MainActivity extends Activity {
	
	private DBContoller controller;
	private String sampleXml = "xml/samplenews.xml";
	private String sampleUrl = "http://10.0.0.28:80/znews_handler/index.php/news/allnews/format/xml";
	private ViewGroup newsContainerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Is this the first time? Yes doFirstTimeTngs()
		// with new thread fill layout from database starting from current
		// channel
		// if no connection display No connection... else fetch xml from server
		controller = new DBContoller(getApplicationContext());
		Consts.controller = controller;
		Consts.context = getApplicationContext();
		loadNews();
		newsContainerView = (ViewGroup) findViewById(R.id.news_container);
		refreshPage();
	}

	public void refreshPage() {
		ArrayList<Zenna> zwoch = controller.getZennawochFromDbTable();
		for(int x = 0; x < zwoch.size(); x++) {
			addItem(zwoch.get(x));			
		}		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void loadNews() {
		Log.d(Consts.Z_TAG, "loadingNews Method with xml" + sampleXml);
		if (new SyncDbWithServer().execute(sampleUrl).equals("success")) {
			Log.d(Consts.Z_TAG, "loadNews successfull Refreshing page");
			refreshPage();
		}
		
	}
	
	private void addItem(Zenna z) {
        // Instantiate a new "row" view.
		
		Log.d(Consts.Z_TAG, "addItem");
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.news_card, newsContainerView, false);

        ((TextView) newView.findViewById(R.id.news_title)).setText(z.getTitle());
        ((ImageView) newView.findViewById(R.id.news_image)).setImageResource(R.drawable.ic_launcher);
        ((TextView) newView.findViewById(R.id.news_content)).setText(z.getFullContent());
        
        newsContainerView.addView(newView, 0);
    }

}
