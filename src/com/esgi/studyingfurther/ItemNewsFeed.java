package com.esgi.studyingfurther;



import java.util.concurrent.ExecutionException;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.vm.MainViewModel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class ItemNewsFeed extends Activity {

	MainViewModel manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_news_feed);
		
		 try {
			manager=new MainViewModel(new Factory(this));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 ImageView img1 = (ImageView) findViewById(R.id.newspic);
		 ImageView img2 = (ImageView) findViewById(R.id.avatar);
		 Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.calender);
		 Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
		 Bitmap conv_bm =manager.getRoundedRectBitmap(resized, 100);
		 img1.setImageBitmap(conv_bm);
		 img2.setImageBitmap(conv_bm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_news_feed, menu);
		return true;
	}



}
