package com.esgi.studyingfurther;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.esgi.studyingfurther.bl.Factory;
import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.MainViewModel;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
			ExecutionException, JSONException {

		ManagerUser = new MainViewModel(new Factory(this));
		String username = UserName.getText().toString();
		String Password = PassWord.getText().toString();
       //j new Repository(this).getClass();
        
		if (ManagerUser.authenticate(username, Password) == true) {
            
			Intent intent = new Intent(this, NewsFeed.class);
		    intent.putExtra("userId",ManagerUser.getCurrentUserId());
			startActivity(intent);
			
		} else {
			this.UserName.setFocusable(true);
			this.UserName.requestFocus();
		}

	}

}
