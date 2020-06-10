package com.afforteam.affordex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer; //App drawer (= Les 3 traits en haut à droite de l'écran) permettant d'accéder au menu latéral en cliquant dessus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assignation de l'affordex_toolbar créée dans activity_main.xml
        Toolbar toolbar = findViewById(R.id.affordex_toolbar);
        setSupportActionBar(toolbar);

        //Assignation du drawer (= Les 3 traits en haut à droite de l'écran) permettant d'accéder au menu latéral en cliquant dessus
        drawer = findViewById(R.id.drawer_affordex);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAffordex()).commit();
            navigationView.setCheckedItem(R.id.nav_affordex);
        }

        setContentView(R.layout.activity_main);

        //Récupération des informations de la BD
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://78.204.206.61:3306/ProjetTuto_DB?autoReconnect=true&useSSL=false", "Richard", "Raymond");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from POKEMON");

            int i = 0;
            // Tant qu'il y a des résultats
            while (rs.next()) {

                i++;

                setContentView(R.layout.activity_main);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
                //Création d'une "Tuile" pour chaque pokémon
                RelativeLayout pokemon = new RelativeLayout(this);
                linearLayout.addView(pokemon);

                pokemon.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                pokemon.getLayoutParams().height = 70;
                pokemon.setId(i);
                //Création des différents attributs de chaque pokémon
                TextView nomP = new TextView(this);
                nomP.setText(rs.getString(2));// Réxupération de la colonne 2 qui contient les noms de pokémon
                pokemon.addView(nomP);

                ImageView imageP = new ImageView(this);
                //imageP.setImageResource("cheminImage" + rs.getString(2)); A implémenter avec le stockage des images dans la mémoire du téléphone
                pokemon.addView(imageP);

                TextView typeP = new TextView(this);
                typeP.setText(rs.getString(4));
                pokemon.addView(typeP);

                TextView numP = new TextView(this);
                numP.setText(rs.getString(1));
                pokemon.addView(numP);

                TextView genP = new TextView(this);
                genP.setText(rs.getString(13));
                pokemon.addView(genP);
            }

            con.close();
        }

        catch(Exception e){

            e.printStackTrace();
        }

    }

    //Cette procédure permet d'éviter de quitter l'application lorsque le menu latéral est ouvert
    //Si le menu latéral est ouvert, cette fonction le fermera si l'on appuie sur la touche retour
    //S'il n'est pas ouvert, l'application se fermera
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Cette fonction permet d'afficher une notification Toast lorsque l'utilisateur clique sur l'un des éléments du menu latéral
    //Le premier case du switch fait référence à l'élément Affordex et affichera le layout fragment_affordex.xml
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_affordex:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAffordex()).commit();
                break;
            case R.id.nav_FType:
                Toast.makeText(this, "Triés par type", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_FAlpha:
                Toast.makeText(this, "Triés par ordre alphabétique", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_FPuiss_Croiss:
                Toast.makeText(this, "Triés par puissance croissante", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_FPuiss_Decroiss:
                Toast.makeText(this, "Triés par puissance décroissante", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}