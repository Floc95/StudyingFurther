package com.esgi.studyingfurther;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.esgi.studyingfurther.bl.User;
import com.esgi.studyingfurther.dal.Repository;

import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsFeed extends Activity {

	private ListView maListViewPerso;
	private int userId;
	private Bundle bundle;

	// Within which the entire activity is enclosed
	private DrawerLayout mDrawerLayout;
	 
	// ListView represents Navigation Drawer
	private ListView mDrawerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);
        try {
			getNews();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Méthode appelée lors de la sélection d'un élément du menu
	 * 
	 * @param item
	 */
	  /* Called whenever we call invalidateOptionsMenu() */  
	
	
 
private void getNews() throws InterruptedException, ExecutionException, JSONException
   {

		
	
		  this.userId=getIntent().getExtras().getInt("userId", 0);
		 // Log.v("NEWS",userId+"");
		  new Repository(this).getNews(userId);
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
				new int[] { R.id.list_image, R.id.title, R.id.news,R.id.newspic, R.id.heurPub }
		);
		 maListViewPerso.setAdapter(mSchedule);

   }
	public void modification(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Modifier");
		adb.setMessage("Vous avez appuiez sur le button Modifier");
		adb.setPositiveButton("Ok", null);
		adb.show();

	}

	public void pub(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Publier");
		adb.setMessage("Vous avez appuiez sur le button Publier");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

	public void write(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Write");
		adb.setMessage("Vous avez appuiez sur le button Write");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

}
