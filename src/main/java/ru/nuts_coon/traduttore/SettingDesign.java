package ru.nuts_coon.traduttore;



import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.nuts_coon.traduttore.MainActivity.SHARED_PREFERENCE;
import static ru.nuts_coon.traduttore.MainActivity.THEME_ARRAY;
import static ru.nuts_coon.traduttore.MainActivity.Theme;

/**
 * Created by Michael on 20.08.2017.
 * Активити выбора цвета
 */

public class SettingDesign extends ListActivity {

    int[] COLORS;
    String[] COLORS_LIST;
    AppCompatDelegate mDelegate;

    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(Theme(loadColorSetting()));

        super.onCreate(savedInstanceState);

        COLORS_LIST = getResources().getStringArray(R.array.colors);

        COLORS = new int[11];
        COLORS[0] = R.color.primaryRed;
        COLORS[1] = R.color.primaryOrange;
        COLORS[2] = R.color.primaryAmber;
        COLORS[3] = R.color.primaryTeal;
        COLORS[4] = R.color.primaryGreen;
        COLORS[5] = R.color.primaryBlue;
        COLORS[6] = R.color.primaryIndigo;
        COLORS[7] = R.color.primaryPurple;
        COLORS[8] = R.color.primaryPink;
        COLORS[9] = R.color.primaryBlueGrey;
        COLORS[10] = R.drawable.mix;

        List<DesignColor> dColor = new ArrayList<>();
        for (int i = 0; i < COLORS_LIST.length; i++){
            dColor.add(new DesignColor(COLORS_LIST[i], COLORS[i]));
        }

        DesignAdapter adapter = new DesignAdapter(getApplicationContext(), R.layout.my_list_item, dColor);
        setListAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.design_title));
        actionBar.setSubtitle(getResources().getString(R.string.design_summary));


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

    //Сохранение выбранна цвета
    public void saveColorSetting(String color){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("color", color);
        ed.apply();
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        saveColorSetting(THEME_ARRAY[position]);

        //Уничтожение текущей Активити и запуск предыдущей чтобы вазвать метод onCreate и установить
        //выбранный цвет
        finish();
        startActivity(new Intent(SettingDesign.this, SettingActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        startActivity(new Intent(SettingDesign.this, SettingActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(SettingDesign.this, SettingActivity.class));
    }
}
