package com.esgi.studyingfurther;

import org.json.JSONException;

import com.esgi.studyingfurther.dal.Repository;
import com.esgi.studyingfurther.vm.MainViewModel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable.Factory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static java.lang.String.*;

public class Identification extends Activity {

	private MainViewModel ManagerUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
        ManagerUser=new MainViewModel(new com.esgi.studyingfurther.bl.Factory());
    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identification, menu);
		return true;
	}



	public void seConnecter(View v) throws JSONException{
		  String username= ((EditText) findViewById(R.id.username)).getText().toString();
	      String Passwordd= ((EditText) findViewById(R.id.password)).getText().toString();
	  //    Repository r=new Repository();
	 // Log.v("webservice",    r.convertJeson("http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99"));
	//      r.convertJeson("http://www.your-groups.com/API/Ident?key=7e2a3a18cd00ca322f60c28393c43264&username=Floc&password=5f4dcc3b5aa765d61d8327deb882cf99");

		if(this.authentification("simo", "simo"))
        {
		Intent intent = new Intent(this, NewsFeed.class);
		startActivity(intent);
        }


	}

    public boolean authentification(String ID, String Password)
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
