package com.esgi.studyingfurther.bl;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private String avatar;
	private UserStatus status;
	private boolean isProf;
	
	public User(int id, String firstName, String lastName, String login,String password, String avatar, int status)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.avatar = avatar;
		this.status = status == 0 ? UserStatus.Student : UserStatus.Professor;
		this.isProf = status == 0 ? false : true;
	}

	// Get / Set

	public int getId() 
	{
		return id;
	}

	public String getFirstName() 
	{
		return firstName;
	}

	public String getLastName() 
	{
		return lastName;
	}

	public String getNomComplet()
	{
		return lastName + " " + firstName;
	}

	public String getLogin() 
	{
		return login;
	}

	public String getPassword() 
	{
		return password;
	}

	public String getAvatar() 
	{
		return avatar;
	}

	public UserStatus getStatus() 
	{
		return status;
	}
	public boolean isProfessor()
	{
		return isProf;
	}

}
