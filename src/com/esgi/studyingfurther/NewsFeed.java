package com.esgi.studyingfurther;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.bl.User;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.DownloadImageTask;
import com.esgi.studyingfurther.vm.MainViewModel;
import com.esgi.studyingfurther.vm.MyViewBinder;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsFeed extends Activity {

	private ListView maListViewPerso;
	private int userId;
	private Bundle bundle;
	MainViewModel Manager = null;
	JSONArray News;
    private String PrefixurlPic="http://www.your-groups.com";
    private static final int ONE_ID = Menu.FIRST+1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		try {
			Manager = new MainViewModel(new Factory());
			getNews();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Méthode appelée lors de la sélection d'un élément du menu
	 * 
	 * @param item
	 */
	/* Called whenever we call invalidateOptionsMenu() */

	private void getNews() throws InterruptedException, ExecutionException,JSONException, IOException {

		this.userId = getIntent().getExtras().getInt("userId", 0);
		this.News = new Repository().getNews(userId);
		
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		
//**************************************** Boucle sur la listeview
		
		for (int i = 0; i < this.News.length(); i++) {
			
			JSONObject row = this.News.getJSONObject(i);
			HashMap<String, Object> map=new HashMap<String, Object>();
		
			//***************************************************************
			
			 Bitmap bm = new DownloadImageTask().execute(this.PrefixurlPic+row.getJSONObject("utilisateur").getString("avatar")).get();
			 Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
			 Bitmap conv_bm =getRoundedCornerImage(resized);
	        
			 
			 //**************************************************************

			map.put("titre", Manager.decodeString(row.getString("titre")));
			map.put("contenu",Manager.decodeString(row.getString("contenu")));
			map.put("nbcommentaires", row.getJSONArray("commentaires").length());
			map.put("img", conv_bm);
			map.put("newspic", R.drawable.bout);
			map.put("heurPub",Manager.decodeString(row.getString("dateCreation")));
			listItem.add(map);

		}
		
//*************************************Fin de la  boucle
		SimpleAdapter mSchedule = new SimpleAdapter(
				
				this.getBaseContext(),listItem, R.layout.activity_item_news_feed,
				new String[] { "img", "titre", "contenu", "newspic","heurPub","nbcommentaires" }, 
				new int[] { R.id.avatar, R.id.title,R.id.contenu, R.id.newspic, R.id.heurPub,R.id.nbcommentaires }
		);
		mSchedule.setViewBinder(new MyViewBinder());
		maListViewPerso.setAdapter(mSchedule);
		
		//******* Action onClick sur un item de la liste view
		
		

	}
	


	public void modification(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Modifier");
		adb.setMessage("Vous avez appuiez sur le button Modifier");
		adb.setPositiveButton("Ok", null);
		adb.show();

	}

	public void commentaire(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Publier");
		adb.setMessage("Vous avez appuiez sur le button Publier");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

	public void write(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Write");
		adb.setMessage("Vous avez appuiez sur le button Write");
		adb.setPositiveButton("Ok", null);
		adb.show();
	}
      
	public static Bitmap getRoundedCornerImage(Bitmap bitmap) {
		
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 100;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, ONE_ID, Menu.NONE,"Parametre");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == ONE_ID) {
			Intent intent = new Intent(this, Parameter.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
