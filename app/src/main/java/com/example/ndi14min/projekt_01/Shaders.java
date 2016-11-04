package com.example.ndi14min.projekt_01;



import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glShaderSource;

/**
 * Created by ndi14min on 2016-10-20.
 */
public class Shaders {

        public static final String VERTEX_SHADER_CODE =

                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex shader
                "attribute vec4 vPosition; \n" +
                        "attribute vec4 vNormal; \n" +
                        "varying vec4 c; \n" +
                        "uniform vec4 uColor; \n" +
                        "uniform vec4 lightpos; \n" +
                        "uniform vec4 La, Ld; \n" +
                        "uniform float Ka, Kd; \n" +
                        "uniform mat4 uMVPMatrix;\n" +
                        "//varying vec4 n; \n" +
                        "void main() { \n" +
                        "  //c = uColor; \n" +
                        "  vec4 n = uMVPMatrix * vNormal; \n"+
                        "  vec4 I_a = La * Ka; \n" +
                        "  vec4 transformedPos =  uMVPMatrix * vPosition; \n" +
                        "  vec4 transformedLightPos =  lightpos; \n" +
                        "  vec4 lightVec = normalize(transformedLightPos - transformedPos); \n" +
                        "  vec4 I_d = Ld * Kd * max(0.0f, dot(n, lightVec)); \n" +
                        "  c = I_a + I_d;  \n" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = transformedPos;\n" +
                        "}" ;



        public static final String FRAGMENT_SHADER_CODE =
                "precision mediump float;\n" +
                        "varying vec4 c; \n" +
                        "//varying vec4 n; \n" +
                        "void main() {\n" +
                        "  gl_FragColor = c;\n" +
                        "}";







        public static final String vsh =

                "attribute vec4 vPosition; \n" +
                        "attribute vec4 vNormal; \n" +
                        "varying vec4 c; \n" +
                        "uniform vec4 uColor; \n" +
                        "uniform vec4 lightpos; \n" +
                        "uniform vec4 La, Ld; \n" +
                        "uniform float Ka, Kd; \n" +
                        "uniform mat4 uMVPMatrix;\n" +
                        "//varying vec4 n; \n" +
                        "void main() { \n" +
                        "  c = uColor; \n" +
                        "  vec4 n = uMVPMatrix * vNormal; \n"+
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;\n" +
                        "}";

        public static final String fsh =

                "precision mediump float;\n" +
                        "varying vec4 c; \n" +
                        "//varying vec4 n; \n" +
                        "void main() {\n" +
                        "  gl_FragColor = c;\n" +
                        "}";



        public static final String vsh2 =
                "attribute vec4 vPosition; \n" +
                        "attribute vec4 vColor; \n" +
                        "uniform mat4 uMVPMatrix;\n" +
                        "varying vec4 c; \n" +
                        "void main() { \n" +
                        "  c = vColor; \n" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;\n" +
                        "}";

        public static int loadShader(int type, String shaderCode){

                int shader = glCreateShader(type);
                glShaderSource(shader, shaderCode);
                glCompileShader(shader);

                return shader;
        }

}
