package com.esgi.studyingfurther.vm;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	protected Bitmap doInBackground(String... urls) {
		// get url of image
		String urldisplay = urls[0];
		// create Bitmap variable
		Bitmap mIcon11 = null;
		try {
			// create instance of InputStream and pass URL
			InputStream in = new java.net.URL(urldisplay).openStream();
			// decode stream and initialize bitmap
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			// show error log
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		// return bitmap
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {

	}

}
