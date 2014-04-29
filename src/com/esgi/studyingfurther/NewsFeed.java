package com.esgi.studyingfurther;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
public class NewsFeed extends Activity {

	private ListView maListViewPerso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		
		 //R閏up閞ation de la listview cr殚e dans le fichier main.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
 
        //Cr閍tion de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On d閏lare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Cr閍tion d'une HashMap pour ins閞er les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on ins鑢e un 閘閙ent titre que l'on r閏up閞era dans le textView titre cr殚 dans le fichier affichageitem.xml
        map.put("titre", "Word");
        //on ins鑢e un 閘閙ent description que l'on r閏up閞era dans le textView description cr殚 dans le fichier affichageitem.xml
        map.put("description", "Editeur de texte");
        //on ins鑢e la r閒閞ence � l'image (convertit en String car normalement c'est un int) que l'on r閏up閞era dans l'imageView cr殚 dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.android));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des donn閑s diff閞entes pour former les items de notre ListView
 
        map = new HashMap<String, String>();
        map.put("titre", "Excel");
        map.put("description", "Tableur");
        map.put("img", String.valueOf(R.drawable.android));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Power Point");
        map.put("description", "Logiciel de pr閟entation");
        map.put("img", String.valueOf(R.drawable.android));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Outlook");
        map.put("description", "Client de courrier 閘ectronique");
        map.put("img", String.valueOf(R.drawable.android));
        listItem.add(map);
 
        //Cr閍tion d'un SimpleAdapter qui se chargera de mettre les items pr閟ent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.activity_affichageitem,new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
 
        //On attribut � notre listView l'adapter que l'on vient de cr閑r
        maListViewPerso.setAdapter(mSchedule);
 
        //Enfin on met un 閏outeur d'関鑞ement sur notre listView
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			@Override
        	@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on r閏up鑢e la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
        		//on cr閑r une boite de dialogue
        		AlertDialog.Builder adb = new AlertDialog.Builder(NewsFeed.this);
        		//on attribut un titre � notre boite de dialogue
        		adb.setTitle("S閘ection Item");
        		//on ins鑢e un message � notre boite de dialogue, et ici on affiche le titre de l'item cliqu�
        		adb.setMessage("Votre choix : "+map.get("titre"));
        		//on indique que l'on veut le bouton ok � notre boite de dialogue
        		adb.setPositiveButton("Ok", null);
        		//on affiche la boite de dialogue
        		adb.show();
        		Intent intent = new Intent(NewsFeed.this, AffichageItem.class);
        		startActivity(intent);
        	}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_feed, menu);
		return true;
	}

}
