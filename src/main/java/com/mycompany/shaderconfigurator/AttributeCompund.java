/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shaderconfigurator;

/**
 *
 * @author arda.eksi
 */
public class AttributeCompund {
    
    
    private int id;
    private Usage usage;
    
    
    public AttributeCompund(Usage usage, int id){
        this.usage = usage;
        this.id = id;
    }
    
    public Usage getUsage(){
        return this.usage;
    }
    public int getID(){
        return this.id;
    }
    
}
