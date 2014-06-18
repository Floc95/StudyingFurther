package com.esgi.studyingfurther.vm;



import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap;



import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.User;

public class MainViewModel {

	private Factory factory;
	private User currentUser;
	private FeedManager feedManager;
	
	public MainViewModel(Factory factory)
	{
		this.factory = factory;
		this.feedManager = new FeedManager(factory);
	}
	
	public boolean authenticate(String login, String password) throws InterruptedException, ExecutionException, JSONException
	{
		User user = this.factory.getUser(login, password);
	//	Log.i("User.id",user.getId()+"");
		if (user != null)
		{
			this.currentUser = user;
		}
		return user != null ? true : false;
	}
	
	// Faire toutes les autres méthodes utiles à l'UI : getPosts, comments etc...
	public int getCurrentUserId()
	{
		return this.currentUser.getId();
	}
	
	
	public Bitmap LoadImageFromWebOperations(String url){
        try{
          String encodedurl = url.replace(" ", "%20");
          InputStream is = (InputStream) new URL(encodedurl).getContent();
          Bitmap d = BitmapFactory.decodeStream(is);
          return d;
        }catch (Exception e) {
         e.printStackTrace();
         return null;
        }
      }
	public  Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
	    Bitmap result = null;
	    try {
	        result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(result);

	        int color = 0xff424242;
	        Paint paint = new Paint();
	        Rect rect = new Rect(0, 0, 200, 200);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawCircle(50, 50, 50, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	    } catch (NullPointerException e) {
	    } catch (OutOfMemoryError o) {
	    }
	    return result;
	}
}
