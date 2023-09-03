/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arda.eksi
 */
public abstract class ShaderBlueprint {

    private int prgID;
    private int vertexShaderID;
    private int fragmentShaderID;
    public static Map<String, Tuple> uniformMap = new HashMap<String, Tuple>();
    public static Map<String, Tuple> attributeMap = new HashMap<String, Tuple>();
    private List<Integer> shaderIDList = new ArrayList<Integer>();
    private ArrayDeque<Tuple> structElements = new ArrayDeque<Tuple>();
    private GL4 gl;
    private static boolean prgIDset = false;

    public void setGL(GL4 gl) {
        this.gl = gl;

    }

    public int getProgramID() {
        return this.prgID;
    }

    public void configureShader(String file, int type) {
        shaderIDList.add(load(file, type));

    }

    public void loadBuffers(){
        
        
    }
    public void loadShaders() {

        prgID = gl.glCreateProgram();

        for (int id : shaderIDList) {
            gl.glAttachShader(prgID, id);
        }
        gl.glLinkProgram(prgID);
        gl.glValidateProgram(prgID);

    }

    public abstract void bindAttribs();

    public int getAttribLoc(String name) {
        return gl.glGetAttribLocation(prgID, name);
    }

    public void bindAttrib(int attribute, String variable) {
        gl.glBindAttribLocation(prgID, attribute, variable);
    }

    public void loadMat(int location, MatrixCompound matCompound) {

        boolean extensionAvailable = gl.isExtensionAvailable("GL_ARB_gpu_shader_fp64");
        boolean isSingleDimension = matCompound.isSingleDimension();
        int firstDimension = matCompound.getFirstDimension();
        int secondDimension = matCompound.getSecondDimension();
        boolean isFloat = matCompound.isFloat();
        int arrayAmount = matCompound.getArrayAmount();

        if (isSingleDimension) {

            if (firstDimension == 2) {
                if (isFloat) {
                    gl.glUniformMatrix2fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                } else if (extensionAvailable) {
                    gl.glUniformMatrix2dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                }
            } else if (firstDimension == 3) {
                if (isFloat) {
                    gl.glUniformMatrix3fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                } else if (extensionAvailable) {
                    gl.glUniformMatrix3dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                }
            } else if (firstDimension == 4) {
                if (isFloat) {
                    gl.glUniformMatrix4fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                } else if (extensionAvailable) {
                    gl.glUniformMatrix4dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                }
            }

        } else {
            if (firstDimension == 2) {
                if (secondDimension == 3) {
                    if (isFloat) {
                        gl.glUniformMatrix2x3fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix2x3dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                } else if (secondDimension == 4) {
                    if (isFloat) {
                        gl.glUniformMatrix2x4fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix2x4dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                }
            } else if (firstDimension == 3) {
                if (secondDimension == 2) {
                    if (isFloat) {
                        gl.glUniformMatrix3x2fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix3x2dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                } else if (secondDimension == 4) {
                    if (isFloat) {
                        gl.glUniformMatrix3x4fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix3x4dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                }
            } else if (firstDimension == 4) {
                if (secondDimension == 2) {
                    if (isFloat) {
                        gl.glUniformMatrix4x2fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix4x2dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                } else if (secondDimension == 3) {
                    if (isFloat) {
                        gl.glUniformMatrix4x3fv(location, arrayAmount, false, (FloatBuffer) matCompound.getBuffer());
                    } else if (extensionAvailable) {
                        gl.glUniformMatrix4x3dv(location, arrayAmount, false, (DoubleBuffer) matCompound.getBuffer());
                    }

                }
            }
        }
    }

    public void start() {
        gl.glUseProgram(prgID);
    }

    public void stop() {
        gl.glUseProgram(0);
    }

    public void clear() {
        stop();
        gl.glDetachShader(prgID, fragmentShaderID);
        gl.glDetachShader(prgID, vertexShaderID);
        gl.glDeleteShader(vertexShaderID);
        gl.glDeleteShader(fragmentShaderID);
        gl.glDeleteProgram(prgID);
    }

    public abstract void getAllUniformsAndAttribs();

    public int getUniform(String varName) {
        int tempID = gl.glGetUniformLocation(prgID, varName);
        return tempID;
    }

    private int load(String file, int type) {
        String[] temp;
        String fullName;
        StringBuilder source = new StringBuilder();
        boolean isUniformType = false;
        boolean isStructType = false;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                isStructType = line.contains("struct");
                temp = line.split(" ");
               
                int index = (temp.length) - 1;

                if (temp[index].contains("[")) {
                    int bracketIndex = temp[index].indexOf("[");
                    temp[index] = temp[index].substring(0, bracketIndex);
                    System.out.println("sdadad: "+temp[index]);
                }
                if (index > 0) {
                    if (temp[index - 1].contains("[")) {
                        int bracketIndex = temp[index - 1].indexOf("[");
                        temp[index - 1] = temp[index - 1].substring(0, bracketIndex);
                    }
                }

                fullName = temp[index].replaceAll(";", "");
                boolean advance = true;

                if (line.matches(".*(uniform\\s+.*)[^\\;]") || line.matches(".*(struct\\s+.*)[^\\;]")) {
                    String tempStr = temp[index];
                    if(tempStr.equals("{")){
                        tempStr = temp[index-1];
                        
                    }
                    tempStr = tempStr.replaceAll("\\{", "");
                    
                    Tuple temptpl = new Tuple("-", -1, tempStr, isStructType);
                   // System.out.println("TUPLE First: " + temptpl.getName() + " " + temptpl.getGLSLType());
                    structElements.add(temptpl);
                    isUniformType = true;
                    advance = false;
                    if (line.contains("}")) {
                        isUniformType = false;
                    }

                }
                if (isUniformType && line.contains("}")) {
                    isUniformType = false;
                   
                } else if (isUniformType && (index > 0) && advance) {
                    Tuple temptpl = new Tuple(temp[index - 1], -1, fullName, isStructType);
                    //System.out.println("TUPLE Second: " + temptpl.getName() + " " + temptpl.getGLSLType());
                    structElements.add(temptpl);

                }
                advance = true;

                //(layout\\s*\\(\\s*location\\s*=\\s*\\d*\\s*\\)\\s*)*
                if (line.matches(".*(uniform\\s+.*)\\;")) {

                    uniformMap.put(fullName, new Tuple(temp[index - 1], -1, fullName, isStructType));
                } else if (type == gl.GL_VERTEX_SHADER && line.matches(".*(in\\s+.*)\\;")) {

                    attributeMap.put(fullName, new Tuple(temp[index - 1], -1, fullName, isStructType));
                }

                source.append(line).append("\r\n");
            }

             
            addNewValuesToMap();
            reader.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] sourceArr = new String[]{source.toString()};
        int shaderID = gl.glCreateShader(type);

        gl.glShaderSource(shaderID, 1, sourceArr, null);
        gl.glCompileShader(shaderID);
        IntBuffer result = Buffers.newDirectIntBuffer(1);

        gl.glGetShaderiv(shaderID, gl.GL_COMPILE_STATUS, result);

        if (result.get() == gl.GL_FALSE) {
            IntBuffer length = Buffers.newDirectIntBuffer(1);
            ByteBuffer str = Buffers.newDirectByteBuffer(500);
            gl.glGetShaderInfoLog(shaderID, 500, length, str);
            String s = StandardCharsets.UTF_8.decode(str).toString();
            System.out.println(s);
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

    private void addNewValuesToMap() {

        String name = "";
        while (!this.structElements.isEmpty()) {
            Tuple tempTuple = this.structElements.pollFirst();
            
            if (tempTuple.getGLSLType().equals("-")) {
                name = tempTuple.getName();
                continue;
            } else {
                uniformMap.put(name+"."+tempTuple.getName(), tempTuple);
            }

        }

    }

}


