package com.esgi.studyingfurther.bl;

import java.util.concurrent.ExecutionException;

import android.content.Context;

import com.esgi.studyingfurther.dal.Repository;

public class Factory {

	private Repository repository;
	
	public Factory(Context thisContext) throws InterruptedException, ExecutionException
	{
		repository = new Repository(thisContext);
	}
	
	public User getUser(String user, String password)
	{
		return this.repository.getUser(user, password);
	}
	
	// Faire toutes les autres méthodes d'accès au repository pour les VMs
}
