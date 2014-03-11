package com.esgi.studyingfurther;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static java.lang.String.*;

public class Identification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identification, menu);
		return true;
	}



	public void SeConnecter(View v){

		if(this.Authentification("SIMO","SIMO")==true)
        {
		Intent intent = new Intent(this, NewsFeed.class);
		startActivity(intent);
        }


	}

    public boolean Authentification(String ID, String Password)
    {
        String username= ((EditText) findViewById(R.id.username)).getText().toString();
        String Pass= ((EditText) findViewById(R.id.username)).getText().toString();
      Log.v("info",Pass+":"+username);

         if(ID.equals(username) && Password.equals(Pass))
         {
             return  true;
         }
        return false;
    }
}
