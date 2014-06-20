package com.esgi.studyingfurther.vm;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.text.Html;
import android.util.Log;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.User;

public class MainViewModel {

	private Factory factory;
	private JSONObject currentUser;
	private FeedManager feedManager;

	public MainViewModel(Factory factory) {
		this.factory = factory;
		this.feedManager = new FeedManager(factory);
	}

	public boolean authenticate(String login, String password) throws InterruptedException, ExecutionException, JSONException {
		
		 this.currentUser = this.factory.getUser(login, password);
		return this.currentUser != null ? true : false;
	}

	// Faire toutes les autres méthodes utiles à l'UI : getPosts, comments
	// etc...
	public JSONObject getCurrentUser() {
		return this.currentUser;
	}


	public static String decodeString(String myString) throws UnsupportedEncodingException {
		String tempString= new String(myString.getBytes("ISO-8859-1"), "UTF-8");
		return Html.fromHtml(tempString).toString();

	}
	
public static Bitmap getRoundedCornerImage(String url) throws InterruptedException, ExecutionException {
	     
	     Bitmap avatar = new DownloadImageTask().execute(url).get();
	     Bitmap resized = Bitmap.createScaledBitmap(avatar, 100, 100, true);
		Bitmap output = Bitmap.createBitmap(resized.getWidth(),resized.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, resized.getWidth(), resized.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 100;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(resized, rect, rect, paint);

		return output;

		}

public static void  executeRequest(String requestComment)
{
	String url = String.format(requestComment);
	 
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet request = new HttpGet(url);
	 // Execute the request
	 try {
	   
	     HttpResponse response = httpclient.execute(request);
	    
	     Log.v("test", response.getStatusLine().toString());
	      
	    
	 } catch (Exception e)
	 {
	     Log.v("test","Exception: " + e.getMessage());
	 }
}

}
