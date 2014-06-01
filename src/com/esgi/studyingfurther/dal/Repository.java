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
   private String UrlUser = "http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";
	private HashMap<String, String> users;
	//JSONObject JsonObjectAuthentification =null;
    
    public Repository(Context thiscontext) throws InterruptedException, ExecutionException
	{
    	users=new JSONParser(thiscontext,this.UrlUser).execute().get();
	}
	
	public User getUser(int id)
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un id
		// Retourne Null si l'id n'existe pas

		for(int i=1;i<=this.users.size();i++)
		{
		
			if(Integer.parseInt(this.users.get("id"))==id)
			{
				return new User(id, this.users.get("nom"), this.users.get("prenom"), this.users.get("login"), this.users.get("password"), this.users.get("avatar"),Integer.parseInt( this.users.get("statut")));
				
			}
		}
		return null;
	}
	
	public User getUser(String login, String password)
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un login et d'un mo t de passe
		// Retourne Null s'il n'existe pas
	
			for(String key : users.keySet())
			{
				if(key.equals("id"))
				{
				
				if(this.users.get("login").equals(login) && this.users.get("password").equals(MD5(password)) )
				{
			   // Log.i("login_",key);
				 return new User(Integer.parseInt(this.users.get("id")), this.users.get("nom"), this.users.get("prenom"), this.users.get("login"), this.users.get("password"), this.users.get("avatar"),Integer.parseInt( this.users.get("statut")));
				
				}
				}
			}
		
		
		return null;
	
	}
	
	// D'autres méthodes utiles seront ajoutés
	// (obtention des posts, des commentaires, des utilisateurs, des groupes, etc.)
	private String MD5(String md5) {
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
