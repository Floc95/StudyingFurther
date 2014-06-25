package com.esgi.studyingfurther.bl;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.esgi.studyingfurther.dal.Repository;

public class Factory {

	private Repository repository;
   // private Context currentContext;
	  
	 public Factory()  {
			repository = new Repository();
		}
	 

	public JSONObject getUser(String user, String password)
			throws InterruptedException, ExecutionException, JSONException {
		return this.repository.getUser(user, password);
	}

	// Faire toutes les autres méthodes d'accès au repository pour les VMs
}
