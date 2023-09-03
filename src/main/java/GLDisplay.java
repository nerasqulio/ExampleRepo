/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arda.eksi
 */

import ShittyThings.OBJLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;




public class GLDisplay implements GLEventListener{
	
	public GL2 gl;
	Experiment exp;
        Experiment2 exp2;
        
	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	    gl = drawable.getGL().getGL2();	
	    gl.setSwapInterval(2);
            OBJLoader.loadOBJ("ship2");
            exp = new Experiment();
            exp2 = new Experiment2();

            
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		
	
	}

	@Override
	public void display(GLAutoDrawable drawable) {		
         
           exp2.clearScreen();
	    exp.render();
            exp2.render();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
