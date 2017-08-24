package ru.nuts_coon.traduttore;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    final String API_KEY = "trnsl.1.1.20170712T151942Z.db9262f613260602.4ec133e357fa30948db5d285b3dd576169e457d8";

    final static String SHARED_PREFERENCE = "colorSetting";

    static String[] THEME_ARRAY = { "MyThemeRed", "MyThemeOrange", "MyThemeAmber" ,
            "MyThemeTeal", "MyThemeGreen", "MyThemeBlue", "MyThemeIndigo",
            "MyThemePurple", "MyThemePink", "MyThemeBlueGrey", "mix"};

    String[] langList, langCode;
    Map<String, List<String>> dict;

    int SpinnerSelected1, SpinnerSelected2;

    EditText editText1, editText2;
    Spinner spinner1, spinner2;
    Button btnDirect, btnReverse;
    TextView textView;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sPref = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);

        setTheme(Theme(loadColorSetting()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        langList = getResources().getStringArray(R.array.language);
        langCode = getResources().getStringArray(R.array.langCode);


        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.tv);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        //Наполняем спиннеры
        inflateSpinner();

        btnDirect = (Button) findViewById(R.id.btnDirect);
        btnReverse = (Button) findViewById(R.id.btnReverse);

        //Устанавливаем текст на кнопках
        btnSetText();

        btnDirect.setOnClickListener(this);
        btnReverse.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        String lang;

        if (isOnline(getApplicationContext())){
            switch (view.getId()){
                //Перевод слева направо
                case R.id.btnDirect:
                    String textDirect = editText1.getText().toString();
                    lang = langCode[SpinnerSelected1] + "-" + langCode[SpinnerSelected2];

                    if (!textDirect.isEmpty()){
                        editText2.setText("...");
                        getTranslate(textDirect, lang, editText2);
                    }
                    break;
                //Перевод справа налево
                case R.id.btnReverse:
                    String textReverse = editText2.getText().toString();
                    lang  = langCode[SpinnerSelected2] + "-" + langCode[SpinnerSelected1];

                    if (!textReverse.isEmpty()){
                        editText1.setText("...");
                        getTranslate(textReverse, lang, editText1);
                    }
                    break;
            }
        }else {
            Toast.makeText(getApplicationContext(),
                    getResources().getText(R.string.offline).toString(),
                    Toast.LENGTH_LONG).show();
        }


    }

    //Получение перевода
    public void getTranslate(String text, String lang, final EditText et){

        //Конечно глупо переводить с русского на русский.
        //Но использовать для этого API ещё глупее.
        if (SpinnerSelected1 != SpinnerSelected2) {
            Api api = Api.retrofit.create(Api.class);
            Call<Answer> call = api.getAnswer(API_KEY, text, lang);

            call.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    List<String> resp = response.body().getText();
                    for (String translate : resp){
                        et.setText(translate);
                    }
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t + "", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            et.setText(text);
        }

    }


    // Наполнение спиннеров
    public void inflateSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, langList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        //Установка значений по умолчанию
        spinner1.setSelection(Arrays.asList(langList).indexOf("Английский"));
        spinner2.setSelection(Arrays.asList(langList).indexOf("Русский"));

        SpinnerSelected1 = spinner1.getSelectedItemPosition();
        SpinnerSelected2 = spinner2.getSelectedItemPosition();
    }

    //Текст для кнопок
    public void btnSetText(){


        btnDirect.setText(langCode[SpinnerSelected1] + "-" + langCode[SpinnerSelected2] + "->");

        btnReverse.setText("<-" + langCode[SpinnerSelected2] + "-" + langCode[SpinnerSelected1]);
    }

    //Выбор темы
    public static int Theme(String nameTheme){
        int themeID = R.style.MyThemeOrange;

        switch (nameTheme){
            case "MyThemePurple":
                themeID = R.style.MyThemePurple;
                break;
            case "MyThemeRed":
                themeID =  R.style.MyThemeRed;
                break;
            case "MyThemeOrange":
                themeID =  R.style.MyThemeOrange;
                break;
            case "MyThemeBlue":
                themeID =  R.style.MyThemeBlue;
                break;
            case "MyThemeAmber":
                themeID =  R.style.MyThemeAmber;
                break;
            case "MyThemeGreen":
                themeID =  R.style.MyThemeGreen;
                break;
            case "MyThemeBlueGrey":
                themeID =  R.style.MyThemeBlueGrey;
                break;
            case "MyThemePink":
                themeID =  R.style.MyThemePink;
                break;
            case "MyThemeTeal":
                themeID = R.style.MyThemeTeal;
                break;
            case "MyThemeIndigo":
                themeID =  R.style.MyThemeIndigo;
                break;
        }
        return themeID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.action_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                finish();
                startActivity(intent);
        }

        return true;
    }

    //Загрузка сохранённого цвета. По умолчанию фиолетовый.
    public String loadColorSetting(){

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

    //Проверка наличия интернета
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
        SpinnerSelected1 = spinner1.getSelectedItemPosition();
        SpinnerSelected2 = spinner2.getSelectedItemPosition();


        View v = adapterView.getSelectedView();

        if (v != null){
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(langList[i].equalsIgnoreCase("Эсперанто")){
                        startActivity(new Intent(MainActivity.this, Author.class));
                    }
                    return true;
                }
            });
        }

        btnSetText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
