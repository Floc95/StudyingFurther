package com.esgi.studyingfurther;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GererClass extends Activity {

	Button add, delete;
	ListView myList;
	SimpleAdapter listItemAdapter;
	ArrayList<HashMap<String, String>> listItem;
	EditText modify, addEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerer_class);

		add = (Button) findViewById(R.id.add);
		delete = (Button) findViewById(R.id.delete);
		myList = (ListView) findViewById(R.id.listClass);

		listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("class", "class1");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("class", "class2");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("class", "class3");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("class", "class4");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("class", "class5");
		listItem.add(map);

		listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem,
				R.layout.listparameter, new String[] { "class" },
				new int[] { R.id.textPara });
		myList.setAdapter(listItemAdapter);

		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				modify = new EditText(GererClass.this);
				new AlertDialog.Builder(GererClass.this)
						.setTitle("Modifier le nom")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(modify)
						.setPositiveButton("OK", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (modify.getText().toString().trim()
										.equals("")) {
									new AlertDialog.Builder(GererClass.this)
											.setTitle("Warning")
											.setMessage("Can not be empty")
											.setPositiveButton("OK", null)
											.show();
								} else {
									HashMap<String, String> tmp = new HashMap<String, String>();
									tmp.put("class", modify.getText()
											.toString());
									listItem.set(arg2, tmp);
									listItemAdapter.notifyDataSetChanged();
								}
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										modify.getWindowToken(), 0);
							}
						}).setNegativeButton("Cancel", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										modify.getWindowToken(), 0);
							}
						}).show();
				modify.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

			}
		});

		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				addEditText = new EditText(GererClass.this);
				new AlertDialog.Builder(GererClass.this)
						.setTitle("ajouter le nom")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(addEditText)
						.setPositiveButton("OK", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (addEditText.getText().toString().trim()
										.equals("")) {
									new AlertDialog.Builder(GererClass.this)
											.setTitle("Warning")
											.setMessage("Can not be empty")
											.setPositiveButton("OK", null)
											.show();
								} else {
									HashMap<String, String> tmp = new HashMap<String, String>();
									tmp.put("class", addEditText.getText()
											.toString());
									listItem.add(tmp);
									listItemAdapter.notifyDataSetChanged();
									delete.setEnabled(true);
								}
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										addEditText.getWindowToken(), 0);
							}
						}).setNegativeButton("Cancel", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										addEditText.getWindowToken(), 0);
							}
						}).show();
				addEditText.requestFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			String[] it;
			boolean[] bolIt;

			@Override
			public void onClick(View v) {
				it = new String[listItem.size()];
				bolIt = new boolean[listItem.size()];
				for (int i = 0; i < it.length; i++) {
					it[i] = listItem.get(i).get("class");
					bolIt[i] = false;

				}

				new AlertDialog.Builder(GererClass.this)
						.setTitle("Delet Class")
						.setMultiChoiceItems(it, bolIt,
								new OnMultiChoiceClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1, boolean arg2) {
										bolIt[arg1] = arg2;
									}
								})
						.setPositiveButton("OK", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								for (int i = bolIt.length - 1; i >= 0; i--) {
									if (bolIt[i]) {
										listItem.remove(i);
									}
								}
								listItemAdapter.notifyDataSetChanged();
								if (listItem.size() == 0) {
									delete.setEnabled(false);
								}
							}
						}).setNegativeButton("Cancel", null).show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gerer_class, menu);
		return true;
	}

}
