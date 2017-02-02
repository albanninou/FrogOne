package fr.albanninou.frogone;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by eleve on 31/01/2017.
 */

public class Render implements GLSurfaceView.Renderer {

    /**
     * Square instance
     */

    float tCase = 0, tLigne = 0;
    float lCase = 0, lLigne = 0;
    float ratio = 0;
    /**
     * Angle For The Pyramid
     */
    private float rtri;
    /**
     * Angle For The Cube
     */
    private float rquad;
    private Grille grille;

    /**
     * Instance the Triangle and Square objects
     */
    public Render(Grille grille) {
        this.grille = grille;
    }

    /**
     * The Surface is created/init()
     */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        for (int l = 0; l < grille.getLc()[0]; l++) {
            for (int c = 0; c < grille.getLc()[1]; c++) {
                grille.getJeton()[l][c].loadGLTexture(gl);
            }
        }
        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping ( NEW )
        gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
        gl.glClearColor(0, 0, 0, 0);    //Black Background
        gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


    }

    /**
     * Here we do our drawing
     */
    public void onDrawFrame(GL10 gl) {

        //Clear Screen And Depth Buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();                    //Reset The Current Modelview Matrix
        //Log.w("myApp", "tCase : "+tCase);
        if (!grille.isLoad() || grille.getWinBoolean()) {
            return;
        }
        if (grille.getJeton() != null) {
            for (int l = 0; l < grille.getLc()[0]; l++) {
                for (int c = 0; c < grille.getLc()[1]; c++) {
                    //gl.glRotatef(rquad, 0.0f, 0.0f, 1.0f);    //Rotate The Square On The X axis ( NEW )
                    if (grille.getSelect()[0] != -1) {
                        if (grille.GameOneCanBreak(l, c)) {
                            grille.getJeton()[l][c].setCanBreak(true);
                        } else {
                            grille.getJeton()[l][c].setCanBreak(false);
                        }
                    } else {
                        grille.getJeton()[l][c].setCanBreak(false);
                    }
                    if (grille.getJeton()[l][c].getType() != 'V' || grille.getJeton()[l][c].isSelect() || grille.getJeton()[l][c].getBroke() || grille.getJeton()[l][c].isCanbreak()) {
                        if (!grille.getJeton()[l][c].isLoadTexture()) {
                            grille.getJeton()[l][c].loadGLTexture(gl);
                        }
                        grille.getJeton()[l][c].draw(gl, tLigne, tCase);
                    }
                    gl.glLoadIdentity();
                }
            }
        }
        //Draw the square

        //Reset The Current Modelview Matrix
        gl.glLoadIdentity();

        //Decrease The Rotation Variable For The Quad ( NEW )
    }

    /**
     * /**
     * If the surface changes, reset the view
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (grille.getLc()[0] < grille.getLc()[1]) {
            tCase = 3f / (grille.getLc()[1] + 1);
            tLigne = tCase / (grille.getLc()[1] + 1);
        } else {
            tCase = 3f / (grille.getLc()[0] + 1);
            tLigne = tCase / (grille.getLc()[0] + 1);
        }
        tCase = (Math.round((int) (tCase * 100f)) - 0.5f) / 100f;
        tLigne = (float) (Math.round((int) (tLigne * 100f) - 0.5f)) / 100f;
        for (int l = 0; l < grille.getLc()[0]; l++) {
            for (int c = 0; c < grille.getLc()[1]; c++) {
                float vertices[] = {
                        -tCase / 2f, -tCase / 2f, 0.0f,    //Bottom Left
                        tCase / 2f, -tCase / 2f, 0.0f,        //Bottom Right
                        -tCase / 2f, tCase / 2f, 0.0f,        //Top Left
                        tCase / 2f, tCase / 2f, 0.0f        //Top Right
                };
                grille.getJeton()[l][c].setVertices(vertices);
            }
        }
        if (height == 0) {                        //Prevent A Divide By Zero By
            height = 1;                        //Making Height Equal One
        }
        gl.glViewport(0, 0, width, height);    //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION);    //Select The Projection Matrix
        gl.glLoadIdentity();                    //Reset The Projection Matrix

        //Calculate The Aspect Ratio Of The Window

        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);    //Select The Modelview Matrix
        gl.glLoadIdentity();                    //Reset The Modelview Matrix
    }
}
