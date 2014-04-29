package com.esgi.studyingfurther;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Parameter extends Activity {

	private ListView myList;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	CheckBox cb;
	private static String url="";
	NotificationManager m_NotificationManager;  
    Notification m_Notification;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameter);
		
		myList = (ListView)findViewById(R.id.listPara);
		listItem = new ArrayList<HashMap<String, Object>>(); 
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Gerer mes classes");
        listItem.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "Se deconnecter");
        listItem.add(map);
        listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listparameter, new String[]{ "title"}, new int[]{R.id.textPara});
        myList.setAdapter(listItemAdapter);
        
        cb = (CheckBox)findViewById(R.id.cb);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) 
				{
//					getServerData(url);
					addNotificaction();
				}
				else {
					m_NotificationManager.cancel(1);
				}
			}
		});
        
        myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) 
				{
					Intent intent = new Intent(Parameter.this,GererClass.class);
					startActivity(intent);
				}
				else
				{
					new AlertDialog.Builder(Parameter.this)
					.setTitle("Warning")
					.setMessage("deco")
					.setPositiveButton("OK", null)
					.show();
					
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parameter, menu);
		return true;
	}
	/*
	private void getServerData(String url)
	{
		url += "?username=123456";
		HttpClient client = new DefaultHttpClient();
		HttpPost request; 
		try 
		{
			request = new HttpPost(new URI(url));
			HttpResponse response=client.execute(request);
			
			if(response.getStatusLine().getStatusCode()==200)
			{
				HttpEntity entity=response.getEntity();
				if(entity!=null)
				{
					String out = EntityUtils.toString(entity);
					new AlertDialog.Builder(this).setMessage(out).create().show();
				}
			}
		}
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	*/
	
	@SuppressWarnings("deprecation")
	private void addNotificaction() 
	{  
        m_NotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);  
        m_Notification = new Notification();
        m_Notification.when = System.currentTimeMillis();
        m_Notification.icon = R.drawable.android;  
        m_Notification.tickerText = "StudyingFurther";
        m_Notification.defaults = Notification.DEFAULT_ALL;  
        m_Notification.flags = Notification.FLAG_ONGOING_EVENT;
  
        Intent intent = new Intent(this, AffichageItem.class);  
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);  
        m_Notification.setLatestEventInfo(this, "NotificationTitle��", "Notification Content", pendingIntent);  
        m_NotificationManager.notify(1, m_Notification);  
          
    }  
}
