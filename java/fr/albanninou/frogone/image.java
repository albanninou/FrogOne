package fr.albanninou.frogone;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jeune on 25/01/2017.
 */

public class Image {
    Grille grille;
    Canvas canvas;
    Bitmap[] win;

    public Image(Grille grille) {
        this.grille = grille;
        win = new Bitmap[(int) (grille.getLargeur() / 15 + 1)];
        for (int i = 0; i < grille.getLargeur() / 15; i++) {
            win[i] = Bitmap.createScaledBitmap(grille.getWinbmp(), 15 * i + 1, 30 * i + 1, true);
        }
    }
    public Bitmap getWin(int image) {
        return win[image / 15];
    }
}
