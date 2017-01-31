package fr.albanninou.frogone;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by eleve on 09/01/2017.
 */

public class Jeton {

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;    // buffer holding the texture coordinates
    private int[] textures = new int[1];

    private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 1.0f,        // top left		(V2)
            0.0f, 0.0f,        // bottom left	(V1)
            1.0f, 1.0f,        // top right	(V4)
            1.0f, 0.0f        // bottom right	(V3)
    };
    /**
     * The initial vertex definition
     */
    private float vertices[] = {
            -1.0f, -1.0f, 0.0f,    //Bottom Left
            1.0f, -1.0f, 0.0f,        //Bottom Right
            -1.0f, 1.0f, 0.0f,        //Top Left
            1.0f, 1.0f, 0.0f        //Top Right
    };


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
    private int ligne, colonne;

    Jeton(char type, int ligne, int colonne, Grille grille) {
        this.type = type;
        this.type2 = type;
        this.ligne = ligne;
        this.colonne = colonne;
        this.select = false;
        this.grille = grille;

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuf.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

    }

    public void setVertices(float vertices[]) {
        this.vertices = vertices;
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public void draw(GL10 gl, float tLigne, float tCase) {
        gl.glTranslatef(-0.495f + tLigne * (colonne + 1) + tCase * colonne + tCase * 0.5f, 0.85f - tLigne * (ligne + 1) - tCase * ligne - tCase * 0.5f, -2.1f);
        gl.glRotatef(180, 0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);


        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

    }

    public void loadGLTexture(GL10 gl) {
        // loading texture

        // generate one texture pointer
        gl.glGenTextures(1, textures, 0);
        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        if (type == 'A') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getA(), 0);
        }
        if (type == 'B') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getB(), 0);
        }
        if (type == 'C') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getC(), 0);
        }
        if (type == 'D') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getD(), 0);
        }
        if (type == 'E') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getE(), 0);
        }

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