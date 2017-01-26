package fr.albanninou.frogone;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jeune on 23/01/2017.
 */

public class Tuto extends SurfaceView implements SurfaceHolder.Callback{
    private Grille grille;
    private int step = 0;
    private GraphiqueThread thread;
    private Activity activity;

    public Tuto(Context context,Activity activity) {
        super(context);
        getHolder().addCallback(this);
        this.grille = new Grille(1,activity);
        this.activity = activity;
    }


    public Grille getGrille(){
        return this.grille;
    }

    public int getStep(){
        return step;
    }
    public void setStep(int step){
        this.step = step;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GraphiqueThread(grille, holder);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setExecute(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException ie) {

                //Try again and again and again
            }
            break;
        }


    }
}
