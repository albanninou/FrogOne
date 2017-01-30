package fr.albanninou.frogone.Thread;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import fr.albanninou.frogone.Grille;
import fr.albanninou.frogone.Main.MainActivity;

/**
 * Created by Jeune on 29/01/2017.
 */

public class LoadingThread extends Thread {
    boolean execute = false;
    SurfaceHolder holder;
    Grille grille;
    Canvas canvas;
    boolean test = true;
    Bitmap loading;
    int image = 0;
    int conteur = 0;

    public LoadingThread(SurfaceHolder holder) {
        super();
        this.holder = holder;
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
            try {
                canvas = holder.lockCanvas(null);
                synchronized (holder) {
                    if (canvas != null) {
                        if (loading == null) {
                            if (canvas != null) {
                                loading = Bitmap.createScaledBitmap(MainActivity.loadingbmp, canvas.getWidth(), canvas.getHeight(), false);
                            }
                        }
                        canvas.drawBitmap(loading, 0, 0, null);
                    }
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
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

