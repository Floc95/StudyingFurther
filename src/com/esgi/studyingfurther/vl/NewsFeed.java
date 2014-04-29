package com.esgi.studyingfurther.vl;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.view.Menu;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.esgi.studyingfurther.R;

public class NewsFeed extends Activity {

	private ListView maListViewPerso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);

        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
 

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

       HashMap<String, String> map;

        for(int i=1;i<20;i++)
        {
                map = new HashMap<String, String>();
                map.put("titre", "Post " + i);
                map.put("description", "Evenemet " + i);
                map.put("img", String.valueOf(R.drawable.android));
                map.put("heurPub", Time.getCurrentTimezone());
                listItem.add(map);

        }


 
       SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.activity_affichageitem,new String[] {"img", "titre", "description","heurPub"}, new int[] {R.id.list_image, R.id.title, R.id.news,R.id.heurPub});
       maListViewPerso.setAdapter(mSchedule);

 // Action Click
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			@Override
        	@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	     		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
         		AlertDialog.Builder adb = new AlertDialog.Builder(NewsFeed.this);
        		adb.setTitle("Selection Item");
        		adb.setMessage("Votre choix : "+map.get("titre"));
         		adb.setPositiveButton("Ok", null);
        		adb.show();
        	}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_feed, menu);
		return true;
	}


}
