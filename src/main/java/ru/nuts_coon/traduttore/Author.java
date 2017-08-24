package ru.nuts_coon.traduttore;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

import static ru.nuts_coon.traduttore.MainActivity.SHARED_PREFERENCE;
import static ru.nuts_coon.traduttore.MainActivity.THEME_ARRAY;
import static ru.nuts_coon.traduttore.MainActivity.Theme;


/**
 * Created by Michael on 21.08.2017.
 */

public class Author extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setTheme(Theme(loadColorSetting()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.author);
        setTitle("Author");
    }

    public String loadColorSetting(){

        SharedPreferences sPref = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
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
}
