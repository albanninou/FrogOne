package fr.albanninou.frogone.Lvl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.albanninou.frogone.GLSurface;
import fr.albanninou.frogone.Main.MainActivity;
import fr.albanninou.frogone.R;
import fr.albanninou.frogone.Render;

/**
 * Created by eleve on 31/01/2017.
 */

public class LvlActivity extends Activity implements View.OnTouchListener {

    public static int lvl;
    public static Lvl Lvl;
    public static int lvlmax = 17;
    public static Resources res;
    public static Activity act;
    public static RelativeLayout layout;
    public static TextView textlvl;
    public static TextView win;
    public static TextView coups;
    public static Button b1, b2;
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            LvlActivity.layout.addView(LvlActivity.textlvl);
            LvlActivity.layout.addView(LvlActivity.coups);
            LvlActivity.layout.addView(LvlActivity.b1);
            LvlActivity.layout.addView(LvlActivity.b2);
        }
    };
    private GLSurfaceView glSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.activity_lvl);
        //Toast.makeText(this, "Vous avez recommncé" , Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        lvl = intent.getIntExtra("lvl", 0);
        textlvl = (TextView) findViewById(R.id.textlvl);
        textlvl.setText("Lvl " + lvl);
        layout = (RelativeLayout) findViewById(R.id.activity_lvl);
        res = getResources();
        Lvl = new Lvl(this, this, lvl);
        win = (TextView) findViewById(R.id.win);
        coups = (TextView) findViewById(R.id.coup);
        coups.setText("Coup(s) : 0");
        layout.removeView(win);
        layout.removeView(coups);
        layout.removeView(textlvl);


        //Create an Instance with this Activity
        glSurface = new GLSurface(this);
        //Set our own Renderer
        glSurface.setRenderer(new Render(Lvl.getGrille()));


        b1 = (Button) findViewById(R.id.recommencez);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Lvl.getGrille().getWinBoolean()) {
                    coups.setText("Coup(s) : 0");
                    layout.removeView(coups);
                    layout.addView(coups);
                    Lvl.restart();
                }
            }
        });
        b2 = (Button) findViewById(R.id.annuler);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!Lvl.getGrille().getWinBoolean()) {
                    Lvl.undow();
                    coups.setText("Coup(s) : " + Lvl.getGrille().getCoup());
                    layout.removeView(coups);
                    layout.addView(coups);
                }
            }
        });
        layout.removeView(b1);
        layout.removeView(b2);
        layout.addView(glSurface);
        layout.addView(Lvl);
    }

    @Override
    public void onBackPressed() {
        TextView mid = (TextView) findViewById(R.id.mid);
        PopupMenu popup = new PopupMenu(this, mid);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.quitter) {
                    MainActivity.setButton();
                    finish();
                }

                return true;
            }
        });
        popup.show();//showing popup menu
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Lvl.getGrille().getWinBoolean() != true) {
                    float x = event.getX();
                    float y = event.getY();
                    int X = (int) ((x) / ((Lvl.getGrille().gettCase() * ((float) Lvl.getGrille().getLc()[1]) + Lvl.getGrille().gettLigne() * ((float) (Lvl.getGrille().getLc()[1] + 1))) / ((float) Lvl.getGrille().getLc()[1])));
                    int Y = (int) ((y - 40) / ((Lvl.getGrille().gettCase() * ((float) Lvl.getGrille().getLc()[0]) + Lvl.getGrille().gettLigne() * ((float) (Lvl.getGrille().getLc()[0] + 1))) / ((float) Lvl.getGrille().getLc()[0])));
                    if (X < 0) {
                        X = 0;
                    }
                    if (Y < 0) {
                        Y = 0;
                    }
                    if (X >= Lvl.getGrille().getLc()[1] || Y >= Lvl.getGrille().getLc()[0]) {
                        return true;
                    }
                    Lvl.getGrille().setSelect(Y, X);
                    //Log.w("myApp",""+Lvl.getGrille().getGrille()[Lvl.getGrille().getCoup()]);
                    if (Lvl.getGrille().getWin()) {
                        Lvl.getGrille().setWinBoolean(true);
                        layout.addView(win);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    public void Win() {
        finish();
        Intent intent = new Intent(LvlActivity.this, LvlActivity.class);
        if (lvl != lvlmax) {
            intent.putExtra("lvl", lvl + 1);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            if (preferences.getInt("lvl", 1) == lvl) {
                editor.putInt("lvl", lvl + 1);
                editor.commit();
            }
        } else {
            intent.putExtra("lvl", lvl);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("lvl", lvlmax + 1);
            editor.commit();
        }
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
