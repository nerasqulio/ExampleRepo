/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.util.List;

/**
 *
 * @author arda.eksi
 */
public class Model {

    private int ID;
    private int textureID;
    private int vertexNumber;
    private GL4 gl = GLContext.getCurrentGL().getGL4();
    private List<Integer> texIDList;
    private int count;

    public Model(int ID, int textureID, int vertexNumber) {

        this.ID = ID;
        this.textureID = textureID;
        this.vertexNumber = vertexNumber;

    }

    public int getID() {
        return ID;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setTexIDList(List<Integer> texIDList) {
        this.texIDList = texIDList;
    }

    public void draw() {

        gl.glBindVertexArray(0);
        this.gl.glBindVertexArray(this.ID);
        gl.glBindTexture(gl.GL_TEXTURE_2D, 0);

        if (this.texIDList != null) {
            for (int id : this.texIDList) {
                gl.glActiveTexture(gl.GL_TEXTURE0 + count);
                gl.glBindTexture(gl.GL_TEXTURE_2D, id);

                count++;

            }
        }

        gl.glDrawElements(gl.GL_TRIANGLES, getVertexNumber(), gl.GL_UNSIGNED_INT, 0);
        count = 0;
    }

}
