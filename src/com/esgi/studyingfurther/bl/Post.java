package com.esgi.studyingfurther.bl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ToggleButton;

import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.AddCommentTask;
import com.esgi.studyingfurther.vm.DownloadImageTask;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;

public class Post {

	private int id;
	private String title;
	private String content;
	private String creationDate;
	private String modificationDate;
	private Bitmap image;
	private String plusun;
	private Collection<String> attachements;
	private int nbcommentaires;
	private User owner;
	private Bitmap avatar;
	private int statutCurrentUser = -1;
	private Collection<JSONObject> comments;

	public Post() {
	}

	public Post(int id, String title, String content, String creationDate,
			String modificationDate, Bitmap image,
			Collection<String> attachements, int nbcommentaires, Bitmap avatar,
			User owner, int statutCurrentUser, String plusun) {

		this.id = id;
		this.title = title;
		this.content = content;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.nbcommentaires = nbcommentaires;
		this.image = image;
		this.attachements = attachements;
		this.avatar = avatar;
		this.owner = owner;
		this.statutCurrentUser = statutCurrentUser;
		this.plusun = plusun;
	}

	public ArrayList<Post> getPosts(JSONArray news, int statutCurrentUser)
			throws JSONException, InterruptedException, ExecutionException,
			UnsupportedEncodingException {
		
		ArrayList<Post> listItem = new ArrayList<Post>();

		// Loop on the listeview

		for (int i = 0; i < news.length(); i++) {

			JSONObject row = news.getJSONObject(i);
			JSONObject ownerUser = row.getJSONObject("utilisateur");
			User owner = new User(ownerUser.getInt("id"),
					ownerUser.getString("nom"), ownerUser.getString("prenom"),
					ownerUser.getString("login"),
					ownerUser.getString("password"),
					ownerUser.getString("avatar"), ownerUser.getInt("statut"));

			// this.comments.add(row);
			Bitmap conv_bm = MainViewModel.getRoundedCornerImage(ManagerURL.urlGetAvatar+ row.getJSONObject("utilisateur").getString("avatar"));

			Post post = new Post(

			row.getInt("id"),

			row.getString("titre"),

			row.getString("contenu"),

			row.getString("dateCreation"),

			row.getString("dateModification"),

			new DownloadImageTask().execute(row.getString("image")).get(),

			null,

			row.getJSONArray("commentaires").length(),

			conv_bm, owner, statutCurrentUser,

			row.getString("plusun")

			);

			listItem.add(post);
		}
		return listItem;

	}
	
	// Add plus one or delete it

	public static String addPlusOne(int userId, int postId) throws InterruptedException, ExecutionException 
	{
		   String requestAddPlusOne =  ManagerURL.urlAddorRemovePlusOne+ "&userId="+userId+"&postid="+postId;
		       
	       return new AddCommentTask().execute(requestAddPlusOne).get();
	}
	
	public static String removePlusOne(int userId,int postId) throws InterruptedException, ExecutionException
	{
		 String requestDelPlusOne = ManagerURL.urlAddorRemovePlusOne+ "&userId="+userId+"&postid="+postId;
	       
	       return new AddCommentTask().execute(requestDelPlusOne).get();
	}

	// Get / Set

	public String getPlusun() {
		return plusun;
	}

	public void setPlusun(String plusun) {
		this.plusun = plusun;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Collection<String> getAttachements() {
		return attachements;
	}

	public void setAttachements(Collection<String> attachements) {
		this.attachements = attachements;
	}

	public int getNbcommentaires() {
		return nbcommentaires;
	}

	public void setNbcommentaires(int nbcommentaires) {
		this.nbcommentaires = nbcommentaires;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Bitmap getAvatar() {
		return avatar;
	}

	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}

	public int getStatutCurrentUser() {
		return statutCurrentUser;
	}

	public void setStatutCurrentUser(int statutCurrentUser) {
		this.statutCurrentUser = statutCurrentUser;
	}

	public Collection<JSONObject> getComments() {
		return comments;
	}

	public void setComments(Collection<JSONObject> comments) {
		this.comments = comments;
	}
	
	public static String addPost(int idUser,String groupId,String Content) throws InterruptedException, ExecutionException
	{
		
		String requestAddComment = ManagerURL.urlAddPost+ "&userId="
		        + idUser
		        + "&groupId="
				+ groupId
				+ "&content="
				+ Uri.encode(Content);
	       return new AddCommentTask().execute(requestAddComment).get();
	}
	
	public static String addPostWithPhoto(int idUser,String groupId,String Content,String imgUrl) throws InterruptedException, ExecutionException
	{
		
		String requestAddComment = ManagerURL.urlAddPost+ "&userId="
		        + idUser
		        + "&groupId="
				+ groupId
				+ "&content="
				+ Uri.encode(Content)
				+ "&documentUrl="
				+ imgUrl;
	       return new AddCommentTask().execute(requestAddComment).get();
	}

}
