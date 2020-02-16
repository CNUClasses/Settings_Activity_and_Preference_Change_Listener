package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //need these to track changes
    private SharedPreferences myPreference;
    private SharedPreferences.OnSharedPreferenceChangeListener listener = null;
    private boolean enablePreferenceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPreferences();
            }
        });
    }

    private void doPreferences() {
        Intent myintent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(myintent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            doPreferences();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void togglePreferenceChangeListener(View view) {

        // lets get a handle to default shared prefs
        if (myPreference == null)
            myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // this is the bit that listens for any preference changes to defaultsharedpreferences
        // (the prefs that the pref activity accesses)
        //you can also implements OnSharedPreferenceChangeListener for the mainactivity and then
        //register to have have the mainactivity listen for changes like this
        //myPreference.registerOnSharedPreferenceChangeListener(this);
        //and forgo whats below
        if (listener == null) {
            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Toast.makeText(MainActivity.this, "Handle change of Key=" + key, Toast.LENGTH_SHORT).show();
                }
            };
        }

        //toggle listener
        enablePreferenceListener = !enablePreferenceListener;
        if (enablePreferenceListener)
            // register the listener (turn it on)
            myPreference.registerOnSharedPreferenceChangeListener(listener);
        else
            //or unregister it (turn it off)
            myPreference.unregisterOnSharedPreferenceChangeListener(listener);

    }
}
