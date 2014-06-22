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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.esgi.studyingfurther.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class JSONParser extends AsyncTask<String, String, JSONArray> {

	private String url;
	static InputStream is = null;
	static JSONArray jArray = null;
	static String json = "";
	JSONObject jsonobjet = null;

	public JSONParser(String url) {

		this.url = url;
		jArray = new JSONArray();

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Toast.makeText(this.thiscontext,"Connecting....",Toast.LENGTH_SHORT).show();

	}

	@Override
	protected JSONArray doInBackground(String... args) {
		// JSONParser jParser = new JSONParser();
		// Getting JSON from URL
		Log.v("NEWS", this.url);
		return getJSONFromUrl(this.url);

	}

	@Override
	protected void onPostExecute(JSONArray jsArray) {

		Log.v("NEWS:", jsArray.toString());

	}

	private JSONArray getJSONFromUrl(String url) {

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
			// Log.e("json",json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// try parse the string to a JSON object
		try {
			Object json_ = new JSONTokener(json).nextValue();

			if (json_ instanceof JSONObject) {

				jArray = new JSONArray()
						.put(0, new JSONObject(json.toString()));
				return jArray;
			}

			jArray = new JSONArray(json);

		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jArray;
	}

}
