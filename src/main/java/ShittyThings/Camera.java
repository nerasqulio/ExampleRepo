package ShittyThings;


import org.joml.Vector3f;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arda.eksi
 */
public class Camera  {
	
	private Vector3f position = new Vector3f(0,0,0);
   
	private float angle = 0;
	private float distance =3f;
	private float pitch = 6f;
	private float yaw = 0;
	
	
	
	private float getHrDistance()
	{
		return (float) (distance * Math.cos(Math.toRadians(pitch)));
	}
	private float getVrDistance()
	{
		return (float) (distance * Math.sin(Math.toRadians(pitch)));
	}
	
	
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public float getAngle()
	{
		return this.angle;
	}
	public float getPitch()
	{
		return this.pitch;
	}
	public float getYaw()
	{
		return this.yaw;
	}


	


	
	

}

