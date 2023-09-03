/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

import java.lang.reflect.Field;

/**
 *
 * @author arda.eksi
 */
public class ShaderParameterFieldTuple {
    
    private ShaderParameter parameter;
    private Field field;
    
    public ShaderParameterFieldTuple(ShaderParameter parameter, Field field){
        this.field = field;
        this.parameter = parameter;
    }
    
    
    public ShaderParameter getParameter(){
        return this.parameter;
    }
    public Field getField(){
        return this.field;
    }
    
}
