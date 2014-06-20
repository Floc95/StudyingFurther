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

public class GererClass extends Activity {

	Button add;
	ListView myList;
	SimpleAdapter listItemAdapter;
    ArrayList<HashMap<String, Object>> listItem, listUsers;
    EditText modify,addEditText;
    int userId = 0;
    int groupId = 0;
    int realPosition;
    String userIdString = "";
    JSONArray groups, users;
    MainViewModel Manager = null;
    private String addGroup = "http://www.your-groups.com/API/AddGroup?key=7e2a3a18cd00ca322f60c28393c43264";
    private String removeUser = "http://www.your-groups.com/API/RemoveUserOfGroup?key=7e2a3a18cd00ca322f60c28393c43264";
    String TAG = "GereClass";
    String[] userList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerer_class);
		
		add = (Button) findViewById(R.id.add);
		myList = (ListView) findViewById(R.id.listClass);
		
		try {
			Manager = new MainViewModel(new Factory());
			getGroups();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
        
        listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listparameter, new String[]{"libelle"}, new int[]{R.id.textPara});
        myList.setAdapter(listItemAdapter);
        
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
				
				
				new AlertDialog.Builder(GererClass.this)
				.setTitle("Memeber of " + listItem.get(realPosition).get("libelle").toString())
				.setItems(userList, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final int position = which;
						new AlertDialog.Builder(GererClass.this)
						.setTitle("Attention")
						.setMessage("Remove "+ listUsers.get(which).get("nom")+" "+listUsers.get(which).get("prenom")+" from the groupe?")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
						        new UploadUrlTask(GererClass.this).execute("removeUser", removeUser, userIdString, listItem.get(realPosition).get("idGroupe").toString(), listUsers.get(position).get("id").toString());

							}
						})
						.setNegativeButton("NO", null)
						.show();
						
					}
				})
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setNegativeButton("Cancel", null)
				.show();
				
			}
		});
        
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addEditText = new EditText(GererClass.this);
				new AlertDialog.Builder(GererClass.this)  
				.setTitle("ajouter le nom")  
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
					        new UploadUrlTask(GererClass.this).execute("addClasse", addGroup, userIdString, addEditText.getText().toString());
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
	
	public void getGroups() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		this.userId = 1;//getIntent().getExtras().getInt("userId", 0);
		userIdString = "" + userId;
		this.groups = new Repository().getGroup(userId);
		listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < this.groups.length(); i++) {
			JSONObject obj = groups.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("idGroupe", Manager.decodeString(obj.getString("idGroupe")));
			map.put("libelle", Manager.decodeString(obj.getString("libelle")));
			listItem.add(map);
			
		}
	}
	
	public void getGroupUsers() throws InterruptedException, ExecutionException, JSONException, UnsupportedEncodingException
	{
		users = new Repository().getGroupUsers(groupId);
		listUsers = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < this.users.length(); i++) {
			JSONObject obj = users.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
			
			map.put("id", Manager.decodeString(obj.getString("id")));
			map.put("nom", Manager.decodeString(obj.getString("nom")));
			map.put("prenom", Manager.decodeString(obj.getString("prenom")));
			map.put("login", Manager.decodeString(obj.getString("login")));
			listUsers.add(map);
			
		}

		userList = new String[listUsers.size()];
		for (int i = 0; i < listUsers.size(); i++) {
			String tmp = listUsers.get(i).get("prenom").toString() +" "+ listUsers.get(i).get("nom").toString();
			userList[i] = tmp;
		}
	}

}
