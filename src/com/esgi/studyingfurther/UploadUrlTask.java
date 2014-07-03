package com.esgi.studyingfurther;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UploadUrlTask extends AsyncTask<String, Void, String> {

	Context context;
	String TAG ="UploadUrlTask";
	public UploadUrlTask(Context c){
		context = c;
	}
	
	@Override
	protected String doInBackground(String... params) {
		String result = null;
		String UrlGenerated = "";
		if (params != null && params.length > 0) {
			if(params[0] == "addClasse")
			{
				UrlGenerated = UrlForAddClasse(params[1], params[2], params[3]);
			}
			else if (params[0] == "addUser") 
			{
				UrlGenerated = UrlForAddUser(params[1], params[2], params[3], params[4]);
			}
			else if (params[0] == "removeUser") 
			{
				UrlGenerated = UrlForRemoveUser(params[1], params[2], params[3], params[4]);
			}
			else if (params[0] == "addPost") {
				UrlGenerated = UrlForAddPost(params[1], params[2], params[3], params[4]);
				
			}
			else if (params[0] == "addPostWithPhoto") {
				UrlGenerated = UrlForAddPostWithPhoto(params[1], params[2], params[3], params[4], params[5]);
				
			}
				
			
				try {
					Log.v(TAG,UrlGenerated);
					result = uploadUrl(UrlGenerated);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		return result;
	}
	/**
	 * generate the Url for remove a user
	 * @param myUrl
	 * @param userId
	 * @param groupId
	 * @param userToRemoveId
	 * @return
	 */
	private String UrlForRemoveUser(String myUrl, String userId, String groupId, String userToRemoveId) {
		return myUrl+"&userId="+userId+"&groupId="+groupId+"&userToRemoveId="+userToRemoveId;
	}

	/**
	 * generate the Url for add a post
	 * @param myUrl
	 * @param userId
	 * @param groupId
	 * @param userToRemoveId
	 * @return
	 */
	private String UrlForAddPost(String myUrl, String userId, String groupId, String content) {
		return myUrl+"&userId="+userId+"&groupId="+groupId+"&content="+content;
	}
	
	/**
	 * generate the Url for add a post
	 * @param myUrl
	 * @param userId
	 * @param groupId
	 * @param userToRemoveId
	 * @return
	 */
	private String UrlForAddPostWithPhoto(String myUrl, String userId, String groupId, String content, String imgUrl) {
		return myUrl+"&userId="+userId+"&groupId="+groupId+"&content="+content+"&documentUrl="+imgUrl;
	}
	/**
	 * generate the Url for add a user
	 * @param myUrl
	 * @param userId
	 * @param groupId
	 * @param usertoaddid
	 * @return
	 */
	private String UrlForAddUser(String myUrl, String userId, String groupId, String usertoaddid) {
		Log.v("adduser", myUrl+"&userId="+userId+"&groupId="+groupId+"&userToAddId="+usertoaddid);
		return myUrl+"&userId="+userId+"&groupId="+groupId+"&userToAddId="+usertoaddid;
	}
	
	/**
	 * generate the Url for add a class
	 * @param myUrl
	 * @param userId
	 * @param groupName
	 * @return
	 */
	private String UrlForAddClasse(String myUrl, String userId, String groupName)
	{
		groupName = groupName.replace(" ", "%20");
		return myUrl+"&userId="+userId+"&groupName="+groupName;
	}

	/**
	 * function to make the connection to the web service and upload
	 * @param myUrl
	 * @return
	 * @throws IOException
	 */
	private String uploadUrl(String myUrl) throws IOException {
		String contentAsString = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		// Only display the first 1000 characters of the retrieved web page content.
		int len = 1000;

		try {
			URL url = new URL(myUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			is = conn.getInputStream();

			// Convert the InputStream into a String
			contentAsString = readIt(is, len);
		}
		finally {
			// Makes sure that the InputStream is closed after the app is finished using it.
			if (is != null)
				is.close();
			if (conn != null)
				conn.disconnect();
		}

		return contentAsString;
	}

	/**
	 * function to get the information of a type of InputStream and return a String
	 * @param stream
	 * @param len
	 * @return String
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		reader.close();
		return new String(buffer);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result.substring(0, 5).equals("Valid")) {
			new AlertDialog.Builder(this.context)
			.setTitle("Valid")
			.setMessage("Operation Success")
			.setPositiveButton("OK", null).show();
			
		}else if (result.substring(0, 5).equals("Error")) {
			new AlertDialog.Builder(this.context)
			.setTitle("Error")
			.setMessage("Operation Fail")
			.setPositiveButton("OK", null).show();
		}
	}
}

