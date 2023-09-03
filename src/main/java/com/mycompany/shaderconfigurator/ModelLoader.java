/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

/**
 *
 * @author arda.eksi
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.ImageUtil;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.nio.DoubleBuffer;

public class ModelLoader {
	
	
        private static ModelLoader instance;
	private GL4 gl;
        private List<Integer[]> vaoIDList = new ArrayList<Integer[]>();
	private int[] vaoID = new int[1];
	private int[] vboID = new int[1];
        //private List<Integer> attrID = new ArrayList<Integer>();
	private List<Integer> vaoArr = new ArrayList<Integer>();
	private List<Integer> vboArr = new ArrayList<Integer>();
	private List<Integer> textureArr = new ArrayList<Integer>();
	
        private int modelID;
        private int indexArrLength;
        private boolean isVAOCreated = false;
        
	
	
       
           
        
        public static ModelLoader getInstance(){
            
           
            if(instance == null){
                instance = new ModelLoader();
                instance.setGL();
                
                
            }
            return instance;
        }
        private void setGL(){
            this.gl = (GL4)(GLContext.getCurrentGL());
            
        }
	
	public int loadTexture(String fileName) throws IOException{
	
	    File imgPath = new File(fileName);
	    BufferedImage image = ImageIO.read(imgPath);
	    ImageUtil.flipImageVertically(image);
	    int[] pixels = new int[image.getWidth() * image.getHeight()];
	    image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
	    ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

	    for(int h = 0; h < image.getHeight(); h++) {
	        for(int w = 0; w < image.getWidth(); w++) {
	            int pixel = pixels[h * image.getWidth() + w];

	            buffer.put((byte) ((pixel >> 16) & 0xFF));
	            buffer.put((byte) ((pixel >> 8) & 0xFF));
	            buffer.put((byte) (pixel & 0xFF));
	            buffer.put((byte) ((pixel >> 24) & 0xFF));
	        }
	    }

	    buffer.flip();
	    int[] id = new int[1];
	    
           
	 
	    gl.glGenTextures(1, id,0);
	   
	    gl.glBindTexture(gl.GL_TEXTURE_2D, id[0]);
	    gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_S, gl.GL_REPEAT);
	    gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_T, gl.GL_REPEAT);
	    gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER,gl.GL_LINEAR_MIPMAP_LINEAR);
	    gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

	    gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, gl.GL_RGBA, image.getWidth(), image.getHeight(),0,gl.GL_RGBA, gl.GL_UNSIGNED_BYTE, buffer);
	    gl.glGenerateMipmap(gl.GL_TEXTURE_2D);
           

	    
	    return id[0];
	
		
	}
        
        public void loadIndex(int[] indexArr){
            if(!this.isVAOCreated){
                 this.modelID = createVAO();
                 this.isVAOCreated = true;
            }
            if(indexArr != null){
                this.indexArrLength = indexArr.length;
                indexVBO(indexArr);  
           }
        }
        
        public void loadVAO(int id,float[] attrArr,int componentSize){
            
            if(!this.isVAOCreated){
                 this.modelID = createVAO();
                 this.isVAOCreated = true;
            }
            if(attrArr != null){
              //   this.attrID.add(id);
                 storeDataInVAO(id,attrArr,componentSize);
            }
            
        }
        public Model getModel(int texID){
          
                this.isVAOCreated = false;
		return new Model(this.modelID,texID, this.indexArrLength);
        }
    
	public void removeVBOandVAO(){
		
		
		gl.glDeleteVertexArrays(1,vaoID,0);	
		gl.glDeleteBuffers(vboArr.size(),new int[] {1,2,3,4},0);	
		
	}
	
	private int createVAO(){
		
		
		gl.glGenVertexArrays(1,vaoID,0);
		vaoArr.add(vaoID[0]);
		gl.glBindVertexArray(vaoID[0]);
		return vaoID[0];
	}
	private void storeDataInVAO(int attributeCount, float[] data, int totalComponentsSize)	{
		
		gl.glGenBuffers(1,vboID,0); 
		vboArr.add(vboID[0]);
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vboID[0]);
		FloatBuffer buffer = convertToFloatBuffer(data);
		gl.glBufferData(gl.GL_ARRAY_BUFFER,(data.length * 4), buffer, gl.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(attributeCount, totalComponentsSize, gl.GL_FLOAT, false, 0,0);	
                gl.glEnableVertexAttribArray(attributeCount);
                 
		
	}
	private FloatBuffer convertToFloatBuffer(float[] data){
            
		FloatBuffer buffer = Buffers.newDirectFloatBuffer(data.length);
		buffer.position(0);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
        private DoubleBuffer convertToDoubleBuffer(double[] data){
            
                DoubleBuffer buffer = Buffers.newDirectDoubleBuffer(data.length);
		buffer.position(0);
		buffer.put(data);
		buffer.flip();
		return buffer;
        }
	private void unbind(){
            
		gl.glBindVertexArray(0);
	}
	
	private void indexVBO(int[] index){
		
		gl.glGenBuffers(1,vboID,0);
		vboArr.add(vboID[0]);
		gl.glBindBuffer(gl.GL_ELEMENT_ARRAY_BUFFER, vboID[0]);
		IntBuffer temp = convertToIntBuffer(index);
		gl.glBufferData(gl.GL_ELEMENT_ARRAY_BUFFER,(index.length * 4), temp, gl.GL_STATIC_DRAW);
		
		
		
	}
	private IntBuffer convertToIntBuffer(int[] data){
            
		IntBuffer buffer = Buffers.newDirectIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

    
}