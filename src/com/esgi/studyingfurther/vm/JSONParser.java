package com.esgi.studyingfurther.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.esgi.studyingfurther.MainActivity;
import com.google.gson.JsonObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


	public class JSONParser extends AsyncTask<String, String, HashMap<String, String>> {
		
		
		private ProgressDialog pDialog;
		private Context thiscontext;
		private String url;
		static InputStream is = null;
		static JSONObject jObj = null;
		static String json = "";
		JSONObject jsonobjets=null;
		private HashMap<String, String> MapperObjets=null;
		
		
		public JSONParser(Context c,String url)
		{
			
			this.MapperObjets=new HashMap<String, String>();
			this.thiscontext=c;
			this.url=url;
			
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		//	Toast.makeText(this.thiscontext,"Connecting....",Toast.LENGTH_SHORT).show();
			pDialog = new ProgressDialog(this.thiscontext);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected HashMap<String, String> doInBackground(String... args) {
			//JSONParser jParser = new JSONParser();
			// Getting JSON from URL
			
			 jsonobjets = getJSONFromUrl(this.url);
			
		       	this.MapperObjets.clear();
				for(Iterator<String> iter = jsonobjets.keys();iter.hasNext();) 
				{
				    String key = iter.next();
				    try {
						Object value = this.jsonobjets.get(key);
						this.MapperObjets.put(key,value.toString());
						//Log.i(key,value.toString());
						//i++;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			 return this.MapperObjets;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> Mapper) {
		
			pDialog.dismiss();
		    Mapper= this.MapperObjets;
		   
			
		}
		
		
		  private JSONObject getJSONFromUrl(String url) {

			    // Making HTTP request
			    try {
			      // defaultHttpClient
			      DefaultHttpClient httpClient = new DefaultHttpClient();
			      HttpPost httpPost = new HttpPost(url);
			      HttpResponse httpResponse = httpClient.execute(httpPost);
			      HttpEntity httpEntity = httpResponse.getEntity();
			      is = httpEntity.getContent();
			    } catch (UnsupportedEncodingException e) {
			      e.printStackTrace();
			    } catch (ClientProtocolException e) {
			      e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
			    try {
			      BufferedReader reader = new BufferedReader(new InputStreamReader(
			          is, "iso-8859-1"), 8);
			      StringBuilder sb = new StringBuilder();
			      String line = null;
			      while ((line = reader.readLine()) != null) {
			        sb.append(line + "n");
			      }
			      is.close();
			      json = sb.toString();
			    } catch (Exception e) {
			      Log.e("Buffer Error", "Error converting result " + e.toString());
			    }
			    // try parse the string to a JSON object
			    try {
			      jObj = new JSONObject(json);
			    } catch (JSONException e) {
			      Log.e("JSON Parser", "Error parsing data " + e.toString());
			    }
			    
			    // return JSON String
			    return jObj;
			  }
			  
	}

