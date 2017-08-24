package ru.nuts_coon.traduttore;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import java.util.Random;

import static ru.nuts_coon.traduttore.MainActivity.SHARED_PREFERENCE;
import static ru.nuts_coon.traduttore.MainActivity.THEME_ARRAY;
import static ru.nuts_coon.traduttore.MainActivity.Theme;


/**
 * Created by Michael on 18.08.2017.
 * Активити основных настроек
 */

public class SettingActivity extends PreferenceActivity {

    AppCompatDelegate mDelegate;
    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(Theme(loadColorSetting()));

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Уничтожение текущей Активити и запуск предыдущей чтобы вазвать метод onCreate и установить
        //выбранный цвет
        finish();
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
    }

    //Загрузка сохранённого цвета. По умолчанию фиолетовый.
    public String loadColorSetting(){

        sPref = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);

        if(sPref.contains("color")){
            String color = sPref.getString("color", "MyThemePurple");

            if (!color.equalsIgnoreCase("mix")){
                return color;
            }else {
                Random random = new Random(System.currentTimeMillis());
                int numberTheme = random.nextInt(THEME_ARRAY.length + 1);
                return THEME_ARRAY[numberTheme];
            }
        }else {
            return "MyThemePurple";
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
