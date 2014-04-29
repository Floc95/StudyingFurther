package com.esgi.studyingfurther;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AffichageItem extends Activity {

    TextView titre;
    TextView description;
    TextView owner;
    TextView time;
    Button button, download;
    SimpleAdapter listItemAdapter;
    ArrayList<HashMap<String, Object>> listItem;
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        myList = (ListView)findViewById(R.id.listviewperso);
        button = (Button)findViewById(R.id.send);
        button.setEnabled(false);
        titre = (TextView)this.findViewById(R.id.titre);
        description = (TextView)this.findViewById(R.id.description);

        listItem = new ArrayList<HashMap<String, Object>>();


        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("title", "Comment "+ listItem.size());

        listItem.add(map);
        addItem("titile1","content1");
        addItem("owner1","content2");
        addItem("owner2","content3");
        addItem("owner3","content3");
        addItem("owner4","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");
        addItem("owner5","content3");

        HashMap<String, Object> tmp = new HashMap<String, Object>();
        tmp.put("title", "Comment  "+ listItem.size());
        listItem.set(0, tmp);

        listItemAdapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.activity_affichageitem, new String[]{"image", "title", "text"}, new int[]{R.id.img, R.id.titre, R.id.description});
        myList.setAdapter(listItemAdapter);


        myList.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AffichageItem.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                button.setEnabled(false);
                return false;
            }
        });


        final EditText coment = (EditText)findViewById(R.id.editText1);
        coment.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button.setEnabled(true);
                return false;
            }
        });


        coment.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(coment, 0);
                }

            }
        });


        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!coment.getText().toString().trim().equals(""))
                {
                    addItem("new",coment.getText().toString());
                    coment.setText("");
                    listItemAdapter.notifyDataSetChanged();
                }
                else {
                    new AlertDialog.Builder(AffichageItem.this)
                            .setTitle("Warning")
                            .setMessage("Comment can't be empty")
                            .setPositiveButton("OK", null)
                            .show();
                }


            }
        });

        download = (Button)findViewById(R.id.download);
        download.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffichageItem.this, Parameter.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addItem(String title, String text)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("image", R.drawable.android);
        map.put("title", title);
        map.put("text", text);
        listItem.add(map);
//        listItemAdapter.notifyDataSetChanged();
    }


}