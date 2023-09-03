/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;
import com.jogamp.opengl.GL4;
public enum Usage {
	
	
	vertexShader(GL4.GL_VERTEX_SHADER),
	fragmentShader(GL4.GL_FRAGMENT_SHADER),
        modelAttrib(7),      
        indexAttrib(8),  
        uniformVariable(9),
        textureFilePath(10);
	
	
	int usage;

	Usage(int i) {
		// TODO Auto-generated constructor stub
		this.usage = i;
	}

}
