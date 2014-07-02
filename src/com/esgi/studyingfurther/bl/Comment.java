package com.esgi.studyingfurther.bl;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.esgi.studyingfurther.vm.AddCommentTask;
import com.esgi.studyingfurther.vm.DownloadImageTask;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;

public class Comment {

	private int id;
	private String content;
	private String creationDate;
	private String modificationDate;
	private User owner;
	private String plusun;
	private int statutCurrentUser;
	private Bitmap avatar;
	
	public Comment()
	{}

	public Comment(int id, String content, String creationDate,String modificationDate, User owner,String plusun,int statutCurrentUser,Bitmap avatar) {
		this.id = id;
		this.content = content;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.owner = owner;
		this.plusun=plusun;
		this.statutCurrentUser=statutCurrentUser;
		this.avatar=avatar;
	}
	
	public  ArrayList<Comment> getComment(JSONObject newsFeed,int statutCurrentUser) throws JSONException, InterruptedException, ExecutionException
	{
		
		
		ArrayList<Comment> listItem = new ArrayList<Comment>();
        JSONArray Arraycomments=newsFeed.getJSONArray("commentaires");
        
        Log.v("++1",Arraycomments.toString());
		// Loop on the listeview

	for (int i = 0; i < Arraycomments.length(); i++) {

			
			JSONObject Comment = Arraycomments.getJSONObject(i);
			JSONObject ownerComment=Comment.getJSONObject("user");
			User owner = new User(ownerComment.getInt("id"),
					ownerComment.getString("nom"), ownerComment.getString("prenom"),
					ownerComment.getString("login"),
					ownerComment.getString("password"),
					ownerComment.getString("avatar"), ownerComment.getInt("statut"));

			// this.comments.add(row);
			Bitmap avatar = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ owner.getAvatar());

			Comment cmt = new Comment(

		     Comment.getInt("id"),

		     Comment.getString("contenu"),

		     Comment.getString("dateCreation"),

		     Comment.getString("dateModification"),
		     
		     owner,
		     
		     Comment.getString("plusun"),
   
		     statutCurrentUser,
		     
		     avatar
			);

			listItem.add(cmt);
		}
		return listItem;
			
		
	}
	

	public static void modificationComment()
	{
		
	}
	public static String addComment(int idUser,int postId,String Content) throws InterruptedException, ExecutionException
	{
		
		String requestAddComment = ManagerURL.urlAddComment+ "&userId="
		        + idUser
		        + "&postId="
				+ postId
				+ "&content="
				+ Uri.encode(Content);
	       return new AddCommentTask().execute(requestAddComment).get();
	
	}

	
	// GET SET
	public Bitmap getAvatar() {
		return avatar;
	}

	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}
	
	public int getstatutCurrentUser() {
		return this.statutCurrentUser;
	}

	public void setstatutCurrentUser(int idCurrentUser) {
		this.statutCurrentUser = idCurrentUser;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPlusun() {
		return plusun;
	}

	public void setPlusun(String plusun) {
		this.plusun = plusun;
	}

	
}
