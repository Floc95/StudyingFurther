package com.esgi.studyingfurther.vl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.esgi.studyingfurther.R;

import java.util.Date;

/**
 * Created by server-pc on 13/03/14.
 */
public class Inscription extends Activity {



    private String Nom;
    private String Prenom;
    private Date DateDeNess;
    private String Email;
    private  String Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
      //  TextView t=(TextView)findViewById(R.id.message);
      //  t.setText("OUI");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public String getNom() {return Nom;}

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public Date getDateDeNess() {
        return DateDeNess;
    }

    public void setDateDeNess(Date dateDeNess) {
        DateDeNess = dateDeNess;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
