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
public class Tuple {
    
    private String type;
    
	private int id;
	private boolean isDouble;
        private boolean isBool ;
        private boolean isInteger;
        private boolean isFloat;     
        private boolean isStruct;
        private String name;
        private int uniformArrayDimension;

	public int getID() {
		return this.id;
	}
	
	
	
	public void setID(int id){
		this.id = id;
	}


	public Tuple(String type, int id, String name, boolean isStruct){
               
                this.name = name;
		this.type = type;
		this.id = id;
                this.isStruct = isStruct;
               
                this.uniformArrayDimension = name.split("\\[").length - 1;
                
		if(type.charAt(0) == 'd'){
                    this.isDouble = true;
		}else if(type.charAt(0) == 'b'){
                    this.isBool = true;
                }else if(type.charAt(0) == 'i' || type.charAt(0) == 'u' || type.contains("sampler")){
                    this.isInteger = true;
                }else {
                    this.isFloat = true;
                }
	
	}
	@Override
	public String toString(){
            return this.type+", "+this.id;
	}
        
        public Type getJavaType(){
            
            if(this.isDouble){
                return Double.class;
            }else if(this.isBool){
                return Boolean.class;
            }
            else if(this.isInteger){
                return Integer.class;
            }
            else if(this.isFloat){
                return Float.class;
            }
            else{
                return null;
            }
        }
        public String getGLSLType(){
            return this.type;
        }
        public String getName(){
            return this.name;
        }
        public int getArrayDimension(){
            return this.uniformArrayDimension;
        }
       

}
