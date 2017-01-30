package fr.albanninou.frogone.Main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import fr.albanninou.frogone.R;

public class MainActivity extends AppCompatActivity {
    public static Bitmap loadingbmp;
    static LinearLayout menu;
    static SharedPreferences preferences;
    static int lvl = 0;
    static Context context;
    static Activity activity;
    static MainActivity ac;

    public static void setButton() {
        lvl = preferences.getInt("lvl", 0);
        menu.removeAllViews();
        for (int i = 0; i <= ConfigLvl.lvlmax; i++) {
            menu.addView(new BouttonLvl(context, lvl, i, activity)); //coucou
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = (LinearLayout) findViewById(R.id.menu);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        context = this;
        activity = this;
        ac = this;
        setButton();
        loadingbmp = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
    }

}
