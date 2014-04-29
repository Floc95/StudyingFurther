package com.esgi.studyingfurther.vl;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.esgi.studyingfurther.R;

public class Affichageitem extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affichageitem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.affichageitem, menu);
		return true;
	}

    public  void  Modification()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Button Modifier");
        adb.setMessage("Vous avez appuiez sur le button Modifier");
        adb.setPositiveButton("Ok", null);
        adb.show();

    }

    public void Pub()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Button Publier");
        adb.setMessage("Vous avez appuiez sur le button Publier");
        adb.setPositiveButton("Ok", null);
        adb.show();
    }
    public void Write()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Button Write");
        adb.setMessage("Vous avez appuiez sur le button Write");
        adb.setPositiveButton("Ok", null);
        adb.show();
    }

}
