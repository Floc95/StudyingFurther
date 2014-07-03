package com.esgi.studyingfurther;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.ManagerURL;

public class GererClass extends Activity {

	Button add;
	ListView myList;
	SimpleAdapter listItemAdapter;
    ArrayList<HashMap<String, Object>> listItem, listUsers, listAllUsers;
    EditText addEditText,addName;
    int userId = 0;
    int groupId = 0;
    int realPosition;
    String userIdString = "";
    JSONArray groups, groupUsers,allUsers;
    MainViewModel Manager = null;
    String TAG = "GereClass";
    String[] userList;
    String[] allUsersList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerer_class);
		
		add = (Button) findViewById(R.id.add);
		myList = (ListView) findViewById(R.id.listClass);
		//TODO
		this.userId = getIntent().getExtras().getInt("userId", 0);
		Log.v("userid", userId+"");
		
		//get the groups and the users
		try {
			Manager = new MainViewModel(new Factory());
			getGroups();
			getAllUsers();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
        //adapter the list with the SimpleAdapter
        listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listparameter, new String[]{"libelle"}, new int[]{R.id.textPara});
        myList.setAdapter(listItemAdapter);
        
        //list onclicklistener
        myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long id) 
			{
				if (id<0) {
					return;
				}
				realPosition = (int)id;
				groupId = Integer.valueOf(listItem.get(realPosition).get("idGroupe").toString());
				try {
					getGroupUsers();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					Log.v(TAG, e.toString());
					e.printStackTrace();
				}
				
				//alertdialog for display the memeber of the class
				new AlertDialog.Builder(GererClass.this)
				.setTitle("Memeber of " + listItem.get(realPosition).get("libelle").toString())
				.setItems(userList, new DialogInterface.OnClickListener() {
					
					//click the name for remove
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final int position = which;
						new AlertDialog.Builder(GererClass.this)
						.setTitle("Attention")
						.setMessage("Remove "+ listUsers.get(which).get("nom")+" "+listUsers.get(which).get("prenom")+" from the groupe?")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
						        new UploadUrlTask(GererClass.this).execute("removeUser", ManagerURL.removeUserUrl, userIdString, listItem.get(realPosition).get("idGroupe").toString(), listUsers.get(position).get("id").toString());

							}
						})
						.setNegativeButton("NO", null)
						.show();
						
					}
				})//button add to add a new member to the class
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						final boolean[] selected = new boolean[allUsersList.length];
						//initialization of the boolean[]
						for (int i = 0; i < selected.length; i++) {
							selected[i] = false;
						}
						new AlertDialog.Builder(GererClass.this)
						.setTitle("Choose the students")
						.setMultiChoiceItems(allUsersList, selected, new OnMultiChoiceClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked) {
								selected[which] = isChecked;
								
							}
						})
						.setPositiveButton("OK", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								for (int i = 0; i < selected.length; i++) {
									if (selected[i] && allUsersList[i].equalsIgnoreCase(listAllUsers.get(i).get("prenom").toString() +" "+ listAllUsers.get(i).get("nom").toString())) 
									{
										new UploadUrlTask(GererClass.this).execute("addUser", ManagerURL.addUserUrl, userIdString, listItem.get(realPosition).get("idGroupe").toString(), listAllUsers.get(i).get("id").toString());
									}
								}
								
							}
						}).show();
						
					}
					
					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						addName = new EditText(GererClass.this);
//						new AlertDialog.Builder(GererClass.this)
//						.setTitle("Input the Login of the user who you want to add")
//						.setIcon(android.R.drawable.ic_dialog_info)
//						.setView(addName)
//						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								if (addName.getText().toString().trim().equals("")) 
//								{
//									new AlertDialog.Builder(GererClass.this)    
//					                .setTitle("Warning")  
//					                .setMessage("Can not be empty")  
//					                .setPositiveButton("OK", null)  
//					                .show();
//								}
//								else 
//								{
//									Log.v("addname", addName.getText().toString());
//									boolean added = false;
//									for (int i = 0; i < listAllUsers.size(); i++) 
//									{
//										Log.v("login", listAllUsers.get(i).get("login").toString());
//										
//										if (listAllUsers.get(i).get("login").toString().equals(addName.getText().toString())) 
//										{
//											new UploadUrlTask(GererClass.this).execute("addUser", ManagerURL.addUserUrl, userIdString, listItem.get(realPosition).get("idGroupe").toString(), listAllUsers.get(i).get("id").toString());
//											added = true;
//										}
//										
//									}
//									if (!added) {
//										new AlertDialog.Builder(GererClass.this)
//										.setTitle("Warning")  
//						                .setMessage("User doesn't exist")
//						                .setPositiveButton("OK", null)
//										.show();
//									}
//								}
//								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		                        imm.hideSoftInputFromWindow(addName.getWindowToken(), 0);
//							
//							}
//						})
//						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		                        imm.hideSoftInputFromWindow(addName.getWindowToken(), 0);
//								
//							}
//						})
//						.show();
//	
//						
//					}
				})
				.setNegativeButton("Cancel", null)
				.show();
				
			}
		});
        
        //listenr for the button add to add a class
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addEditText = new EditText(GererClass.this);
				new AlertDialog.Builder(GererClass.this)  
				.setTitle("Input the class name")  
				.setIcon(android.R.drawable.ic_dialog_info)  
				.setView(addEditText)  
				.setPositiveButton("OK", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (addEditText.getText().toString().trim().equals("")) 
						{
							new AlertDialog.Builder(GererClass.this)    
							                .setTitle("Warning")  
							                .setMessage("Can not be empty")  
							                .setPositiveButton("OK", null)  
							                .show();  
						}
						else 
						{
							//new task to upload the new class
					        new UploadUrlTask(GererClass.this).execute("addClasse", ManagerURL.addGroupUrl, userIdString, addEditText.getText().toString());
					        try {
								getGroups();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
					        listItemAdapter = new SimpleAdapter(GererClass.this.getBaseContext(), listItem, R.layout.listparameter, new String[]{"libelle"}, new int[]{R.id.textPara});
							myList.setAdapter(listItemAdapter);
							listItemAdapter.notifyDataSetChanged();
							
						}
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(addEditText.getWindowToken(), 0);
					}
				})  
				.setNegativeButton("Cancel", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(addEditText.getWindowToken(), 0);
					}
				})  
				.show();  
				addEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gerer_class, menu);
		return true;
	}
	
	/**
	 * function to get all the groups
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public void getGroups() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		
		userIdString = "" + userId;
		this.groups = new Repository().getGroup(userId);
		listItem = null;
		listItem = new ArrayList<HashMap<String, Object>>();
		listItem.clear();
		for (int i = 0; i < this.groups.length(); i++) {
			JSONObject obj = groups.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("idGroupe", MainViewModel.decodeString(obj.getString("idGroupe")));
			map.put("libelle", MainViewModel.decodeString(obj.getString("libelle")));
			listItem.add(map);
			
		}
	}
	
	/**
	 * function to get all the users of a group
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public void getGroupUsers() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		groupUsers = new Repository().getGroupUsers(groupId);
		listUsers = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < this.groupUsers.length(); i++) {
			JSONObject obj = groupUsers.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("id", MainViewModel.decodeString(obj.getString("id")));
			map.put("nom", MainViewModel.decodeString(obj.getString("nom")));
			map.put("prenom", MainViewModel.decodeString(obj.getString("prenom")));
			map.put("login", MainViewModel.decodeString(obj.getString("login")));
			listUsers.add(map);
			
		}

		userList = new String[listUsers.size()];
		for (int i = 0; i < listUsers.size(); i++) {
			String tmp = listUsers.get(i).get("prenom").toString() +" "+ listUsers.get(i).get("nom").toString();
			userList[i] = tmp;
		}
	}
	
	/**
	 * function to get all the users exist
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public void getAllUsers() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		allUsers =  new Repository().getAllUssers();
		listAllUsers = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < allUsers.length(); i++) {
			JSONObject obj = allUsers.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("id", MainViewModel.decodeString(obj.getString("id")));
			map.put("nom", MainViewModel.decodeString(obj.getString("nom")));
			map.put("prenom", MainViewModel.decodeString(obj.getString("prenom")));
			map.put("login", MainViewModel.decodeString(obj.getString("login")));
			listAllUsers.add(map);
			Log.v(TAG, listAllUsers.get(i).get("nom")+" "+listAllUsers.get(i).get("login")+" "+listAllUsers.get(i).get("id"));
		}
		
		allUsersList = new String[listAllUsers.size()];
		for (int i = 0; i < listAllUsers.size(); i++) {
			String tmp = listAllUsers.get(i).get("prenom").toString() +" "+ listAllUsers.get(i).get("nom").toString();
			allUsersList[i] = tmp;
		}
	}
	
	
}
