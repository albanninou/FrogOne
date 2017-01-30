package fr.albanninou.frogone;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by eleve on 09/01/2017.
 */

public class Jeton {
    private char type;
    private char type2;
    private int[] place;
    private boolean select;
    private boolean canbreak;
    private int image = 0;
    private int sens = 1;
    private boolean broke = false;
    private Bitmap bmp;
    private boolean isdraw = false;
    private int rotation = 0;
    private Grille grille;

    Jeton(char type, int[] place, Grille grille) {
        this.type = type;
        this.type2 = type;
        this.place = place;
        this.select = false;
        this.grille = grille;
    }

    public char getType() {
        return type;
    }

    public boolean getBroke() {
        return broke;
    }

    Jeton drawJeton(Canvas canvas, RectF rect) {

        if (type == 'A') {
            bmp = grille.getA();
        }
        if (type == 'B') {
            bmp = grille.getB();
        }
        if (type == 'C') {
            bmp = grille.getC();
        }
        if (type == 'D') {
            bmp = grille.getD();
        }
        if (type == 'E') {
            bmp = grille.getE();
        }
        if (broke) {
            image = image + 2;
            if (image < rect.height() / 2) {
                if (grille.getImage().getRotate(type2, image) != null) {
                    canvas.drawBitmap(grille.getImage().getRotate(type2, image), rect.left + image, rect.top + image, null);
                }
            } else {
                broke = false;
                image = 0;
            }
        } else {
            if (type != 'V') {
                if (select || image != 0) {
                    if (select) {
                        if (rect.height() / 2 - image < rect.height() / 4) {
                            sens = -1;
                        }
                        if (rect.height() / 2 - image >= rect.height() / 2) {
                            sens = 1;
                        }
                        image = image + sens;
                    } else {
                        if (image - 2 > 0) {
                            image = image - 2;
                        } else {
                            image = 0;
                        }
                    }
                    if (image < 0) {
                        image = 0;
                    }
                    if (grille.getImage().getRetrecir(type, image) != null && !grille.getImage().getRetrecir(type, image).isRecycled()) {
                        canvas.drawBitmap(grille.getImage().getRetrecir(type, image), rect.left + image, rect.top + image, null);
                    }


                } else {
                    if (grille.getImage().getRetrecir(type, image) != null && !grille.getImage().getRetrecir(type, image).isRecycled()) {
                        canvas.drawBitmap(grille.getImage().getRetrecir(type, image), rect.left + image / 2, rect.top + image / 2, null);
                    }
                }
            } else {
                if (canbreak) {
                    image = image + sens;
                    if (rect.height() / 2 - image < rect.height() / 4 + 1) {
                        sens = -1;
                    }
                    if (rect.height() / 2 - image > rect.height() / 2) {
                        sens = 1;
                    }
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setStrokeWidth(3);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    canvas.drawCircle(rect.centerX(), rect.centerY(), rect.height() / 2 - image, paint);

                } else {
                    image = 0;
                }
            }
        }
        isdraw = true;
        return this;
    }

    Jeton setSelect(boolean select) {
        if (select) {
            image = 0;
        }
        this.select = select;

        return this;
    }

    Jeton broke() {
        select = false;
        broke = true;
        image = 0;
        type = 'V';
        return this;
    }

    Jeton setCanBreak(boolean canbreak) {
        this.canbreak = canbreak;

        return this;
    }


}