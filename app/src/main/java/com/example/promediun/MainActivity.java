package com.example.promediun;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.promediun.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swDarkMode;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String THEME_KEY = "Theme";

//    public ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.promediun.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_horario, R.id.nav_boleta_notas, R.id.nav_ajustes,R.id.nav_notas)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        MainActivity ma = MainActivity.this;
        swDarkMode = findViewById(R.id.modedaynish);
        sp = ma.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        editor = sp.edit();
        boolean isDarkMode = sp.getBoolean(THEME_KEY, false);
        swDarkMode.setChecked(isDarkMode);

        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(THEME_KEY, isChecked);
            editor.apply();
            setDayNight(isChecked);
        });
        setDayNight(isDarkMode);



        // Inflar el diseño utilizando ViewBinding
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

    }
    private void setDayNight(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            swDarkMode.setThumbDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icono_luna));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            swDarkMode.setThumbDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icono_sol));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        boolean isDarkMode = sp.getBoolean(THEME_KEY, false);
        setDayNight(isDarkMode);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}