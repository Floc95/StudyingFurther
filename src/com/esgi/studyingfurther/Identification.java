package com.esgi.studyingfurther;



import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.esgi.studyingfurther.vm.JSONParse;
import com.esgi.studyingfurther.vm.MainViewModel;



import com.google.gson.JsonParser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Identification extends Activity {

	private MainViewModel ManagerUser;
	String url = "http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";
	private HashMap<String, String> user;
	//JsonParser Parser=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
		ManagerUser = new MainViewModel(
				new com.esgi.studyingfurther.bl.Factory());
        user=new HashMap<String, String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identification, menu);
		return true;
	}

	public void seConnecter(View v) throws JSONException {

		String username = ((EditText) findViewById(R.id.username)).getText()
				.toString();
		String Passwordd = ((EditText) findViewById(R.id.password)).getText()
				.toString();
		new JSONParse(this,this.url).execute();
		
       
		// String url_=String.format(url,"?","&");

		// ws.setURL(url);

		// Log.v("webservice", ws.getResponseHttp());
		// Log.v("webservice",
		// r.convertJeson("http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99"));
		// r.convertJeson("http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99");

		// if(this.authentification("simo", "simo"))
		// {
		// Intent intent = new Intent(this, NewsFeed.class);
		// startActivity(intent);
		// }

	}

}
