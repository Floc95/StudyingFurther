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
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.AddCommentTask;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;
import com.esgi.studyingfurther.vm.MyViewBinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Comments extends Activity {

	private ListView listViewComment = null;
	private ImageView avatarPost = null;
	private TextView title = null;
	private TextView heurPub = null;
	private TextView contenu = null;
	private AutoCompleteTextView commentText = null;
	private JSONObject jsonObjectFromFeedNews;
	private JSONArray comments;
	private JSONObject currentUser = null;
	private ArrayList<HashMap<String, Object>> listItem;
	private SimpleAdapter mSchedule;
	MainViewModel Manager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);

		listViewComment = (ListView) findViewById(R.id.listviewComments);
		avatarPost = (ImageView) findViewById(R.id.avatarP);
		title = (TextView) findViewById(R.id.titleP);
		heurPub = (TextView) findViewById(R.id.heurPubP);
		contenu = (TextView) findViewById(R.id.contenu);
		commentText = (AutoCompleteTextView) findViewById(R.id.commentText);
		try {
			Manager = new MainViewModel(new Factory());
			getComments();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getComments() throws UnsupportedEncodingException,
			InterruptedException, ExecutionException, JSONException {
          
		this.jsonObjectFromFeedNews = new JSONObject(getIntent().getExtras().getString("comments"));
		this.currentUser = new JSONObject(getIntent().getExtras().getString("currentUser"));
		pullToRefresh();
		this.comments = jsonObjectFromFeedNews.getJSONArray("commentaires");

		// ************ get Comment, avatar,date,tite and avatar of user

		Bitmap conv_bm = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ jsonObjectFromFeedNews.getJSONObject("utilisateur").getString("avatar"));
		avatarPost.setImageBitmap(conv_bm);
		title.setText(this.jsonObjectFromFeedNews.getString("titre"));
		heurPub.setText(this.jsonObjectFromFeedNews.getString("dateCreation"));
		contenu.setText(this.jsonObjectFromFeedNews.getString("contenu"));
		// ************************************************************

		 listItem = new ArrayList<HashMap<String, Object>>();

		// **************************************** Boucle sur la listeview

		for (int i = 0; i < this.comments.length(); i++) {

			JSONObject row = this.comments.getJSONObject(i);

			HashMap<String, Object> map = new HashMap<String, Object>();

			// ***************************************************************

			conv_bm = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ row.getJSONObject("user").getString("avatar"));

			// **************************************************************
			map.put("img", conv_bm);
			map.put("heurPub",MainViewModel.decodeString(row.getString("dateCreation")));
			map.put("contenu", MainViewModel.decodeString(row.getString("contenu")));
			listItem.add(map);

		}

		// *************************************Fin de la boucle
		 mSchedule = new SimpleAdapter(

		this.getBaseContext(), listItem, R.layout.activity_items__comments,
				new String[] { "img", "contenu", "heurPub", "nbcommentaires" },
				new int[] { R.id.avatarP, R.id.contenu, R.id.heurPubP, });

		mSchedule.setViewBinder(new MyViewBinder());
		listViewComment.setAdapter(mSchedule);

	}

	public void addComment(View v) throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException  {
	 
	 Comment.addComment(this.currentUser.getInt("id"), this.jsonObjectFromFeedNews.getInt("id"), this.commentText.getText().toString());
	 getComments();
	}

	public void pullToRefresh() throws InterruptedException, ExecutionException, JSONException
	{
		JSONArray getNewsForUpdate=new Repository().getNews(this.currentUser.getInt("id"));
		for (int i = 0; i < getNewsForUpdate.length(); i++) 
		{

			JSONObject row = getNewsForUpdate.getJSONObject(i);
			if(row.getInt("id")==this.jsonObjectFromFeedNews.getInt("id"))
			{
				this.jsonObjectFromFeedNews=new JSONObject();
				this.jsonObjectFromFeedNews=row;
				
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
