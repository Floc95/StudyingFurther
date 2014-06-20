package com.esgi.studyingfurther.bl;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.content.Context;

import com.esgi.studyingfurther.dal.Repository;

public class Factory {

	private Repository repository;
	
	public Factory() throws InterruptedException, ExecutionException
	{
		repository = new Repository();
	}
	
	public User getUser(String user, String password) throws InterruptedException, ExecutionException, JSONException
	{
		return this.repository.getUser(user, password);
	}
	
	
	// Faire toutes les autres méthodes d'accès au repository pour les VMs
}
