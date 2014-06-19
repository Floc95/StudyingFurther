package com.esgi.studyingfurther.vm;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.text.Html;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.User;

public class MainViewModel {

	private Factory factory;
	private User currentUser;
	private FeedManager feedManager;

	public MainViewModel(Factory factory) {
		this.factory = factory;
		this.feedManager = new FeedManager(factory);
	}

	public boolean authenticate(String login, String password)
			throws InterruptedException, ExecutionException, JSONException {
		User user = this.factory.getUser(login, password);
		// Log.i("User.id",user.getId()+"");
		if (user != null) {
			this.currentUser = user;
		}
		return user != null ? true : false;
	}

	// Faire toutes les autres méthodes utiles à l'UI : getPosts, comments
	// etc...
	public int getCurrentUserId() {
		return this.currentUser.getId();
	}

	public String decodeString(String myString) throws UnsupportedEncodingException {
		String tempString= new String(myString.getBytes("ISO-8859-1"), "UTF-8");
		return Html.fromHtml(tempString).toString();

	}

}
