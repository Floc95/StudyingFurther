package com.esgi.studyingfurther.bl;

import java.util.Collection;
import java.util.Date;

public class Post {

	private int id;
	private String title;
	private String content;
	private Date creationDate;
	private Date modificationDate;
	private String image;
	private Collection<String> attachements;

	private User owner;
	private Collection<Comment> comments;

	public Post(int id, String title, String content, Date creationDate,
			Date modificationDate, String image, Collection<String> attachements) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.image = image;
		this.attachements = attachements;
	}

	// Get / Set

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Collection<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(Collection<String> attachements) {
		this.attachements = attachements;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComment(Collection<Comment> comments) {
		this.comments = comments;
	}

}
