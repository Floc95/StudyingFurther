package com.esgi.studyingfurther.bl;

import com.esgi.studyingfurther.dal.Repository;

public class Factory {

	private Repository repository;
	
	public Factory()
	{
		repository = new Repository();
	}
	
	public User getUser(String user, String password)
	{
		return this.repository.getUser(user, password);
	}
	
	// Faire toutes les autres m�thodes d'acc�s au repository pour les VMs
}
