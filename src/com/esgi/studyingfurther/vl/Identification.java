package com.esgi.studyingfurther.vl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.esgi.studyingfurther.R;

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

    public void CreateUser(View v){

            Intent intent = new Intent(this, Inscription.class);
            startActivity(intent);

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
        String Pass= ((EditText) findViewById(R.id.password)).getText().toString();

         if(ID.equals(username) && Password.equals(Pass))
         {
             return  true;
         }
        return false;
    }
}
