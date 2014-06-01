package com.esgi.studyingfurther;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.JSONParser;
import com.esgi.studyingfurther.vm.MainViewModel;






import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Identification extends Activity {

	private MainViewModel ManagerUser;
	
//	   String UrlUser = "http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
		try {
			
			ManagerUser = new MainViewModel(new Factory(this));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identification, menu);
		return true;
	}

	public void seConnecter(View v) throws InterruptedException, ExecutionException  {

			String username = ((EditText) findViewById(R.id.username)).getText().toString();
			String Password = ((EditText) findViewById(R.id.password)).getText().toString();
			
			//	new JSONParser(getBaseContext(), UrlUser);
			
	 if(ManagerUser.authenticate(username, Password)==true)
		 {
		 
			 Intent intent = new Intent(this, NewsFeed.class);
			 startActivity(intent);
		 }

	}

}
