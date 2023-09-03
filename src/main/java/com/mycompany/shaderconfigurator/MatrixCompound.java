/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author arda.eksi
 */
public class MatrixCompound {
    
    
    private Buffer matrixBuffer;
    private int firstDimension;
    private int secondDimension;   
    private int arrayAmount;
    
    public MatrixCompound(Buffer matrixBuffer,int arrayAmount, int firstDimension, int secondDimension){
        
        this.arrayAmount = arrayAmount;
        this.matrixBuffer = matrixBuffer;
        this.firstDimension = firstDimension;
        this.secondDimension = secondDimension;       
        this.matrixBuffer.flip();
    }
    
    public boolean isSingleDimension(){
        return this.secondDimension == 0 ? true : false;
    }
    
    public int getFirstDimension(){
        return this.firstDimension;
    }
    public int getSecondDimension(){
        return this.secondDimension;
    }
    public boolean isFloat(){
        return (matrixBuffer instanceof FloatBuffer);
    }
    public Buffer getBuffer(){
        return this.isFloat() ? (FloatBuffer)this.matrixBuffer : (DoubleBuffer)this.matrixBuffer;
    }
    public int getArrayAmount(){
        return this.arrayAmount;
    }
    
    
    
}
