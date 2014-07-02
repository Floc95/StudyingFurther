package com.esgi.studyingfurther;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.esgi.studyingfurther.bl.Comment;
import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.Post;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.CustomAdapter;
import com.esgi.studyingfurther.vm.CustomAdapterDetailsPost;
import com.esgi.studyingfurther.vm.DownloadImageTask;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;
import com.esgi.studyingfurther.vm.MyViewBinder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Comments extends Activity {

	// header of my view
	    public  ToggleButton plusun;
	    public  TextView titleP;
	    public  TextView contenu;
	    public  TextView heurPubP;
	    public  ImageView newspic;
	    public  ImageView avatarP;
	
	//
	
	
	private ListView listViewComment = null;
	private TextView commentText = null;
	private JSONObject jsonobjectFromFeedNews;
	//private JSONArray comments;
	private JSONObject currentUser = null;


	MainViewModel Manager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
         
		
		listViewComment = (ListView) findViewById(R.id.listviewComments);
		commentText = (TextView) findViewById(R.id.commentText);
		plusun = (ToggleButton) findViewById(R.id.plusun);
    	titleP = (TextView) findViewById(R.id.titleP);
    	contenu = (TextView) findViewById(R.id.contenu);
    	heurPubP = (TextView) findViewById(R.id.heurPubP);
    	newspic = (ImageView) findViewById(R.id.newspicdetails);
    	avatarP = (ImageView) findViewById(R.id.avatarP);
	
	try {
			
			if(MainViewModel.isNetworkAvailable(this))
			{
				this.jsonobjectFromFeedNews = new JSONObject(getIntent().getExtras().getString("news"));
				this.currentUser = new JSONObject(getIntent().getExtras().getString("currentUser"));
				MainViewModel.changeActionBarWithValueOfCurrentUser(this, this.getActionBar(), this.currentUser);
				// 
				this.setValuetheHeaderofListView(this.jsonobjectFromFeedNews);
				
				//get all comment
				CustomAdapterDetailsPost adapter = new CustomAdapterDetailsPost(this,new Comment().getComment(this.jsonobjectFromFeedNews,this.currentUser.getInt("statut")));

				listViewComment.setAdapter(adapter);

			}
			else{
				MainViewModel.alertNetwork(this);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressLint("NewApi")
	private void setValuetheHeaderofListView(JSONObject post) throws InterruptedException, ExecutionException, JSONException
	{
		   
			
		    
		Drawable avatar=new BitmapDrawable(this.getResources(), new DownloadImageTask().execute(ManagerURL.urlGetNews+post.getJSONObject("utilisateur").getString("avatar")).get());
		Drawable newspic=new BitmapDrawable(this.getResources(), new DownloadImageTask().execute(post.getString("image")).get());
	
		this.avatarP.setBackground(avatar);
		this.titleP.setText(post.getString("titre"));
		this.heurPubP.setText(post.getString("dateCreation"));
		this.contenu.setText("contenu");
		this.newspic.setBackground(newspic);
		
		
	}
	
	public void addComment(View v) throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException  {
	 
	if( Comment.addComment(this.currentUser.getInt("id"), this.jsonobjectFromFeedNews.getInt("id"), this.commentText.getText().toString()).equals("86"))
	{
		
		if(MainViewModel.isNetworkAvailable(this))
		{
		Toast.makeText(getBaseContext(),"Comment is added", Toast.LENGTH_LONG).show();
		//getComments();
		}else
		{
			MainViewModel.alertNetwork(this);
		}
		 this.commentText.setText("");
	}
	else 
	{
		
		Toast.makeText(getBaseContext(),"Error", Toast.LENGTH_LONG).show();
	}
	

	}

	public void pullToRefresh() throws InterruptedException, ExecutionException, JSONException
	{
		JSONArray getNewsForUpdate=new Repository().getNews(this.currentUser.getInt("id"));
		for (int i = 0; i < getNewsForUpdate.length(); i++) 
		{

			JSONObject row = getNewsForUpdate.getJSONObject(i);
			if(row.getInt("id")==this.jsonobjectFromFeedNews.getInt("id"))
			{
				this.jsonobjectFromFeedNews=new JSONObject();
				this.jsonobjectFromFeedNews=row;
				
				break;
			}
			
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comments, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}


