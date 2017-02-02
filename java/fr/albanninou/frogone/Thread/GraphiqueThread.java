package fr.albanninou.frogone.Thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import fr.albanninou.frogone.Grille;

/**
 * Created by Jeune on 23/01/2017.
 */

public class GraphiqueThread extends Thread {
    boolean execute = false;
    SurfaceHolder holder;
    Grille grille;
    Canvas canvas;
    boolean test = true;
    int image = 0;

    public GraphiqueThread(Grille grille, SurfaceHolder holder) {
        super();
        this.holder = holder;
        this.grille = grille;
    }

    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long ticksPS = 1000 / 30;
        long startTime;
        long sleepTime;
        execute = true;
        while (execute) {
            startTime = System.currentTimeMillis();
            if (!grille.getFinish()) {
                try {
                    canvas = holder.lockCanvas(null);
                    synchronized (holder) {
                        if (canvas != null) {
                            float taille = canvas.getWidth();
                            float tCase, tLigne;
                            grille.setHauteur(canvas.getHeight());
                            grille.setLargeur(canvas.getWidth());
                            if (grille.getLc()[0] < grille.getLc()[1]) {
                                tCase = canvas.getWidth() / (grille.getLc()[1] + 1);
                                grille.settCase(tCase);
                                tLigne = tCase / (grille.getLc()[1] + 1);
                                grille.settLigne(tLigne);
                            } else {
                                tCase = canvas.getWidth() / (grille.getLc()[0] + 1);
                                grille.settCase(tCase);
                                tLigne = tCase / (grille.getLc()[0] + 1);
                                grille.settLigne(tLigne);
                            }
                            if (grille.getWinBoolean() == false) {
                                grille.drawGrille(canvas);
                            } else {
                                grille.drawWin(canvas);
                            }
                        }
                    }
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                } else {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                execute = false;
                return;
            }
        }


    }

    public boolean getExecute() {
        return this.execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

}
