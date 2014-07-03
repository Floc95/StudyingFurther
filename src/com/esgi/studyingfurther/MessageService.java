package com.esgi.studyingfurther;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MessageService extends Service {

	public static final String TAG = "MessageService";
	private MessageThread message = null; 

	
	
	String Url = "http://www.your-groups.com/API/RegisterForNotifications?key=7e2a3a18cd00ca322f60c28393c43264";
	String deviceID;
	int userId;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		
		userId = intent.getExtras().getInt("userId", 0);
		deviceID = intent.getExtras().getString("deviceID");
		Log.v("MessageService", userId+"");
		super.onStart(intent, startId);
	}
	@Override
	public void onCreate() {
		Log.v("service","creat");
		super.onCreate();
	}
	
	

	//initialization of the notification
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("Notification", "start");
		
//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		deviceID = tm.getDeviceId();
		Log.v(TAG, "deviceId: "+deviceID);
		

		message = new MessageThread(); 
		message.start(); 
		return super.onStartCommand(intent, flags, startId);
	}
	
	// thread of the message, try to visit the web service by a delay.
	class MessageThread extends Thread{
		
		@SuppressWarnings("deprecation")
		public void run() 
		{ 
			while(true)
			{ 
				try 
				{
					Thread.sleep(59000);
					new connectWebService().execute(Url+"&deviceId="+deviceID+"&userId="+userId) ;
					
					 
				} catch (InterruptedException e) { 
				e.printStackTrace(); 
				} 
			} 
		}
		
	}
	
	public class connectWebService extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			if (params != null && params.length > 0) 
			{
				try {
					result = uploadUrl(params[0]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		
		/**
		 * function to make the connection to the web service and upload
		 * @param myUrl
		 * @return
		 * @throws IOException
		 */
		private String uploadUrl(String myUrl) throws IOException {
			String contentAsString = null;
			HttpURLConnection conn = null;
			InputStream is = null;
			// Only display the first 1000 characters of the retrieved web page content.
			int len = 1000;

			try {
				URL url = new URL(myUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);

				// Starts the query
				conn.connect();
				is = conn.getInputStream();

				// Convert the InputStream into a String
				contentAsString = readIt(is, len);
			}
			finally {
				// Makes sure that the InputStream is closed after the app is finished using it.
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
			}

			return contentAsString;
		}
		
		/**
		 * function to get the information of a type of InputStream and return a String
		 * @param stream
		 * @param len
		 * @return String
		 * @throws IOException
		 * @throws UnsupportedEncodingException
		 */
		public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
			Reader reader = null;
			reader = new InputStreamReader(stream, "UTF-8");
			char[] buffer = new char[len];
			reader.read(buffer);
			reader.close();
			return new String(buffer);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.v("Notificationservice", result.substring(0,5));
			super.onPostExecute(result);
		}
		
	}	

}
