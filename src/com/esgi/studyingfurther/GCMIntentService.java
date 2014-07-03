package com.esgi.studyingfurther;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	public static final String TAG = "GCMIntentService";
	public static final String SENDERID = "406531780211";
	
	private Intent MessageIntent = null; 
	private PendingIntent MessagePendingIntent = null; 
	
	private int MessageNotificationId = 1000; 
	public Notification MessageNotification = null; 
	private NotificationManager MessageNotificatioManager = null; 
	
	public GCMIntentService()
	{
		super (SENDERID);
	}
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Log.v(TAG, arg1.getStringExtra("postId"));
		String info = arg1.getStringExtra("postId");
		Intent intent = new Intent();
		intent.setAction("com.esgi.studingfurther.MessageService");
        intent.putExtra("postId", info);
        intent.setAction("com.esgi.studingfurther.MessageService");
        sendBroadcast(intent);
        
        MessageNotification = new Notification(); 
		MessageNotification.icon = R.drawable.android; 
		MessageNotification.tickerText = "StudyingFurther"; 
		MessageNotification.defaults = Notification.DEFAULT_SOUND; 
		MessageNotificatioManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); 
		
		MessageIntent = new Intent(this, NewsFeed.class); 
		MessagePendingIntent = PendingIntent.getActivity(this,0,MessageIntent,0); 
		MessageNotification.setLatestEventInfo(GCMIntentService.this,"New Message",info,MessagePendingIntent); 
		MessageNotificatioManager.notify(MessageNotificationId, MessageNotification); 
		MessageNotificationId++; 
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		Log.v(TAG, "onReistered");
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.v(TAG, "onUnreistered");
		// TODO Auto-generated method stub
		
	}

}
