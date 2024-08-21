package fr.isen.perigot.educscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

import fr.isen.perigot.educscan.databinding.ActivityMainBinding;

//pour qrCode (statique) :
//pour QRcode dynamique basique :
//pour la méthode de focntion de hash :


public class MainActivity extends AppCompatActivity {

    private static final int MENU_LANGUAGE_FR = 1;
    private static final int MENU_LANGUAGE_EN = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fr.isen.perigot.educscan.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration;

        Intent intent =getIntent();
        boolean isStudent = intent.getBooleanExtra("isStudent", false);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_parametres)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


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

    @SuppressLint("UnsafeIntentLaunch")
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