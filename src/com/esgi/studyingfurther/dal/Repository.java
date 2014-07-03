package com.esgi.studyingfurther.dal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.JetPlayer;
import android.util.Log;

import com.esgi.studyingfurther.bl.*;
//import com.esgi.studyingfurther.vm.JSONParser;
import com.esgi.studyingfurther.vm.JSONParser;
import com.esgi.studyingfurther.vm.ManagerURL;

public class Repository {
	
	private JSONArray user;
	private JSONArray News;
	
		private JSONArray Groups;
		private JSONArray UsersOfGroup;
		private JSONArray AllUsers;
	// JSONObject JsonObjectAuthentification =null;
   

	public User getUser(int id) throws NumberFormatException, JSONException {

		return null;
	}

	public JSONObject getUser(String login, String password)
			throws InterruptedException, ExecutionException, JSONException {
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un login et d'un mo t de
		// passe
		// Retourne Null s'il n'existe pas
		String UrlUser = ManagerURL.urlAuthentificate + "&username=" + login+ "&password=" + md5(password) + "";
		try
		{
			user = new JSONParser(UrlUser).execute().get();
		}
		catch (Exception ex)
		{
			return null;
		}
	
		if (user != null) 
		{
			return this.user.getJSONObject(0);
		}

		return null;

	}

	// D'autres méthodes utiles seront ajoutés
	// (obtention des posts, des commentaires, des utilisateurs, des groupes,
	// etc.)

	public JSONArray getNews(int idUser) throws InterruptedException,
			ExecutionException, JSONException {

		String UrlNews = ManagerURL.urlGetNews + "&userId=" + idUser;
		this.News = new JSONParser(UrlNews).execute().get();

		return this.News;

	}

	public static String md5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * Get the groups with the user id
	 * @param idUser
	 * @return an array of group
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONArray getGroup(int idUser) throws InterruptedException, ExecutionException
	{
		String UrlGroup = ManagerURL.UrlAPIGroup+"&userId="+idUser;
		this.Groups = new JSONParser(UrlGroup).execute().get();
		return this.Groups;
	}
	/**
	 * Get the users of the group
	 * @param idGroup
	 * @return JSONArray
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONArray getGroupUsers(int idGroup) throws InterruptedException, ExecutionException
	{
		String UrlUserOfGroup = ManagerURL.urlAPIUsersOfGroup+"&groupId="+idGroup;
		this.UsersOfGroup = new JSONParser(UrlUserOfGroup).execute().get();
		return this.UsersOfGroup;
	}

	/**
	 * Get all the users
	 * @return JSONArray
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONArray getAllUssers() throws InterruptedException, ExecutionException
	{
		AllUsers = new JSONParser(ManagerURL.urlAPIAllUsers).execute().get();
		return AllUsers;
	}
}
