package com.esgi.studyingfurther;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;

import java.util.ArrayList;

import java.util.HashMap;
import android.app.AlertDialog;
import android.view.View;

import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsFeed extends Activity {

	private ListView maListViewPerso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);

		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map;

		for (int i = 1; i < 20; i++) {
			map = new HashMap<String, String>();
			map.put("titre", getResources().getString(R.string.Title));
			map.put("description",
					getResources().getString(R.string.Description));
			map.put("img", String.valueOf(R.drawable.android));
			if (i % 2 == 0) {
				map.put("newspic", String.valueOf(R.drawable.bout));
			}
			map.put("heurPub", Time.MONTH_DAY + ":" + Time.HOUR);
			listItem.add(map);

		}

		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),
				listItem, R.layout.activity_item_news_feed, new String[] {
						"img", "titre", "description", "newspic", "heurPub" },
				new int[] { R.id.list_image, R.id.title, R.id.news,
						R.id.newspic, R.id.heurPub });
		maListViewPerso.setAdapter(mSchedule);
	}

	// Action Click
	/*
	 * maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * @Override
	 * 
	 * @SuppressWarnings("unchecked") public void onItemClick(AdapterView<?> a,
	 * View v, int position, long id) { HashMap<String, String> map =
	 * (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	 * AlertDialog.Builder adb = new AlertDialog.Builder(NewsFeed.this);
	 * adb.setTitle("Selection Item");
	 * adb.setMessage("Votre choix : "+map.get("titre"));
	 * adb.setPositiveButton("Ok", null); adb.show(); } }); }
	 */

	// @Override
	/*
	 * public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.news_feed, menu); return true; }
	 */
	

	public void Modification(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Modifier");
		adb.setMessage("Vous avez appuiez sur le button Modifier");
		adb.setPositiveButton("Ok", null);
		adb.show();

	}

	public void Pub(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Publier");
		adb.setMessage("Vous avez appuiez sur le button Publier");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

	public void Write(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Write");
		adb.setMessage("Vous avez appuiez sur le button Write");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

}
