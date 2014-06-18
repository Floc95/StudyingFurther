package com.esgi.studyingfurther.vm;



import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;




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

}
