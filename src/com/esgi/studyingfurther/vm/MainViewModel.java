package com.esgi.studyingfurther.vm;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;


import com.esgi.studyingfurther.R;
import com.esgi.studyingfurther.bl.Factory;


public class MainViewModel {

	private Factory factory;
	private JSONObject currentUser;
	
  
	public MainViewModel(Factory factory) {
		this.factory = factory;
		
	}
	

	
	public static void changeActionBarWithValueOfCurrentUser(Context c,ActionBar actionBar,JSONObject currentUser) throws InterruptedException, ExecutionException, JSONException
	{
		actionBar.setTitle(" "+currentUser.getString("prenom"));
		Bitmap avatar =new DownloadImageTask().execute(ManagerURL.urlGetAvatar+currentUser.getString("avatar")).get();// MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+currentUser.getString("avatar"));
	    Drawable btmpDrawable=new BitmapDrawable(c.getResources(), avatar);
		actionBar.setIcon(btmpDrawable);
	//	actionBar.setBackgroundDrawable(c.getResources().getDrawable(R.drawable.bg));
		
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

	public static boolean isNetworkAvailable(Context myContext) {
	    ConnectivityManager connectivityManager  = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	public static String decodeString(String myString)
			throws UnsupportedEncodingException {
		String tempString = new String(myString.getBytes("ISO-8859-1"), "UTF-8");
		return Html.fromHtml(tempString).toString();

	}
	public static void alertNetwork(Context c)
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(c);
		adb.setTitle("No NetWork");
		adb.setMessage("Your mobile is not connected,Please cheeck your wifi/cellulaire network.");
		adb.setPositiveButton("Ok", null);
		adb.show();
		
	}

	public static Bitmap getRoundedCornerImage(String url)
			throws InterruptedException, ExecutionException {

		Bitmap avatar = new DownloadImageTask().execute(url).get();
		Bitmap resized = Bitmap.createScaledBitmap(avatar, 100, 100, true);
		Bitmap output = Bitmap.createBitmap(resized.getWidth(),
				resized.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, resized.getWidth(),resized.getHeight());
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

	
}
