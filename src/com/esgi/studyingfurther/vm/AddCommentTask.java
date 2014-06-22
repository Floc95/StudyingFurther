package com.esgi.studyingfurther.vm;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class AddCommentTask extends AsyncTask<String, Void, String> {

	protected String doInBackground(String... urls) {

		// get url of of request comment
		String urldisplay = urls[0];
		// String decodeMyUrl= Uri.encode(urldisplay,"UTF-8");
		Log.v("url", urldisplay);
		// create instance of InputStream and pass URL
		try {
			InputStream in = new URL(urldisplay).openStream();

			return String.valueOf(in.read());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "error";
	}

	protected void onPostExecute(String result) {

	}
}
