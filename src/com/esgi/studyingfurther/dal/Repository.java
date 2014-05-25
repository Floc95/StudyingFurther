package com.esgi.studyingfurther.dal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.JetPlayer;
import android.util.Log;

import com.esgi.studyingfurther.bl.*;
import com.esgi.studyingfurther.vm.JSONParser;

public class Repository {
String UrlUser="http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";
	public Repository()
	{
	}
	
	public User getUser(int id)
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un id
		// Retourne Null si l'id n'existe pas
		return null;
	}
	
	public User getUser(String login, String password)
	{
		// TODO getUser
		// Chargement d'un utilisateur en fonction d'un login et d'un mo t de passe
		// Retourne Null s'il n'existe pas
		//Log.v("webservice", convertJeson(UrlUser));
		return null;
	}
	
	// D'autres méthodes utiles seront ajoutés
	// (obtention des posts, des commentaires, des utilisateurs, des groupes, etc.)
	
	public void convertJeson(String URL) throws JSONException
	{
		 // Creating new JSON Parser
	    JSONParser jParser = new JSONParser();
	    // Getting JSON from URL
	    JSONObject json = jParser.getJSONFromUrl(URL);
	
	      // Getting JSON Array
	      JSONArray  user = json.getJSONArray("nom");
	      JSONObject c = user.getJSONObject(0);
	      Log.v("nom",c.toString());
	   
	    	//return json.toString();
	 
		
	}
}
