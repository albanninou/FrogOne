package fr.albanninou.frogone;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by Jeune on 25/01/2017.
 */

public class Image {
    Grille grille;
    Canvas canvas;
    Bitmap[][] rotate;
    Bitmap[][] retrecir;

    public Image(Grille grille) {
        this.grille = grille;
        retrecir = new Bitmap[10][(int) grille.gettCase() / 2];
        rotate = new Bitmap[10][(int) grille.gettCase() / 2];
        for (int i = 0; i < 5; i++) {
            for (int s = 0; s < (grille.gettCase() - 16) / 2; s++) {
                Matrix matrix;
                switch (i) {
                    case 0:
                        retrecir[i][s] = Bitmap.createScaledBitmap(grille.getA(), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        matrix = new Matrix();
                        matrix.postRotate(s);
                        rotate[i][s] = Bitmap.createScaledBitmap(Bitmap.createBitmap(retrecir[i][s], 0, 0, retrecir[i][s].getWidth(), retrecir[i][s].getHeight(), matrix, true), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        break;
                    case 1:
                        retrecir[i][s] = Bitmap.createScaledBitmap(grille.getB(), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        matrix = new Matrix();
                        matrix.postRotate(s);
                        rotate[i][s] = Bitmap.createScaledBitmap(Bitmap.createBitmap(retrecir[i][s], 0, 0, retrecir[i][s].getWidth(), retrecir[i][s].getHeight(), matrix, true), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        break;
                    case 2:
                        retrecir[i][s] = Bitmap.createScaledBitmap(grille.getC(), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        matrix = new Matrix();
                        matrix.postRotate(s);
                        rotate[i][s] = Bitmap.createScaledBitmap(Bitmap.createBitmap(retrecir[i][s], 0, 0, retrecir[i][s].getWidth(), retrecir[i][s].getHeight(), matrix, true), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        break;
                    case 3:
                        retrecir[i][s] = Bitmap.createScaledBitmap(grille.getD(), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        matrix = new Matrix();
                        matrix.postRotate(s);
                        rotate[i][s] = Bitmap.createScaledBitmap(Bitmap.createBitmap(retrecir[i][s], 0, 0, retrecir[i][s].getWidth(), retrecir[i][s].getHeight(), matrix, true), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        break;
                    case 4:
                        retrecir[i][s] = Bitmap.createScaledBitmap(grille.getE(), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        matrix = new Matrix();
                        matrix.postRotate(s);
                        rotate[i][s] = Bitmap.createScaledBitmap(Bitmap.createBitmap(retrecir[i][s], 0, 0, retrecir[i][s].getWidth(), retrecir[i][s].getHeight(), matrix, true), Math.round(grille.gettCase() - 16 - s * 2), Math.round(grille.gettCase() - 16 - s * 2), false);
                        break;

                }

            }

        }

    }

    public Bitmap getRetrecir(char type, int image) {
        if (type == 'A') {
            return retrecir[0][image];
        }
        if (type == 'B') {
            return retrecir[1][image];
        }
        if (type == 'C') {
            return retrecir[2][image];
        }
        if (type == 'D') {
            return retrecir[3][image];
        }
        if (type == 'E') {
            return retrecir[4][image];
        }
        return null;


    }

    public Bitmap getRotate(char type, int image) {
        if (type == 'A') {
            return rotate[0][image];
        }
        if (type == 'B') {
            return rotate[1][image];
        }
        if (type == 'C') {
            return rotate[2][image];
        }
        if (type == 'D') {
            return rotate[3][image];
        }
        if (type == 'E') {
            return rotate[4][image];
        }
        return null;


    }
}
