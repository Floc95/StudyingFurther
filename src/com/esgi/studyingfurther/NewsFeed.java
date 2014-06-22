package com.esgi.studyingfurther;

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
import com.esgi.studyingfurther.vm.ManagerURL;
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
	private JSONObject currentUser;
	MainViewModel Manager = null;
	JSONArray news;
	ArrayList<JSONObject> comments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);

		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		try {
			Manager = new MainViewModel(new Factory());
			if(MainViewModel.isNetworkAvailable(this))
			{
			getNews();
			}
			else
			{
				MainViewModel.alertNetwork(this);
			}
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

	private void getNews() throws InterruptedException, ExecutionException,
			JSONException, IOException {

		this.currentUser = new JSONObject(getIntent().getExtras().getString(
				"currentUser"));
		this.news = new Repository().getNews(this.currentUser.getInt("id"));
		this.comments = new ArrayList<JSONObject>();
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		// **************************************** Boucle sur la listeview

		for (int i = 0; i < this.news.length(); i++) {

			JSONObject row = this.news.getJSONObject(i);
			this.comments.add(row);
			HashMap<String, Object> map = new HashMap<String, Object>();

			// ***************************************************************

			Bitmap conv_bm = MainViewModel
					.getRoundedCornerImage(ManagerURL.urlGetAvatar
							+ row.getJSONObject("utilisateur").getString(
									"avatar"));

			// **************************************************************

			map.put("titre", MainViewModel.decodeString(row.getString("titre")));
			map.put("contenu", MainViewModel.decodeString(row.getString("contenu")));
			map.put("nbcommentaires", row.getJSONArray("commentaires").length());
			map.put("img", conv_bm);
			map.put("newspic", "");// R.drawable.bout);
			map.put("heurPub",
					MainViewModel.decodeString(row.getString("dateCreation")));
			listItem.add(map);

		}

		// *************************************Fin de la boucle
		SimpleAdapter mSchedule = new SimpleAdapter(

		this.getBaseContext(), listItem, R.layout.activity_item_news_feed,
				new String[] { "img", "titre", "contenu", "newspic", "heurPub",
						"nbcommentaires" }, new int[] { R.id.avatarP,
						R.id.titleP, R.id.contenu, R.id.newspic, R.id.heurPubP,
						R.id.nbcommentaires });

		mSchedule.setViewBinder(new MyViewBinder());
		maListViewPerso.setAdapter(mSchedule);

	}

	public void comment(View v) {

		Intent intent = new Intent(getBaseContext(), Comments.class);
		intent.putExtra("comments",
				this.comments.get(maListViewPerso.getPositionForView(v))
						.toString());
		intent.putExtra("currentUser", this.currentUser.toString());
		startActivity(intent);
	}

	public void modification(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Modifier");
		adb.setMessage("Vous avez appuiez sur le button Modifier");
		adb.setPositiveButton("Ok", null);
		adb.show();

	}

	public void write(View v) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Button Write");
		adb.setMessage(""
				+ this.comments.get(maListViewPerso.getPositionForView(v))
						.toString());
		adb.setPositiveButton("Ok", null);
		adb.show();
	}

}
