package com.esgi.studyingfurther.bl;

import java.util.Collection;

public class Classroom {

	private Collection<Student> students;

	public Classroom() {

	}

	public Classroom(Collection<Student> students) {
		this.students = students;
	}

	// Get / Set

	public Collection<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

}
