package fr.isen.perigot.educscan;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import fr.isen.perigot.educscan.databinding.ActivityProfBinding;

public class ProfActivity extends AppCompatActivity {

    private static final int MENU_LANGUAGE_FR = 1;
    private static final int MENU_LANGUAGE_EN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fr.isen.perigot.educscan.databinding.ActivityProfBinding binding = ActivityProfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_prof);
        AppBarConfiguration appBarConfiguration;

        Intent intent = getIntent();

        BottomNavigationView navViewProf = findViewById(R.id.nav_view_prof);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_camembert, R.id.navigation_listes, R.id.navigation_parametres)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewProf, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gonflez le menu de traduction dans l'action bar
        getMenuInflater().inflate(R.menu.menu_trad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_LANGUAGE_FR:
                setLocale("fr");
                return true;
            case MENU_LANGUAGE_EN:
                setLocale("en");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setLocale(String lang) {
        // Créer un objet de type Locale avec la langue spécifiée
        Locale locale = new Locale(lang);
        // Définir la locale pour l'application
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Redémarrer l'activité pour appliquer les changements de langue
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}