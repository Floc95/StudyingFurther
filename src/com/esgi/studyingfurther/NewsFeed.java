package com.esgi.studyingfurther;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.Post;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.CustomAdapter;
import com.esgi.studyingfurther.vm.MainViewModel;


public class NewsFeed extends Activity {

	private ListView maListViewPerso;
	private JSONObject currentUser;
	MainViewModel Manager = null;
	JSONArray news;
	JSONArray groups;
	ArrayList<HashMap<String, Object>> listGroup;
	int userId = 0;
	String userIdString = "";
	String[] groupStringList;
	
	String imgId = "";
	int realPosition = 0;
	//ArrayList<JSONObject> comments;

	
	private TextView postText = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		
		postText = (TextView) findViewById(R.id.postText);
		
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);
		Manager = new MainViewModel(new Factory());
		
				
		if (MainViewModel.isNetworkAvailable(this)) {

			try {
				this.currentUser = new JSONObject(getIntent().getExtras().getString("currentUser"));
				userId = this.currentUser.getInt("id");
				Log.v("userid", userId+"");
				getGroups();

				// Put the picture of the current user and our username on the
				// header of activity

				MainViewModel.changeActionBarWithValueOfCurrentUser(this,
						this.getActionBar(), this.currentUser);

				// Call a function getNews for fix all post of a current user
				this.news = new Repository().getNews(this.currentUser.getInt("id"));
				// Persistance for deconnect mode
				android.content.SharedPreferences prefs = getSharedPreferences("news", 0);
				android.content.SharedPreferences.Editor editor = prefs.edit();
				editor.putString("news",this.news.toString());
				editor.commit();
				//
				CustomAdapter adapter = new CustomAdapter(this,new Post().getPosts(this.news,this.currentUser.getInt("statut")));

				maListViewPerso.setAdapter(adapter);

			} catch (JSONException e) {

			} catch (InterruptedException e) {

			} catch (ExecutionException e) {

			} catch (UnsupportedEncodingException e) {

			}

		} else {
			
			android.content.SharedPreferences prefs = getSharedPreferences("news", 0);
		    String news = prefs.getString("news","");
		    write(news);
		    MainViewModel.alertNetwork(this);
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
	        	
	        	Intent intent_1 = new Intent(this, Parameter.class);
	        	
			try {
				intent_1.putExtra("userId",this.currentUser.getInt("id"));
				intent_1.putExtra("status", (this.currentUser.getInt("statut") != 0));
				Log.v("NesFeed statut ", ""+(this.currentUser.getInt("statut") != 0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				startActivity(intent_1);
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
		JSONObject currentPost = NewsFeed.this.news.getJSONObject(maListViewPerso.getPositionForView(v));

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

	public void write(String value) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Log");
		adb.setMessage(value);
		adb.setPositiveButton("Ok", null);
		adb.show();
	}
	
	public void getGroups() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		
		userIdString = "" + userId;
		groups = new Repository().getGroup(userId);
		Log.v("Groups length", "test");
		Log.v("Groups length", groups.length()+"");
		listGroup = null;
		listGroup = new ArrayList<HashMap<String, Object>>();
		listGroup.clear();
		for (int i = 0; i < groups.length(); i++) {
			JSONObject obj = groups.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("idGroupe", Manager.decodeString(obj.getString("idGroupe")));
			map.put("libelle", Manager.decodeString(obj.getString("libelle")));
			listGroup.add(map);
			
		}
		groupStringList = new String[listGroup.size()];
		for (int i = 0; i < listGroup.size(); i++) {
			String tmp = listGroup.get(i).get("libelle").toString() ;
			groupStringList[i] = tmp;
		}
	}		
	
	//take a photo now
	private void getPicFromCapture() {
		  try {
			   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			   intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			   startActivityForResult(intent, 1);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  
	}
	//Select photo from album
	private void getPicFromContent()
	{
		  try {
			   Intent intent = new Intent();
			   intent.setType("image/*");
			   intent.setAction(Intent.ACTION_GET_CONTENT);
			   startActivityForResult(intent, 2);
		  } 
		  	catch (ActivityNotFoundException e) 
		  	{
		  		e.printStackTrace();
		  	}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Uri mImageCaptureUri;
		if (resultCode != RESULT_OK) 
		{
			return;
		}
		if (data != null) 
		{
			mImageCaptureUri = data.getData();
			if (mImageCaptureUri != null) 
			{
				Bitmap image;
				try 
				{
					image = MediaStore.Images.Media.getBitmap(
					getContentResolver(), mImageCaptureUri);
					if (image != null) 
					{
						ImgurUploadTask im = new ImgurUploadTask(mImageCaptureUri,NewsFeed.this) {};
						im.execute();
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			} 
			else 
			{
				Bundle extras = data.getExtras();
				if (extras != null) 
				{
					Bitmap image = extras.getParcelable("data");
					if (image != null) 
					{
						mImageCaptureUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), image, null,null));
						ImgurUploadTask im = new ImgurUploadTask(mImageCaptureUri,NewsFeed.this) {};
						im.execute();

					}
				}
			}
		}
	}

	public void addPost(View v) throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException  
	{
		new AlertDialog.Builder(NewsFeed.this)
		.setTitle("Choose the group")
		.setItems(groupStringList, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				realPosition = which;
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						NewsFeed.this).setTitle("Send with a photo, Click No to send a text directly");
					    String[] arrayOfString = new String[2];
					    arrayOfString[0] = "Take it now";
					    arrayOfString[1] = "Choose it from album";
					    localBuilder.setItems(arrayOfString,new DialogInterface.OnClickListener()
					    {
					         public void onClick(DialogInterface 
					        		 paramDialogInterface, int paramInt) 
					         {
						          switch (paramInt) 
						          {
							          default:
							          case 0:
							        	  getPicFromCapture();
							        	  break;
							          case 1:
							        	  getPicFromContent();
							        	  break;
						          }
					         }
					       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								try {
									if (Post.addPost(currentUser.getInt("id"), listGroup.get(which).get("idGroupe").toString(), postText.getText().toString()).equals("86")) 
									{
										if(MainViewModel.isNetworkAvailable(NewsFeed.this))
										{
											Toast.makeText(getBaseContext(),"Post is added", Toast.LENGTH_LONG).show();
											CustomAdapter adapter = new CustomAdapter(NewsFeed.this,new Post().getPosts(NewsFeed.this.news,NewsFeed.this.currentUser.getInt("statut")));

											maListViewPerso.setAdapter(adapter);
										
										//getComments();
										}else
										{
											MainViewModel.alertNetwork(NewsFeed.this);
										}
										 postText.setText("");
									}
									else {
										Toast.makeText(getBaseContext(),"Error", Toast.LENGTH_LONG).show();
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).show();
		 
	}
		});
	}
	
	public abstract class ImgurUploadTask extends AsyncTask<Void, Void, String> {

		private final String TAG = ImgurUploadTask.class.getSimpleName();

		private static final String UPLOAD_URL = "https://api.imgur.com/3/image";

	    private Activity mActivity;
		private Uri mImageUri;  // local Uri to upload

		public ImgurUploadTask(Uri imageUri, Activity activity) {
			this.mImageUri = imageUri;
	        this.mActivity = activity;
		}

		@Override
		protected String doInBackground(Void... params) {
			InputStream imageIn;
			try {
				imageIn = mActivity.getContentResolver().openInputStream(mImageUri);
			} catch (FileNotFoundException e) {
				Log.e(TAG, "could not open InputStream", e);
				return null;
			}

	        HttpURLConnection conn = null;
	        InputStream responseIn = null;

			try {
	            conn = (HttpURLConnection) new URL(UPLOAD_URL).openConnection();
	            conn.setDoOutput(true);

	            conn.setRequestProperty("Authorization", "Client-ID " + "9562d350395406b");

	            OutputStream out = conn.getOutputStream();
	            copy(imageIn, out);
	            out.flush();
	            out.close();

	            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                responseIn = conn.getInputStream();
	                return onInput(responseIn);
	            }
	            else {
	                Log.i(TAG, "responseCode=" + conn.getResponseCode());
	                responseIn = conn.getErrorStream();
	                StringBuilder sb = new StringBuilder();
	                Scanner scanner = new Scanner(responseIn);
	                while (scanner.hasNext()) {
	                    sb.append(scanner.next());
	                }
	                Log.i(TAG, "error response: " + sb.toString());
	                return null;
	            }
			} catch (Exception ex) {
				Log.e(TAG, "Error during POST", ex);
				return null;
			} finally {
	            try {
	                responseIn.close();
	            } catch (Exception ignore) {}
	            try {
	                conn.disconnect();
	            } catch (Exception ignore) {}
				try {
					imageIn.close();
				} catch (Exception ignore) {}
			}
		}

	    private int copy(InputStream input, OutputStream output) throws IOException {
	        byte[] buffer = new byte[8192];
	        int count = 0;
	        int n = 0;
	        while (-1 != (n = input.read(buffer))) {
	            output.write(buffer, 0, n);
	            count += n;
	        }
	        return count;
	    }

		protected String onInput(InputStream in) throws Exception {
	        StringBuilder sb = new StringBuilder();
	        Scanner scanner = new Scanner(in);
	        while (scanner.hasNext()) {
	            sb.append(scanner.next());
	        }

	        JSONObject root = new JSONObject(sb.toString());
	        String id = root.getJSONObject("data").getString("id");
	        String deletehash = root.getJSONObject("data").getString("deletehash");

			Log.i(TAG, "new imgur url: http://imgur.com/" + id + " (delete hash: " + deletehash + ")");
			imgId = id;
			return id;
		}
		
		@Override
		protected void onPostExecute(String result) {
			new AlertDialog.Builder(NewsFeed.this)
			.setTitle("Success")
			.setMessage("Image attached")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						if (Post.addPostWithPhoto(currentUser.getInt("id"), listGroup.get(realPosition).get("idGroupe").toString(), postText.getText().toString(),imgId).equals("86")) 
						{
							if(MainViewModel.isNetworkAvailable(NewsFeed.this))
							{
							Toast.makeText(getBaseContext(),"Post is added", Toast.LENGTH_LONG).show();
							//getComments();
							}else
							{
								MainViewModel.alertNetwork(NewsFeed.this);
							}
							 postText.setText("");
						}
						else {
							Toast.makeText(getBaseContext(),"Error", Toast.LENGTH_LONG).show();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			})
			.show();
			
			
			try {
				CustomAdapter adapter = new CustomAdapter(NewsFeed.this,new Post().getPosts(NewsFeed.this.news,NewsFeed.this.currentUser.getInt("statut")));
				maListViewPerso.setAdapter(adapter);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			super.onPostExecute(result);
		}

	}
}
