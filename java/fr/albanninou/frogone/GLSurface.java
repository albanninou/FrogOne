package fr.albanninou.frogone;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

/**
 * Created by Jeune on 31/01/2017.
 */

public class GLSurface extends GLSurfaceView {
    public GLSurface(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }
}
