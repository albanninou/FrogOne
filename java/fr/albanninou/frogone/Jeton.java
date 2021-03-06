package fr.albanninou.frogone;

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
    private boolean select;
    private boolean canbreak;
    private float image = 0;
    private float sens = 1;
    private boolean broke = false;
    private Grille grille;
    private int ligne, colonne;
    private boolean loadTexture = false;
    private float rotate = 0;
    private int[] lc;

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
        if (!loadTexture) {
            loadGLTexture(gl);
            loadTexture = true;
        }
        if (type2 == 'V' && !canbreak) {
            return;
        }
        gl.glTranslatef(-0.73f + tLigne * (colonne + 1) + tCase * colonne + tCase * 0.5f, 1.245f - tLigne * (ligne + 1) - tCase * ligne - tCase * 0.5f, -3);
        gl.glRotatef(180, 0.0f, 0.0f, 1.0f);

        if (broke) {
            gl.glRotatef(image * 150f, 0.0f, 0.0f, 1.0f);
            gl.glScalef(1f - image * 2, 1.0f - image * 2, 0f);
            image = image + 0.01f;
            if (1 - image * 2 < 0) {
                type2 = 'V';
                type = 'V';
                broke = false;
                image = 0;
                loadTexture = false;
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
            } else {
                if (canbreak) {
                    if (1 - image < 0.7) {
                        sens = -0.005f;
                    }
                    if (1 - image >= 1) {
                        sens = 0.005f;
                    }
                    image = image + sens;
                    rotate = rotate + 0.01f;
                    gl.glScalef(0.7f, 0.7f, 0f);
                    gl.glRotatef(rotate * 150f, 0.0f, 0.0f, -1.0f);

                } else {
                    image = 0;
                }
            }
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
        loadTexture = true;
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
        if (type2 == 'V') {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, grille.getCercle(), 0);
        }

    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        loadTexture = false;
        this.type = type;
        this.type2 = type;
    }

    public boolean getBroke() {
        return broke;
    }

    public void setBroke(boolean broke) {
        loadTexture = false;
        this.broke = broke;
    }

    Jeton broke() {
        canbreak = false;
        select = false;
        broke = true;
        image = 0;
        return this;
    }

    public boolean isLoadTexture() {
        return loadTexture;
    }

    public void setLoadTexture(boolean loadTexture) {
        this.loadTexture = loadTexture;
    }

    Jeton setCanBreak(boolean canbreak) {
        this.canbreak = canbreak;
        if (!canbreak) {
            rotate = 0;
        }
        return this;
    }


}