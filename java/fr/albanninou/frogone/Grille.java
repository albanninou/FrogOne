package fr.albanninou.frogone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;

import static fr.albanninou.frogone.MainActivity.lvl;

public class Grille {
    private Bitmap a, b, c, d, e, winbmp, rotate;
    private Bitmap lastBmp;
    private int lastDegre;
    private char lasttype;
    private char grille[][][];
    private int lc[];
    private int select[] = {-1, 1};
    private int coup = 0;
    private Jeton jeton[][];
    private float tCase, tLigne;
    private int wintaillel = 0;
    private int wintaillec = 0;
    private Activity activity;
    private boolean stop = false, finish = false, win = false;
    private int lvl;

    public Grille(int lvl, Activity activity) {
        grille = new char[100][100][100];
        this.grille[0] = ConfigLvl.getGrille(lvl);
        this.lc = ConfigLvl.getLc(lvl);
        jeton = new Jeton[100][100];
        for (int l = 0; l < lc[0]; l++) {
            for (int c = 0; c < lc[1]; c++) {
                jeton[l][c] = new Jeton(grille[0][l][c], lc, this);
            }
        }
        Resources res = activity.getResources();
        a = BitmapFactory.decodeResource(res, R.drawable.jetonr);
        b = BitmapFactory.decodeResource(res, R.drawable.jetonv);
        c = BitmapFactory.decodeResource(res, R.drawable.jetonb);
        d = BitmapFactory.decodeResource(res, R.drawable.jetonn);
        e = BitmapFactory.decodeResource(res, R.drawable.jetono);
        winbmp = BitmapFactory.decodeResource(res, R.drawable.win);
        this.activity = activity;
        this.lvl = lvl;
    }

    public boolean getWinBoolean() {
        return win;
    }

    public void setWinBoolean(boolean win) {
        this.win = win;
    }

    public void drawJeton(Canvas canvas) {
        for (int l = 0; l < lc[0]; l++) {
            for (int c = 0; c < lc[1]; c++) {
                RectF rect = new RectF(8 + tLigne * (c + 1) + tCase * c, 8 + tLigne * (l + 1) + tCase * l, tLigne * (c + 1) + tCase * (c + 1) - 8, tLigne * (l + 1) + tCase * (l + 1) - 8);
                jeton[l][c].setCanBreak(false);
                if (select[0] != -1) {
                    if (GameOneCanBreak(l, c)) {
                        jeton[l][c].setCanBreak(true);
                    }
                }
                jeton[l][c].drawJeton(canvas, rect);

            }
        }

    }

    public boolean getFinish() {
        return finish;
    }

    public void drawWin(Canvas canvas) {
        wintaillel = wintaillel + 30;
        wintaillec = wintaillec + 15;
        if (wintaillec < canvas.getWidth() && wintaillel < canvas.getHeight()) {
            canvas.drawColor(Color.WHITE);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(winbmp, wintaillec, wintaillel, true);
            canvas.drawBitmap(scaledBitmap, canvas.getWidth() / 2 - wintaillec / 2, canvas.getHeight() / 2 - wintaillel / 2, null);
        } else {
            activity.finish();
            Intent intent = new Intent(activity, LvlActivity.class);
            if (this.lvl != ConfigLvl.lvlmax) {
                intent.putExtra("lvl", this.lvl + 1);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = preferences.edit();
                if (preferences.getInt("lvl", 1) == this.lvl) {
                    editor.putInt("lvl", this.lvl + 1);
                    editor.apply();
                }
            } else {
                intent.putExtra("lvl", this.lvl);
            }

            activity.startActivity(intent);
            finish = true;


        }
    }

    public void restart() {
        for (int l = 0; l < lc[0]; l++) {
            for (int c = 0; c < lc[1]; c++) {
                jeton[l][c] = new Jeton(grille[0][l][c], lc, this);
            }
        }
        coup = 0;
        select[0] = -1;
    }

    public void undow() {
        if (coup > 0) {
            for (int l = 0; l < lc[0]; l++) {
                for (int c = 0; c < lc[1]; c++) {
                    jeton[l][c] = new Jeton(grille[coup - 1][l][c], lc, this);
                }
            }
            select[0] = -1;
            coup--;
        }
    }

    public void drawGrille(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.FILL);

        float taille = canvas.getWidth();
        if (lc[0] < lc[1]) {
            tCase = canvas.getWidth() / (lc[1] + 1);
            tLigne = tCase / (lc[1] + 1);
        } else {
            tCase = canvas.getWidth() / (lc[0] + 1);
            tLigne = tCase / (lc[0] + 1);
        }
        for (int l = 0; l < lc[0] + 1; l++) {
            canvas.drawRect(0, tCase * l + tLigne * l, tLigne * (lc[1] + 1) + tCase * lc[1], tCase * l + tLigne * (l + 1), paint);
        }
        for (int c = 0; c < lc[1] + 1; c++) {
            canvas.drawRect(tLigne * c + tCase * c, 0, tLigne * (c + 1) + tCase * c, tLigne * (lc[0] + 1) + tCase * lc[0], paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawRect((float) 0, tLigne * (lc[0] + 1) + tCase * lc[0], (float) canvas.getWidth(), (float) canvas.getHeight(), paint);
        canvas.drawRect(tLigne * (lc[1] + 1) + tCase * lc[1], (float) 0, (float) canvas.getWidth(), (float) canvas.getHeight(), paint);
    }

    boolean getWin() {
        for (int l = 0; l < lc[0]; l++) {
            for (int c = 0; c < lc[1]; c++) {
                if (grille[coup][l][c] != 'V') {
                    return false;
                }
            }
        }
        return true;
    }

    public Jeton[][] getJeton() {
        return jeton;
    }

    public void setSelect(int y, int x) {
        if (grille[coup][y][x] != 'V') {
            if (select[0] == -1) {
                select[0] = y;
                select[1] = x;
                jeton[y][x].setSelect(true);
            } else {
                jeton[select[0]][select[1]].setSelect(false);
                select[0] = -1;
            }
        } else {
            if (select[0] != -1) {
                int[] casser = {y, x};
                GameOneBreak(casser);
            }

        }
    }

    public Bitmap rotateImage(Bitmap src, int degree, int image, RectF rect, char type) {
        if (lastBmp == src && degree == lastDegre && lasttype == type && rotate != null) {
            return rotate;
        } else {
            if (src != null) {
                if (Math.round(src.getWidth() - image * 2) > 0 && Math.round(src.getHeight() - image * 2) > 0) {
                    lastBmp = src;
                    lastDegre = degree;
                    lasttype = type;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(degree);
                    Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
                    bmp = Bitmap.createScaledBitmap(bmp, Math.round(rect.width()) - image * 2, Math.round(rect.height() - image * 2), false);
                    rotate = bmp;
                    return bmp;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public char[][][] getGrille() {
        return this.grille;
    }

    public int[] getLc() {
        return this.lc;
    }

    boolean GameOneCanBreak(int l, int c) {
        int selectL = select[0], selectC = select[1], coups;
        coups = coup;
        boolean cassez = false;
        boolean stop = false;
        if (grille[coups][l][c] != 'V') {//on verifie que la casse est bien vide
            return false;
        }
        if (!(selectC == c || selectL == l || Math.abs(selectL - l) == Math.abs(selectC - c))) {//on verifie si il joue bien en ligne ou en diagonale
            return false;
        }
        if (!GameOneTrajectoire(selectL, selectC, l, c)) {
            return false;
        }
        grille[coups][l][c] = grille[coups][selectL][selectC];
        grille[coups][selectL][selectC] = 'V';
        int rang;
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (c - 2 + i + rang > lc[1] - 1 || c - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coups][l][c - 2 + i + rang] == grille[coups][l][c]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                cassez = true;
            }

        }
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (l - 2 + i + rang > lc[0] - 1 || l - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coups][l - 2 + i + rang][c] == grille[coups][l][c]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                cassez = true;
            }

        }
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (l - 2 + i + rang > lc[0] - 1 || l - 2 + i + rang < 0 || c - 2 + i + rang > lc[1] - 1 || c - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coups][l - 2 + i + rang][c - 2 + i + rang] == grille[coups][l][c]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                cassez = true;
            }

        }

        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (l + 2 - i - rang > lc[0] - 1 || l + 2 - i - rang < 0 || c - 2 + i + rang > lc[1] - 1 || c - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coups][l + 2 - i - rang][c - 2 + i + rang] == grille[coups][l][c]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                cassez = true;
            }

        }
        grille[coups][selectL][selectC] = grille[coups][l][c];
        grille[coups][l][c] = 'V';
        return cassez;
    }

    boolean GameOneBreak(int[] cassez) {
        int selectL = select[0], selectC = select[1], cassezL = cassez[0], cassezC = cassez[1];
        boolean Bcassez = false;
        boolean stop = false;
        for (int l = 0; l < lc[0]; l++) {
            for (int c = 0; c < lc[1]; c++) {
                grille[coup + 1][l][c] = grille[coup][l][c];
            }
        }

        coup++;
        if (grille[coup][cassezL][cassezC] != 'V') {//on verifie que la casse est bien vide
            return false;
        }
        if (!(selectC == cassezC || selectL == cassezL || Math.abs(selectL - cassezL) == Math.abs(selectC - cassezC))) {//on verifie si il joue bien en ligne ou en diagonale
            return false;
        }
        if (!GameOneTrajectoire(selectL, selectC, cassezL, cassezC)) {
            return false;
        }
        grille[coup][cassezL][cassezC] = grille[coup][selectL][selectC];
        grille[coup][selectL][selectC] = 'V';

        int rang;
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (cassezC - 2 + i + rang > lc[1] - 1 || cassezC - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coup][cassezL][cassezC - 2 + i + rang] == grille[coup][cassezL][cassezC]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                for (int f = 0; f < rang; f++) {
                    if (cassezC - 2 + i + f != cassezC) {
                        grille[coup][cassezL][cassezC - 2 + i + f] = 'V';
                        if (!jeton[cassezL][cassezC - 2 + i + f].getBroke()) {
                            jeton[cassezL][cassezC - 2 + i + f].broke();
                        }
                    }
                }
                i = 3;
                Bcassez = true;
            }

        }
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (cassezL - 2 + i + rang > lc[0] - 1 || cassezL - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coup][cassezL - 2 + i + rang][cassezC] == grille[coup][cassezL][cassezC]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                for (int f = 0; f < rang; f++) {
                    if (cassezL - 2 + i + f != cassezL) {
                        grille[coup][cassezL - 2 + i + f][cassezC] = 'V';
                        if (!jeton[cassezL - 2 + i + f][cassezC].getBroke()) {
                            jeton[cassezL - 2 + i + f][cassezC].broke();
                        }
                    }
                }
                i = 3;
                Bcassez = true;
            }

        }
        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (cassezL - 2 + i + rang > lc[0] - 1 || cassezL - 2 + i + rang < 0 || cassezC - 2 + i + rang > lc[1] - 1 || cassezC - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coup][cassezL - 2 + i + rang][cassezC - 2 + i + rang] == grille[coup][cassezL][cassezC]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                for (int f = 0; f < rang; f++) {
                    if (cassezL - 2 + i + f != cassezL) {
                        grille[coup][cassezL - 2 + i + f][cassezC - 2 + i + f] = 'V';
                        if (!jeton[cassezL - 2 + i + f][cassezC - 2 + i + f].getBroke()) {
                            jeton[cassezL - 2 + i + f][cassezC - 2 + i + f].broke();
                        }
                    }
                }
                i = 3;
                Bcassez = true;
            }

        }

        for (int i = 0; i < 3; i++) {
            rang = 0;

            while (!stop) {
                if (cassezL + 2 - i - rang > lc[0] - 1 || cassezL + 2 - i - rang < 0 || cassezC - 2 + i + rang > lc[1] - 1 || cassezC - 2 + i + rang < 0) {
                    stop = true;
                } else {
                    if (grille[coup][cassezL + 2 - i - rang][cassezC - 2 + i + rang] == grille[coup][cassezL][cassezC]) {
                        rang++;
                    } else {
                        stop = true;
                    }
                }
            }
            stop = false;
            if (rang >= 3) {
                for (int f = 0; f < rang; f++) {
                    if (cassezL + 2 - i - f != cassezL) {
                        grille[coup][cassezL + 2 - i - f][cassezC - 2 + i + f] = 'V';
                        if (!jeton[cassezL + 2 - i - f][cassezC - 2 + i + f].getBroke()) {
                            jeton[cassezL + 2 - i - f][cassezC - 2 + i + f].broke();
                        }
                    }
                }
                i = 3;
                Bcassez = true;
            }

        }
        if (Bcassez) {
            select[0] = -1;
            grille[coup][cassezL][cassezC] = 'V';
            Jeton temp = jeton[cassezL][cassezC];
            jeton[cassezL][cassezC] = jeton[selectL][selectC];
            jeton[selectL][selectC] = temp;
            jeton[cassezL][cassezC].broke();
            return true;
        } else {
            grille[coup][selectL][selectC] = grille[coup][cassezL][cassezC];
            grille[coup][cassezL][cassezC] = 'V';
            coup--;
            return false;
        }


    }

    boolean GameOneTrajectoire(int l, int c, int l2, int c2) {
        int coups = coup;
        if (l == l2) {
            if (c < c2) {
                for (int i = c + 1; i < c2; i++) {
                    if (i <= lc[1]) {
                        if (grille[coup][l][i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                for (int i = c2 + 1; i < c; i++) {
                    if (i <= lc[1]) {
                        if (grille[coups][l][i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        if (c == c2) {
            if (l < l2) {
                for (int i = l + 1; i < l2; i++) {
                    if (i <= lc[0]) {
                        if (grille[coups][i][c] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                for (int i = l2 + 1; i < l; i++) {
                    if (i <= lc[0]) {
                        if (grille[coups][i][c] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        if ((l - c) == (l2 - c2)) {
            if ((l + c) > (l2 + c2)) {
                for (int i = 1; i < l - l2; i++) {
                    if (l - i >= 0 && c - i >= 0) {
                        if (grille[coups][l - i][c - i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                for (int i = 1; i < l2 - l; i++) {
                    if (l + i <= lc[0] && c + i <= lc[1]) {
                        if (grille[coups][l + i][c + i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            }
        } else {
            if (l > l2) {
                for (int i = 1; i < l - l2; i++) {
                    if (l - i >= 0 && c + i <= lc[1]) {
                        if (grille[coups][l - i][c + i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                for (int i = 1; i < l2 - l; i++) {
                    if (l + i <= lc[0] && c - i >= 0) {
                        if (grille[coups][l + i][c - i] != 'V') {
                            return false;
                        }
                    }
                }
                return true;
            }
        }


    }

    public Bitmap getA() {
        return a;
    }

    public Bitmap getB() {
        return b;
    }

    public Bitmap getC() {
        return c;
    }

    public Bitmap getD() {
        return d;
    }

    public Bitmap getE() {
        return e;
    }

    public float gettCase() {
        return tCase;
    }

    public void settCase(float tCase) {
        this.tCase = tCase;
    }

    public float gettLigne() {
        return tLigne;
    }

    public void settLigne(float tLigne) {
        this.tLigne = tLigne;
    }
}
