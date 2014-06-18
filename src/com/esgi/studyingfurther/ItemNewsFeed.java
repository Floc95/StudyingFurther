package com.esgi.studyingfurther;



import java.util.concurrent.ExecutionException;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.vm.MainViewModel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class ItemNewsFeed extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_news_feed);
		Log.v("itemsnew","*******");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_news_feed, menu);
		return true;
	}



}
