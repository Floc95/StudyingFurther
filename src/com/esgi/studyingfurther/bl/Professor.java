package com.esgi.studyingfurther.bl;

import java.util.Collection;

public class Professor extends User {

	private Collection<Classroom> classrooms;

	public Professor(int id, String firstName, String lastName, String login,
			String password, String avatar, int status) {
		super(id, firstName, lastName, login, password, avatar, status);
	}

	// Get / Set

	public Collection<Classroom> getClassrooms() {
		return this.classrooms;
	}

	public void setClassrooms(Collection<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
}
