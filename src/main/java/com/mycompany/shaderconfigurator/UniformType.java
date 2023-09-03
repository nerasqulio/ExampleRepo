/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import java.lang.reflect.Type;

/**
 *
 * @author arda.eksi
 */
public abstract class UniformType {
    
  
    private boolean validation = true;
 
    
    public boolean isValidated(){
        return this.validation;
        
    }
    
    public void setValidation(boolean flag){
        this.validation = flag;
    }
    public abstract Type getType();
    
    public abstract int getComponentNr();
    
    
}
