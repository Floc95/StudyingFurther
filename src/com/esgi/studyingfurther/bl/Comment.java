package com.esgi.studyingfurther.bl;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import android.net.Uri;
import android.util.Log;

import com.esgi.studyingfurther.vm.AddCommentTask;
import com.esgi.studyingfurther.vm.ManagerURL;

public class Comment {

	private int id;
	private String content;
	private Date creationDate;
	private Date modificationDate;

	private User owner;

	public Comment(int id, String content, Date creationDate,Date modificationDate, User owner) {
		this.id = id;
		this.content = content;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.owner = owner;
	}
	public static void addComment(int idUser,int postId,String Content) throws InterruptedException, ExecutionException
	{
		
		String requestAddComment = ManagerURL.urlAddComment+ "&userId="
		        + idUser
		        + "&postId="
				+ postId
				+ "&content="
				+ Uri.encode(Content);
	 Log.v("comment",	new AddCommentTask().execute(requestAddComment).get());
	}

	public int getId() {
		return id;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
