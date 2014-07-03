
package com.esgi.studyingfurther;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.esgi.studyingfurther.bl.Post;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.CustomAdapter;
import com.esgi.studyingfurther.vm.MainViewModel;

public class NewsFeed extends Activity {

	private ListView maListViewPerso;
	private JSONObject currentUser;
	MainViewModel Manager = null;
	JSONArray news;
	//ArrayList<JSONObject> comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		if (MainViewModel.isNetworkAvailable(this)) {

			try {

				this.currentUser = new JSONObject(getIntent().getExtras().getString("currentUser"));

				// Call a function getNews for fix all post of a current user
				this.news = new Repository().getNews(this.currentUser.getInt("id"));
				// Persistance for deconnect mode
				android.content.SharedPreferences prefs = getSharedPreferences("news", 0);
				android.content.SharedPreferences.Editor editor = prefs.edit();
				editor.putString("news",this.news.toString());
				editor.commit();
				//
				CustomAdapter adapter = new CustomAdapter(this,new Post().getPosts(this.news,this.currentUser.getInt("statut"),1));

				maListViewPerso.setAdapter(adapter);

			} catch (JSONException e) {

			} catch (InterruptedException e) {

			} catch (ExecutionException e) {

			} catch (UnsupportedEncodingException e) {

			}

		} else {
			MainViewModel.alertNetwork(this);
			android.content.SharedPreferences prefs = getSharedPreferences("news", 0);
		    String news = prefs.getString("news","");
		    try {
				this.news=new JSONArray(news);
				android.content.SharedPreferences prefc = getSharedPreferences("UserData", 0);
				String currentUser = prefc.getString("currentuser","");
				this.currentUser = new JSONObject(currentUser);
				CustomAdapter adapter=new CustomAdapter(this, new Post().getPosts(this.news, this.currentUser.getInt("statut"),0));
			    maListViewPerso.setAdapter(adapter);	
			
			} catch (JSONException e) {
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		 
		    /*	if(!news.isEmpty())
		    	{
		    		
				
				 
			
	         	CustomAdapter adapter = new CustomAdapter(this,new Post().getPosts(this.news,this.currentUser.getInt("statut")));
				maListViewPerso.setAdapter(adapter);
		    	}
				*/
			
		}

	}

	/*
	 * Méthode appelée lors de la sélection d'un élément du menu
	 * 
	 * @param item
	 */
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	//	write(item.getItemId()+": " +item.getOrder());
	    switch (item.getOrder()) {
	        case 1:
	        	android.content.SharedPreferences prefs = getSharedPreferences("UserData", 0);
				android.content.SharedPreferences.Editor editor = prefs.edit();
				editor.putString("currentuser","");
				editor.commit();
				
			    prefs = getSharedPreferences("news", 0);
			    editor = prefs.edit();
				editor.putString("news","");
				
	        	Intent intent = new Intent(this, Identification.class);
				startActivity(intent);
				
	            return true;
	        case 2:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		
	};
	
	public void showDetailsPost(View v) throws JSONException, InterruptedException, ExecutionException {

   
	    Intent intent = new Intent(getBaseContext(), Comments.class);
		intent.putExtra("news",this.news.getJSONObject(maListViewPerso.getPositionForView(v)).toString());
		intent.putExtra("currentUser", this.currentUser.toString());
		startActivity(intent);
	}

	public void plusun(View v) throws JSONException, InterruptedException,
			ExecutionException {
		JSONObject currentPost = this.news.getJSONObject(maListViewPerso.getPositionForView(v));

		// Is the toggle on?

		boolean on = ((ToggleButton) v).isChecked();

		if (on) {
			// Remove plus one
			if (Post.removePlusOne(this.currentUser.getInt("id"),
					currentPost.getInt("id")).equals("86")) 
			{
				Toast.makeText(this,"Your plus one is successfully added",Toast.LENGTH_LONG).show();
			} 
			else {
				Toast.makeText(this,"Sorry:Error",Toast.LENGTH_LONG).show();
				
			}
			// ************
		} else {
			// Add plus one
			if (Post.addPlusOne(this.currentUser.getInt("id"),
					currentPost.getInt("id")).equals("86")) {
				Toast.makeText(this,"Your plus one is successfully removed",Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this,"Sorry:Error",Toast.LENGTH_LONG).show();
			}
		}

	}

	public void alert(String value) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Log");
		adb.setMessage(value);
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

}
