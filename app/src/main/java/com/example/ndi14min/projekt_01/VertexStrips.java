package com.example.ndi14min.projekt_01;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.provider.ContactsContract;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import static android.opengl.GLES20.*;

/**
 * Created by perjee on 10/6/16.
 */

public class VertexStrips {

    static final int VERTEX_POS_SIZE = 4;
    static final int COLOR_SIZE = 4;

    static final int VERTEX_POS_INDEX = 0;
    static final int COLOR_INDEX = 1;

    static final int VERTEX_POS_OFFSET = 0;
    static final int COLOR_OFFSET = 0;

    static final int VERTEX_ATTRIB_SIZE = VERTEX_POS_SIZE;
    static final int COLOR_ATTRIB_SIZE = COLOR_SIZE;
    private float coorData[];
    private int VERTEX_COUNT;


    private DataCollector d;
    private float[][] stripes;
    private float[] tempData;

    float[][] bufferData;

    private int r;
    private int c;

    private int rowNbr;


    private FloatBuffer vertexDataBuffer;
    private FloatBuffer colorDataBuffer;


    // Set color with red, green, blue and alpha (opacity) values
    float colorData[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;


    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    private int positionHandle;
    private int colorHandle;


    public VertexStrips(DataCollector d) {

        this.r = d.getRows();
        this.c = d.getCols();
        this.d=d;
        float tempData[];
        //float[] cData;
        bufferData = new float[r][];


        for(int i= 0; i<r; i++)
            setBuffer(getStrips(i),i);


        FloatBuffer vertexBuffer;

        //  Log.i("kaot", ""+cData.length);

        // coorData = new float[(r*(c*4))];

/*
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbv = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                cData.length * 4);
        // use the device hardware's native byte order
        bbv.order(ByteOrder.nativeOrder());

        //  Log.i("hejsan", "cp1");

        // create a floating point buffer from the ByteBuffer
        vertexDataBuffer = bbv.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexDataBuffer.put(cData);
        // set the buffer to read the first coordinate
        vertexDataBuffer.position(0);

        // Log.i("hejsan", "cp2");

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbc = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                colorData.length * 4);
        // use the device hardware's native byte order
        bbc.order(ByteOrder.nativeOrder());

        //  Log.i("hejsan", "cp3");

        // create a floating point buffer from the ByteBuffer
        colorDataBuffer = bbc.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorDataBuffer.put(colorData);
        // set the buffer to read the first coordinate
        colorDataBuffer.position(0);

        */

        //  Log.i("hejsan", "cp4");

        int vertexShader = Shaders.loadShader(GL_VERTEX_SHADER,
                Shaders.vsh2);
        int fragmentShader = Shaders.loadShader(GL_FRAGMENT_SHADER,
                Shaders.fsh);

        // Log.i("hejsan", "cp5");




        //private int runProgram(int vertexShader)
        // create empty OpenGL ES Program
        mProgram = startProgram(vertexShader, fragmentShader);




        // Log.i("hejsan", "cp6");

    }
    public int startProgram(int vs, int fs){

        int program = GLES20.glCreateProgram();
        // add the vertex shader to program
        GLES20.glAttachShader(program, vs);

        // add the fragment shader to program
        GLES20.glAttachShader(program, fs);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);

        return program;
    }


    public float[] getStrips(int ind) {

        //int ind = 0;

        //VertexPosition[][] vert = d.getVertPos();


        VertexPosition[][] vertR = d.getVertPosRows(ind);

        float[][] stripes = new float[r][];

        //   for (int j = 0; j < r - 1; j++) {

        tempData = new float[c * 8];

        for (int i = 0; i < c * 8; i = i + 8) {

            tempData[i] = vertR[0][i / 8].getxPos();
            tempData[i+ 1] = vertR[0][i / 8].getyPos();
            tempData[i+ 2] = 0.0f;
            tempData[i + 3] = 1.0f;


            tempData[i+ 4] = vertR[1][i / 8].getxPos();
            tempData[i + 5] = vertR[1][i / 8].getyPos();
            tempData[i + 6] = 0.0f;
            tempData[i + 7] = 1.0f;

        }
        /*tempData = new float[c * 8];

        for (int i = 0; i < c * 8; i = i + 8) {

            tempData[i] = vert[ind][i / 8].getxPos();
            tempData[i+ 1] = vertR[ind][i / 8].getyPos();
            tempData[i+ 2] = 0.0f;
            tempData[i + 3] = 1.0f;


            tempData[i+ 4] = vert[ind + 1][i / 8].getxPos();
            tempData[i + 5] = vert[ind + 1][i / 8].getyPos();
            tempData[i + 6] = 0.0f;
            tempData[i + 7] = 1.0f;

        }*/

        // stripes[j] = tempData;

        //  Log.i("kaoz", "j " + stripes[j].length);
        //    }

/*
        List<Float> list = new ArrayList<>();

        for (int k = 0; k < stripes.length; k++) {

            Log.i("kaos", "" + k);
            if (stripes[k] != null) {
                for (int l = 0; l < stripes[k].length; l++) {

                    list.add(stripes[k][l]);

                    //  Log.i("kaos", " "+stripes[k][l]);
                }
            }
        }

        float[] coorData = new float[list.size()];
        for (int i = 0; i < coorData.length; i++) {
            coorData[i] = list.get(i);
        }

*/
        VERTEX_COUNT = tempData.length / VERTEX_ATTRIB_SIZE;


        Log.i("kaos", "coorData " + tempData.length + " vertex count " + VERTEX_COUNT);

        //  for(int i = 0; i < tempData.length; i++)
        //    Log.i("hejsan", "" + tempData[i]);


        return tempData;

    }

    public void setBuffer(float[] data, int row){


        bufferData[row] = data;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbv = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                bufferData[row].length * 4);
        // use the device hardware's native byte order
        bbv.order(ByteOrder.nativeOrder());

        //  Log.i("hejsan", "cp1");

        // create a floating point buffer from the ByteBuffer
        vertexDataBuffer = bbv.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexDataBuffer.put(bufferData[row]);
        // set the buffer to read the first coordinate
        vertexDataBuffer.position(0);

        // Log.i("hejsan", "cp2");

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbc = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                colorData.length * 4);
        // use the device hardware's native byte order
        bbc.order(ByteOrder.nativeOrder());

          Log.i("hejsan", "cp3");

        // create a floating point buffer from the ByteBuffer
        colorDataBuffer = bbc.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorDataBuffer.put(colorData);
        // set the buffer to read the first coordinate
        colorDataBuffer.position(0);

    }
    public float[] getBuffer(int row){



        return bufferData[row];
    }
99999



    public void draw(float[] mvpMatrix, float) {
        // Add program to OpenGL ES environment

        GLES20.glUseProgram(mProgram);

        for (int i = 0; i<bufferData.length; i++) {
            vertexDataBuffer.put(getBuffer(i));




            //  Log.i("hejsan", "cp7");

            // get handle to shape's transformation matrix
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            GLES20.glEnableVertexAttribArray(positionHandle);
            // Log.i("hejsan", "cp8");


            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(positionHandle, VERTEX_POS_SIZE,
                    GLES20.GL_FLOAT, false,
                    VERTEX_ATTRIB_SIZE * 4, vertexDataBuffer);


            colorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
            GLES20.glEnableVertexAttribArray(colorHandle);
            GLES20.glVertexAttribPointer(colorHandle, COLOR_SIZE,
                    GLES20.GL_FLOAT, false,
                    COLOR_ATTRIB_SIZE * 4, colorDataBuffer);


            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

            Log.i("hejsan", "cp11 " + VERTEX_COUNT);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT);

            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(colorHandle);
        }

    }
/*
    public float[] calcMatrix(float[] mViewMatrix, float[] mProjectionMatrix ){
        // Redraw background color

        float[] mMVPmatrix = new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mViewMatrix, 0);

        //Matrix.scaleM(mMVPmatrix, 0, mViewMatrix, 0, xScaleFactor, yScaleFactor, 1);
        Matrix.translateM(mVi, 0, , 0, -400f);



        Matrix.multiplyMM(CTM, 0, CTM, 0, mRotationMatrix, 0);

        return CTM;



    }*/
}
