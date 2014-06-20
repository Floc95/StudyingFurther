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

public class Repository {
  //  String UrlUser="http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";
   private String UrlAPIIdentification = "http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264";
   private String UrlAPINews="http://www.your-groups.com/API/GetNewsFeed?key=7e2a3a18cd00ca322f60c28393c43264";
	private JSONArray user;
	private JSONArray News;
	//JSONObject JsonObjectAuthentification =null;
    
	public User getUser(int id) throws NumberFormatException, JSONException
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un id
		// Retourne Null si l'id n'existe pas

		/*	if(Integer.parseInt(user.getString("id"))==id)
			{
				return new User(id, user.getString("nom"), user.getString("prenom"), user.getString("login"), user.getString("password"),user.getString("avatar"),Integer.parseInt( user.getString("statut")));
				
			}*/
		
		return null;
	}
	
	public User getUser(String login, String password) throws InterruptedException, ExecutionException, JSONException
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un login et d'un mo t de passe
		// Retourne Null s'il n'existe pas
		     String UrlUser=this.UrlAPIIdentification+"&username="+login+"&password="+md5(password)+"";
		 	Log.v("NEWS","getuser");
    	    user= new JSONParser(UrlUser).execute().get();
    	    if(user!=null)
    	    {
				if(user.getJSONObject(0).getString("login").equals(login) && user.getJSONObject(0).getString("password").equals(md5(password)) )
				{
			
				 return new User(Integer.parseInt(user.getJSONObject(0).getString("id")),user.getJSONObject(0).getString("nom"), user.getJSONObject(0).getString("prenom"), user.getJSONObject(0).getString("login"), user.getJSONObject(0).getString("password"), user.getJSONObject(0).getString("avatar"),Integer.parseInt(user.getJSONObject(0).getString("statut")));
				
				}
				
    	    }
		
		return null;
	
	}
	
	// D'autres méthodes utiles seront ajoutés
	// (obtention des posts, des commentaires, des utilisateurs, des groupes, etc.)
	
	public JSONArray getNews(int idUser) throws InterruptedException, ExecutionException, JSONException
	{
	
			String UrlNews=this.UrlAPINews+"&userId="+idUser;
		    this.News=	new JSONParser( UrlNews).execute().get();
		    
		    return this.News;
			
	}
	
	private String md5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
}
