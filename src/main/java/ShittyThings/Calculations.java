package ShittyThings;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arda.eksi
 */
import org.joml.Matrix4f;
//import org.joml.Vector3fc;
import org.joml.Vector3f;



public class Calculations {
	
	public static final float FOV = 60;
	public static final float NEAR = 0.1f;
	public static final float FAR = 100f;
	
	public static float[] transformationMat(Vector3f translation, float[] rx,float scale)
	{
		
            float[] tempArr = new float[16];
		Matrix4f mat = new Matrix4f();
		mat.identity();
		
		mat.translate(translation, mat);		
		mat.rotate((float)Math.toRadians(rx[0]), new Vector3f(1,0,0), mat);
		mat.rotate((float)Math.toRadians(rx[1]), new Vector3f(0,1,0), mat);
		mat.rotate((float)Math.toRadians(rx[2]), new Vector3f(0,0,1), mat);
		mat.scale(new Vector3f(scale,scale,scale), mat);
		
                /*
                for(int i = 16; i < 32; i++){
                    tempArr[i] = 8f;
                }
                */
                mat.get(tempArr);
               
		return tempArr;
		
	}
	public static float[] projectionMat()
	{
            
            float[] tempArr = new float[16];
		Matrix4f projMat = new Matrix4f();
	
		//System.out.println(App.window.getWidth());
		float aspectRatio = 1920.0f / 1080.0f;
		float yScale = (float)((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustumLength = FAR - NEAR;
		
		
		projMat.m00(xScale);
		projMat.m11(yScale);
		projMat.m22(-((FAR + NEAR) / frustumLength));
		projMat.m23(-1);
		projMat.m32(-((2 * NEAR * FAR) / frustumLength));
		projMat.m33(0);
		
                projMat.get(tempArr);
		return tempArr;
		
		
	}
	public static float[] viewMatrix(Camera cam)
	{
            
                float[] tempArr = new float[16];
		Matrix4f viewMat = new Matrix4f();
		viewMat.identity();
		viewMat.rotate((float) Math.toRadians(cam.getPitch()),new Vector3f(1,0,0), viewMat);
		viewMat.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), viewMat);
		Vector3f camPos = cam.getPosition();
		Vector3f negativeCamPos = new Vector3f(-camPos.x,-camPos.y,-camPos.z);
		viewMat.translate(negativeCamPos, viewMat);
                viewMat.get(tempArr);
                
                
                
		return tempArr;
	}
	
}