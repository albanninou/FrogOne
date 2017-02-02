package fr.albanninou.frogone;

import android.graphics.Bitmap;
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
    private float image = 0;
    private float sens = 1;
    private boolean broke = false;
    private Bitmap bmp;
    private boolean isdraw = false;
    private int rotation = 0;
    private Grille grille;
    private int ligne, colonne;
    private boolean loadTexture = false;

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

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelect() {
        loadTexture = true;
        return this.select;
    }

    Jeton setSelect(boolean select) {
        if (select) {
            image = 0;
        }
        this.select = select;

        return this;
    }

    public boolean isCanbreak() {
        return canbreak;
    }

    public void draw(GL10 gl, float tLigne, float tCase) {
        if (!loadTexture && (type2 != 'V' || canbreak)) {
            loadGLTexture(gl);
            loadTexture = true;
        }
        if (type2 == 'V' && !select) {
            return;
        }

        gl.glTranslatef(-1.48f + tLigne * (colonne + 1) + tCase * colonne + tCase * 0.5f, 2.53f - tLigne * (ligne + 1) - tCase * ligne - tCase * 0.5f, -6.10f);
        gl.glRotatef(180, 0.0f, 0.0f, 1.0f);

        if (broke) {
            gl.glRotatef(image * 150f, 0.0f, 0.0f, 1.0f);
            gl.glScalef(1f - image * 2, 1.0f - image * 2, 0f);
            image = image + 0.005f;
            if (1 - image * 2 < 0) {
                type2 = 'V';
                broke = false;
                image = 0;
            }
        } else {
            if (type != 'V') {
                if (select || image != 0) {
                    if (select) {
                        if (1 - image < 0.7) {
                            sens = -0.005f;
                        }
                        if (1 - image >= 1) {
                            sens = 0.005f;
                        }
                        image = image + sens;
                    } else {
                        image = image - 0.02f;
                    }
                    if (image < 0) {
                        image = 0;
                    }
                    gl.glScalef(1f - image * 2, 1.0f - image * 2, 0f);
                }
            } /*else {
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
            }*/
        }

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

        if (type2 == 'A') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getA(), 0);
        }
        if (type2 == 'B') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getB(), 0);
        }
        if (type2 == 'C') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getC(), 0);
        }
        if (type2 == 'D') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getD(), 0);
        }
        if (type2 == 'E') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getE(), 0);
        }
        if (canbreak) {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getE(), 0);
        }

    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
        this.type2 = type;
    }

    public boolean getBroke() {
        return broke;
    }

    public void setBroke(boolean broke) {
        if (broke == false) {
            loadTexture = false;
        }
        this.broke = broke;
    }

    Jeton broke() {
        loadTexture = false;
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