/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joml.Vector3f;

/**
 *
 * @author arda.eksi
 */
public abstract class ShaderedRenderable implements Renderable {

    private int texID;
    private GL4 gl;
    private boolean initShaders = false;
    private Model model;
    private List<ShaderParameterFieldTuple> parameterList;
    private HashMap<ShaderLocationPath, Field> shaderLocationMap = new HashMap<>();
    private List<Integer> texIDList = new ArrayList<Integer>();

    Shader shader;
    Vector3f position;

    public ShaderedRenderable() {

        this.gl = GLContext.getCurrentGL().getGL4();
        shader = Shader.getInstance(this.gl);
        getAnnotatedFields();

    }

    private void getAnnotatedFields() {

        Class type = this.getClass();
        parameterList = new ArrayList<>();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ShaderParameter.class)) {
                ShaderParameter parameter = field.getDeclaredAnnotation(ShaderParameter.class);
                parameterList.add(new ShaderParameterFieldTuple(parameter, field));
            } else if (field.isAnnotationPresent(ShaderLocationPath.class)) {
                ShaderLocationPath parameter = field.getDeclaredAnnotation(ShaderLocationPath.class);
                this.shaderLocationMap.put(parameter, field);
            }
        }

    }

    @Override
    public void render() {

        if (!initShaders) {

            initShader();
            shader.setAttributes();
            Object obj;

            for (ShaderParameterFieldTuple tuple : this.parameterList) {
                if (tuple.getParameter().Usage() == Usage.textureFilePath) {
                    if (tuple.getField() != null) {
                        try {
                            obj = tuple.getField().get(this);
                            if (obj instanceof String) {
                                try {
                                    // System.out.println("PATH:"+ ((String) obj));
                                    this.texIDList.add(ModelLoader.getInstance().loadTexture((String) obj));

                                } catch (IOException ex) {
                                    Logger.getLogger(ShaderedRenderable.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(ShaderedRenderable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            initAttributes();
            initShaders = true;
        }
        shader.start();
        reInitUniforms();

        this.model.draw();

        shader.stop();
    }

    public void clearScreen() {

        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_CULL_FACE);
        gl.glCullFace(gl.GL_BACK);
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
    }

    private void initShader() {

        for (Entry<ShaderLocationPath, Field> entry : this.shaderLocationMap.entrySet()) {
            ShaderLocationPath key = entry.getKey();
            Field val = entry.getValue();

            Object obj;

            try {
                obj = val.get(this);

                if (obj.getClass().equals(java.lang.String.class)) {
                    shader.loadShader((String) obj, key.shaderType().usage);
                } else {
                    throw new ShaderPathException("Shader path variable doesn't meet the requirements for type String. Found: (" + obj.getClass().getName() + ")");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {

                e.printStackTrace();
            } catch (ShaderPathException e) {

                System.err.println(e);
                System.exit(0);
            }
        }
        shader.loadShaders();
        shader.getAllUniformsAndAttribs();

    }

    private void initAttributes() {

        int attrID = -1;
        int[] indexArray = null;
        float[] floatArr = null;
        int componentSize = -1;
        ModelLoader loader = ModelLoader.getInstance();

        Object obj;

        for (ShaderParameterFieldTuple innerEntry : parameterList) {
            ShaderParameter key = innerEntry.getParameter();
            Field val = innerEntry.getField();

            if (shader.attributeMap.containsKey(val.getName()) || (key.Usage() == Usage.indexAttrib)) {

                if (key.Usage() != Usage.indexAttrib) {

                    attrID = shader.attributeMap.get(val.getName()).getID();
                    String type = shader.attributeMap.get(val.getName()).getGLSLType();

                    if ((type.length() <= 5) && Character.isDigit(type.charAt(type.length() - 1))) {
                        componentSize = Integer.parseInt("" + type.charAt(type.length() - 1));
                    }
                }

                try {
                    obj = val.get(this);
                    if (obj != null) {

                        Object tempObj = TypeConvertor.convert(obj, key.Type());
                        try {
                            if ((key.Usage() == Usage.modelAttrib) && (componentSize != -1)) {

                                floatArr = (float[]) tempObj;

                                loader.loadVAO(attrID, floatArr, componentSize);

                            } else if (key.Usage() == Usage.indexAttrib) {

                                indexArray = (int[]) tempObj;
                                loader.loadIndex(indexArray);
                            }
                        } catch (ClassCastException ex) {
                            System.err.println("Check your 'Type' values in Annotations..Cast operation interrupted!! Make sure both 'Type' attribute and variable type match. Multi-Dimensional arrays are not allowed!!");
                            System.exit(0);
                        }
                    }

                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ShaderedRenderable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        this.model = loader.getModel(this.texID);
        this.model.setTexIDList(this.texIDList);

    }

    private void reInitUniforms() {

        Object obj;
        for (ShaderParameterFieldTuple innerEntry : parameterList) {
            ShaderParameter key = innerEntry.getParameter();
            Field val = innerEntry.getField();
            String name = val.getName();

            if (shader.uniformMap.containsKey(name)) {

                String GLSLType = shader.uniformMap.get(name).getGLSLType();
                try {
                    obj = val.get(this);
                    if (obj == null) {
                        return;
                    }
                    try {
                        if (GLSLType.contains("mat")) {
                            shader.setUniformVariable(TypeConvertor.convertFromArrayToMatrix(obj, GLSLType, key.Type()), name);
                        } else {
                            shader.setUniformVariable(TypeConvertor.convert(obj, key.Type()), name);
                        }

                    } catch (UniformVariableNotFound | UnmatchedUniformType ex) {
                        Logger.getLogger(ShaderedRenderable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ShaderedRenderable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

}
