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
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;
import com.esgi.studyingfurther.vm.MyViewBinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Comments extends Activity {

	//private ListView listViewComment = null;
	private LinearLayout linearLayoutComments = null;
	private ImageView avatarPost = null;
	private TextView title = null;
	private TextView heurPub = null;
	private TextView contenu = null;
	private EditText commentText = null;
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

		//listViewComment = (ListView) findViewById(R.id.listviewComments);
		linearLayoutComments = (LinearLayout) findViewById(R.id.linearLayourComments);
		avatarPost = (ImageView) findViewById(R.id.avatarP);
		title = (TextView) findViewById(R.id.titleP);
		heurPub = (TextView) findViewById(R.id.heurPubP);
		contenu = (TextView) findViewById(R.id.contenu);
		commentText = (EditText) findViewById(R.id.commentText);
	
		try {
			Manager = new MainViewModel(new Factory());
			if(MainViewModel.isNetworkAvailable(this))
			{
				this.jsonObjectFromFeedNews = new JSONObject(getIntent().getExtras().getString("comments"));
				this.currentUser = new JSONObject(getIntent().getExtras().getString("currentUser"));
				MainViewModel.changeActionBarWithValueOfCurrentUser(this, this.getActionBar(), this.currentUser);
				getComments();
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
          
		
		pullToRefresh();
		this.comments = jsonObjectFromFeedNews.getJSONArray("commentaires");

		// ************ get Comment, avatar,date,tite and avatar of user

		Bitmap conv_bm = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ jsonObjectFromFeedNews.getJSONObject("utilisateur").getString("avatar"));
		avatarPost.setImageBitmap(conv_bm);
		String userName = jsonObjectFromFeedNews.getJSONObject("utilisateur").getString("prenom") + " " + jsonObjectFromFeedNews.getJSONObject("utilisateur").getString("nom");
		title.setText(userName);
		heurPub.setText(this.jsonObjectFromFeedNews.getString("dateCreation"));
		contenu.setText(MainViewModel.decodeString(this.jsonObjectFromFeedNews.getString("contenu")));
		// ************************************************************

		 listItem = new ArrayList<HashMap<String, Object>>();

   
		for (int i = 0; i < this.comments.length(); i++) {

			JSONObject row = this.comments.getJSONObject(i);
			
			LinearLayout commentLayout = new LinearLayout(this);
			commentLayout.setOrientation(LinearLayout.VERTICAL);
			commentLayout.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.black_line));
			
			// Header
			LinearLayout headerLayout = new LinearLayout(this);
			headerLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			// Avatar
			ImageView avatar = new ImageView(this);
			conv_bm = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ row.getJSONObject("user").getString("avatar"));
			avatar.setImageBitmap(conv_bm);
			LinearLayout.LayoutParams lpAvatar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			//lpAvatar.weight = 1.0f;
			lpAvatar.setMargins(0, 0, 6, 0);
			lpAvatar.gravity = Gravity.LEFT;
			headerLayout.addView(avatar, lpAvatar);
			
			// Nom + Date
			
			LinearLayout stackedText = new LinearLayout(this);
			stackedText.setOrientation(LinearLayout.VERTICAL);
			
			TextView tvNom = new TextView(this);
			tvNom.setTextSize(16);
			tvNom.setTypeface(Typeface.DEFAULT_BOLD);
			String nom = row.getJSONObject("user").getString("prenom") + " " + row.getJSONObject("user").getString("nom");
			tvNom.setText(nom);
			LinearLayout.LayoutParams lpTvNom = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpTvNom.weight = 1.0f;
			stackedText.addView(tvNom, lpTvNom);
			
			TextView tvDate = new TextView(this);
			tvDate.setTextSize(12);
			tvDate.setText(MainViewModel.decodeString(row.getString("dateCreation")));
			LinearLayout.LayoutParams lpTvDate = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpTvDate.weight = 1.0f;
			stackedText.addView(tvDate, lpTvDate);
			
			LinearLayout.LayoutParams lpStacked = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpStacked.weight = 1.0f;
			lpStacked.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			headerLayout.addView(stackedText, lpStacked);
			
			LinearLayout.LayoutParams lpHeader = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpHeader.weight = 1.0f;
			lpHeader.setMargins(6, 6, 6, 6);
			commentLayout.addView(headerLayout, lpHeader);
			
			// Contenu
			
			TextView tvComment = new TextView(this);
			tvComment.setTextSize(16);
			tvComment.setText(MainViewModel.decodeString(row.getString("contenu")));
			LinearLayout.LayoutParams lpTvComment = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpTvComment.weight = 1.0f;
			lpTvComment.setMargins(6, 6, 6, 6);
			commentLayout.addView(tvComment, lpTvComment);
			
			ToggleButton plusOneButton = new ToggleButton(this);
			plusOneButton.setText("+1");
			LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lpBtn.weight = 1.0f;
			commentLayout.addView(plusOneButton, lpBtn);
			
			// Ajout au layout final
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.weight = 1.0f;
			linearLayoutComments.addView(commentLayout, lp);

		}
	}

	public void modificationComments(View v)
	{
		
	}
	public void addComment(View v) throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException  {
	 
	if( Comment.addComment(this.currentUser.getInt("id"), this.jsonObjectFromFeedNews.getInt("id"), this.commentText.getText().toString()).equals("86"))
	{
		
		if(MainViewModel.isNetworkAvailable(this))
		{
		Toast.makeText(getBaseContext(),"Comment is added", Toast.LENGTH_LONG).show();
		 getComments();
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
