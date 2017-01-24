package fr.albanninou.frogone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TutoActivity extends Activity {
    public static Resources res;
    public TextView commentaire;
    public RelativeLayout layout;
    public Tuto tuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        layout = (RelativeLayout) findViewById(R.id.activity_tuto);
        res = getResources();
        tuto = new Tuto(this,this);
        commentaire = (TextView) findViewById(R.id.commentaire);
        commentaire.setText(R.string.step1);
        layout.removeView(commentaire);
        layout.addView(tuto);
        layout.addView(commentaire);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();
                int X = (int) ((x - 40) / ((tuto.getGrille().gettCase() * ((float) tuto.getGrille().getLc()[1]) + tuto.getGrille().gettLigne() * ((float) (tuto.getGrille().getLc()[1] + 1))) / ((float) tuto.getGrille().getLc()[1])));
                int Y = (int) ((y - 80) / ((tuto.getGrille().gettCase() * ((float) tuto.getGrille().getLc()[0]) + tuto.getGrille().gettLigne() * ((float) (tuto.getGrille().getLc()[0] + 1))) / ((float) tuto.getGrille().getLc()[0])));
                if (X < 0) {
                    X = 0;
                }
                if (Y < 0) {
                    Y = 0;
                }
                switch (tuto.getStep()) {
                    case 0:
                        tuto.setStep(tuto.getStep() + 1);
                        commentaire.setText(R.string.step2);
                        layout.removeView(commentaire);
                        layout.addView(commentaire);
                        break;
                    case 1:
                        if (X >= tuto.getGrille().getLc()[1] || Y >= tuto.getGrille().getLc()[0]) {
                            return true;
                        } else {
                            tuto.getGrille().setSelect(Y, X);
                        }
                        tuto.setStep(tuto.getStep() + 1);
                        commentaire.setText(R.string.step3);
                        layout.removeView(commentaire);
                        layout.addView(commentaire);
                        break;
                    case 2:
                        if (X >= tuto.getGrille().getLc()[1] || Y >= tuto.getGrille().getLc()[0]) {
                            return true;
                        } else {
                            if (tuto.getGrille().getJeton()[Y][X].getType() != 'V') {
                                tuto.getGrille().setSelect(Y, X);
                            } else {
                                int cassez[] = {Y, X};
                                tuto.getGrille().setSelect(Y, X);
                                tuto.setStep(tuto.getStep() + 1);
                                commentaire.setText(R.string.step4);
                                layout.removeView(commentaire);
                                layout.addView(commentaire);
                            }
                        }
                        break;
                    case 3:
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = preferences.edit();
                        if (preferences.getInt("lvl", 0) == 0) {
                            editor.putInt("lvl", 1);
                            editor.apply();
                            MainActivity.setButton();
                        }
                        finish();
                        break;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }
}
