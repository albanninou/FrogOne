package fr.albanninou.frogone.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.albanninou.frogone.R;

public class MainActivity extends AppCompatActivity {
    public static Bitmap loadingbmp;
    public static int width = 0, height = 0;
    public static LinearLayout menu;
    public static SharedPreferences preferences;
    public static int lvl = 0;
    public static Context context;
    public static Activity activity;
    public static MainActivity ac;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.news)
                    .setMessage(R.string.newVersion)
                    .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })
                    .setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };
    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                int version = 0;
                URL url = new URL("http://frogone.esy.es/version.xml");
                HttpURLConnection request1 = (HttpURLConnection) url.openConnection();
                request1.setRequestMethod("GET");
                request1.connect();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(request1.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    version = Integer.parseInt(line);
                }

                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                int thiversion = pInfo.versionCode;
                if (thiversion < version) {
                    mHandler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    private GLSurfaceView mSurfaceView;
    private GLSurfaceView mGLView;

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
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        width = d.getWidth();
        height = d.getHeight();
        thread.start();
    }

}
