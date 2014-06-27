package com.esgi.studyingfurther;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.vm.MainViewModel;

public class Identification extends Activity {

	private MainViewModel ManagerUser;
	private EditText UserName;
	private EditText PassWord;
	

	// String UrlUser =
	// "http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
		this.UserName = ((EditText) findViewById(R.id.username));
		this.PassWord = ((EditText) findViewById(R.id.password));
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identification, menu);
		return true;
	}

	public void seConnecter(View v) throws InterruptedException,
			ExecutionException, JSONException, IOException {
		
		
		
		if(MainViewModel.isNetworkAvailable(this))
		{
			Toast.makeText(getApplicationContext(),"Connecting...Please wait",Toast.LENGTH_LONG).show();
		ManagerUser = new MainViewModel(new Factory());
		String username = UserName.getText().toString();
		String Password = PassWord.getText().toString();
		// j new Repository(this).getClass();

		if (ManagerUser.authenticate(username, Password) == true) {
			
			Intent intent = new Intent(this, NewsFeed.class);
			intent.putExtra("currentUser", ManagerUser.getCurrentUser()
					.toString());
			startActivity(intent);

		} 
		else {
			Toast.makeText(this,"Error Please Check your Username/Password",Toast.LENGTH_LONG).show();
			this.UserName.setTextColor(Color.parseColor("#F01414"));
			this.UserName.setTextColor(Color.parseColor("#F01414"));
			this.UserName.setFocusable(true);
			this.UserName.requestFocus();
		}
		}
	else
	{
		MainViewModel.alertNetwork(this);
	}

	

}

}