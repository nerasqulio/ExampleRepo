package com.mycompany.shaderconfigurator;

import com.jogamp.opengl.GL4;
import java.lang.reflect.Type;
import java.util.Map.Entry;

public class Shader extends ShaderBlueprint {

    private int index;
    private GL4 gl;
    private boolean extensionAvailable;
    private static Shader instance;

    private Shader(GL4 gl) {
        this.gl = gl;
        extensionAvailable = gl.isExtensionAvailable("GL_ARB_gpu_shader_fp64");
        super.setGL(gl);

    }

    public static Shader getInstance(GL4 gl) {
        if (instance == null) {
            instance = new Shader(gl);
        }
        return instance;
    }

    public void loadShader(String shaderPath, int type) {
        super.configureShader(shaderPath, type);

        
    }

    public void setAttributes() {
        index = 0;
        for (Entry<String, Tuple> entry : super.attributeMap.entrySet()) {
            super.bindAttrib(index, entry.getKey());
            index++;
        }
    }
    public void addUniformToMap(String key, Tuple value) {
        super.uniformMap.put(key, value);
    }

    public void setUniformVariable(Object uniform, String uniformName) throws UniformVariableNotFound, UnmatchedUniformType {

        try {
            if (super.uniformMap.containsKey(uniformName)) {

                
                int uniformId = super.uniformMap.get(uniformName).getID();
                Type t = super.uniformMap.get(uniformName).getJavaType();
                String GLSLType = super.uniformMap.get(uniformName).getGLSLType();

                if (GLSLType.contains("vec4")) {

                    int unitSize = Integer.parseInt("" + GLSLType.charAt(GLSLType.length() - 1));

                    if (t.equals(Double.class) && this.extensionAvailable) {
                        double[] valueArr = (double[]) uniform;
                        gl.glUniform4dv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Float.class)) {
                        float[] valueArr = (float[]) uniform;
                        gl.glUniform4fv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Integer.class)) {
                        int[] valueArr = (int[]) uniform;
                        gl.glUniform4iv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    }
                } else if (GLSLType.contains("vec3")) {
                    int unitSize = Integer.parseInt("" + GLSLType.charAt(GLSLType.length() - 1));

                    if (t.equals(Double.class) && this.extensionAvailable) {
                        double[] valueArr = (double[]) uniform;
                        gl.glUniform3dv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Float.class)) {
                        float[] valueArr = (float[]) uniform;
                        gl.glUniform3fv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Integer.class)) {
                        int[] valueArr = (int[]) uniform;
                        gl.glUniform3iv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    }
                } else if (GLSLType.contains("vec2")) {

                    int unitSize = Integer.parseInt("" + GLSLType.charAt(GLSLType.length() - 1));

                    if (t.equals(Double.class) && this.extensionAvailable) {
                        double[] valueArr = (double[]) uniform;
                        gl.glUniform2dv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Float.class)) {
                        float[] valueArr = (float[]) uniform;
                        gl.glUniform2fv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    } else if (t.equals(Integer.class)) {
                        int[] valueArr = (int[]) uniform;
                        gl.glUniform2iv(uniformId, valueArr.length / unitSize, valueArr, 0);
                    }
                } else if (!(GLSLType.contains("vec") || GLSLType.contains("mat"))) {

                    if (t.equals(Double.class) && this.extensionAvailable) {
                        double[] valueArr = (double[]) uniform;
                        gl.glUniform1dv(uniformId, valueArr.length, valueArr, 0);
                    } else if (t.equals(Float.class)) {
                        float[] valueArr = (float[]) uniform;
                        gl.glUniform1fv(uniformId, valueArr.length, valueArr, 0);
                    } else if (t.equals(Integer.class)) {
                        int[] valueArr = (int[]) uniform;
                        gl.glUniform1iv(uniformId, valueArr.length, valueArr, 0);
                    }
                } else {
                    if (uniform instanceof MatrixCompound) {
                        super.loadMat(uniformId, (MatrixCompound) uniform);

                    }

                }
            } else {
                throw new UniformVariableNotFound("Variable - " + uniformName + " - Name Not Found In GLSL !!");

            }
        } catch (UniformVariableNotFound | ClassCastException e) {
            if(e instanceof UniformVariableNotFound){
                 System.err.println(e.getMessage());
            }else{
                System.err.println("Cast operation interrupted while attempting to load '"+ uniformName+"' variable !! Make sure annotation type matches the variable type!!");
            }
            
            System.exit(0);
        }
    }

    @Override
    public void getAllUniformsAndAttribs() {
        // TODO Auto-generated method stub

        for (Entry<String, Tuple> entry : super.uniformMap.entrySet()) {
            entry.getValue().setID(super.getUniform(entry.getKey()));
        }
        for (Entry<String, Tuple> entry : super.attributeMap.entrySet()) {
            entry.getValue().setID(super.getAttribLoc(entry.getKey()));
        }
        
        
     for (Entry<String, Tuple> entry : super.uniformMap.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue().getName()+" "+entry.getValue().getGLSLType());
}

    }

    @Override
    public void bindAttribs() {
        // TODO Auto-generated method stub

    }

}
