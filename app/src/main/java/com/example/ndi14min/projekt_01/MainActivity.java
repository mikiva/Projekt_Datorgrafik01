package com.example.ndi14min.projekt_01;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    MyGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

    }

}

class MyGLSurfaceView extends GLSurfaceView {
    private final CGRenderer mRenderer;


    private final float TOUCH_SCALE_FACTOR = 0.13f; //180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context){
        super(context);

        InputStream in = getResources().openRawResource(R.raw.dem);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new CGRenderer(in);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                mRenderer.setXAngle(mRenderer.getXAngle() + dx * TOUCH_SCALE_FACTOR);
                mRenderer.setYAngle(mRenderer.getYAngle() + dy * TOUCH_SCALE_FACTOR);
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}

class CGRenderer implements GLSurfaceView.Renderer {


    private final float[] mViewMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];

/*

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private float[] CTM = new float[16];
    */

    VertexStrips strip;
    int r;
    public volatile float xAngle;
    public volatile float yAngle;
    private VertexStrips mCubeStrips;
    private DataCollector dataCol;

    private ImportFilter filter;

    public CGRenderer(InputStream in){
        super();
        filter = new ImportFilter(in);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1f);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);


      //  strips = new VertexStrips[r];

        strip = new VertexStrips(dataCol);


    }

    @Override
    public void onDrawFrame(GL10 unused) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.translateM(mViewMatrix, 0, 0, 0, -20000f);
        // Calculate the projection and view transformation
        // Draw shape

        //mCube.draw(CTM);

        strip.draw(mViewMatrix, mProjectionMatrix);

        //mCubeStrips.draw(CTM);
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 50000);

    }

    public float getXAngle() {
        return xAngle;
    }

    public void setXAngle(float angle) {
        xAngle = angle;
    }

    public float getYAngle() {
        return yAngle;
    }

    public void setYAngle(float angle) {
        yAngle = angle;
    }
}





/*
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(4);
        pixelGrid.setNumRows(6);

        setContentView(pixelGrid);
    }
}*/
