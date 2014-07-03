package com.esgi.studyingfurther;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gcm.GCMRegistrar;

public class Parameter extends Activity {

	final String TAG = "Parameter";
	private ListView myList;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	CheckBox cb;
	NotificationManager m_NotificationManager;  
    Notification m_Notification;
    
    private boolean isProf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameter);
		
		isProf = getIntent().getExtras().getBoolean("status");
		myList = (ListView)findViewById(R.id.listPara);
		listItem = new ArrayList<HashMap<String, Object>>(); 
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (isProf) {
        	map.put("title", "Class Management ");
            listItem.add(map);
		}
        
        //definition of the listAdapter
        map = new HashMap<String, Object>();
        map.put("title", "Disconnect");
        listItem.add(map);
        listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listparameter, new String[]{ "title"}, new int[]{R.id.textPara});
        myList.setAdapter(listItemAdapter);
        
        cb = (CheckBox)findViewById(R.id.cb);
        //if the service of notification is running, it will set the true to the checkbox as default
		cb.setChecked(isWorked());
		//add a listener to the checkbox
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Intent intent= new Intent(Parameter.this, com.esgi.studyingfurther.MessageService.class);
				intent.putExtra("userId",getIntent().getExtras().getInt("userId", 0));
				Log.v("parameter", ""+getIntent().getExtras().getInt("userId", 0));
				if (isChecked) 
				{
					
					
					GCMRegistrar.checkDevice(Parameter.this);
					GCMRegistrar.checkManifest(Parameter.this);
					final String regId = GCMRegistrar.getRegistrationId(Parameter.this);
					if (regId.equals("")) 
					{
						GCMRegistrar.register(Parameter.this, GCMIntentService.SENDERID);
						Log.v(TAG, "New Device:"+ GCMRegistrar.isRegistered(Parameter.this) + " Regid= "+GCMRegistrar.getRegistrationId(Parameter.this));
					} 
					else 
					{
						Log.v(TAG, "Already registered");
					}
					final String deviceId = GCMRegistrar.getRegistrationId(Parameter.this);
					intent.putExtra("deviceId", deviceId);
					intent.setAction("com.esgi.studyingfurther.MessageService");
					startService(intent);
				}
				else 
				{
					intent.setAction("com.esgi.studyingfurther.MessageService");
					stopService(intent);
					GCMRegistrar.unregister(getBaseContext());
				}
			}
		});
        
        //add a listener to the listadapter
        myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//arg2 == 0 jump to the selection to manage the class
				if (arg2 == 0) 
				{
					Intent intent = new Intent(Parameter.this,GererClass.class);
					intent.putExtra("userId",getIntent().getExtras().getInt("userId", 0));
					Log.v("parameter userid", getIntent().getExtras().getInt("userId", 0)+"");
					startActivity(intent);
				}
				//arg2 !=0 jump to disconnect
				else
				{
					new AlertDialog.Builder(Parameter.this)
					.setTitle("Attention")
					.setMessage("Are you sure to log out?")
					.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							android.content.SharedPreferences prefs = getSharedPreferences("UserData", 0);
							android.content.SharedPreferences.Editor editor = prefs.edit();
							editor.putString("currentuser","");
							editor.commit();
							
						    prefs = getSharedPreferences("news", 0);
						    editor = prefs.edit();
							editor.putString("news","");
							
							Intent intent = new Intent(Parameter.this, Identification.class);
					        intent.putExtra("finish", true);
					        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
					        startActivity(intent);
					        finish();
						}
					})
					.setNegativeButton("NO", null)
					.show();
				}
			}
		});
	}


	/**
	 * function to find the service of Notification
	 * @return boolean
	 */
	public boolean isWorked()
	{
		boolean isworked = false;
		ActivityManager myManager=(ActivityManager)Parameter.this.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
		for(int i = 0 ; i<runningService.size();i++)
		{
			if(runningService.get(i).service.getClassName().toString().equals("com.esgi.studyingfurther.MessageService"))
			{
				isworked = true;
				break;
			}
		}
			return isworked;
	}
	
	
}
