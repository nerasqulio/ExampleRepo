/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShittyThings;

/**
 *
 * @author arda.eksi
 */
import com.mycompany.shaderconfigurator.Model;
import com.mycompany.shaderconfigurator.ModelLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector3f;

//import org.joml.Vector2f;
//import org.joml.Vector3f;

public class OBJLoader {
	
public static float[] vertexArrS; 
public static int[] indexArrS;
public static float[] textureArrS;
public static float[] normalArrS;	
    
	/*public static Model loadDirectModel(float[] vertex, int[] index, float[] texture, float[] normals)
	{
            ModelLoader mdl = ModelLoader.getInstance();
		return mdl.loadVAO(new int[]{0,1,2},vertex, index, texture,normals,"");
	}*/
	public static void loadOBJ(String fileName)
	{
		 ModelLoader mdl = ModelLoader.getInstance();
		boolean textureBreakPoint = true;
		boolean check = true;
		FileReader fr = null;
		try {
			 fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("File could not be loaded");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		
		List<Vector3f> vertexList = new ArrayList<Vector3f>();
		List<Vector2f> textureList = new ArrayList<Vector2f>();
		List<Vector3f> normalVecList = new ArrayList<Vector3f>();
		List<Integer> indexList = new ArrayList<Integer>();
		float[] vertexArr = null;
		float[] normalArr = null;
		float[] textureArr = null;
		float[] textureArr2 = null;
		int[] indexArr = null;
				
		float tempX;
		float tempY;
		float tempZ;
				
		String line;
		try {
			while((line = reader.readLine()) != null)
			{
				
				
				String[] tempLine = line.split(" ");
				if(line.startsWith("v "))
				{
					tempX = Float.parseFloat(tempLine[1]);
					tempY = Float.parseFloat(tempLine[2]);
					tempZ = Float.parseFloat(tempLine[3]);
					
					vertexList.add(new Vector3f(tempX, tempY, tempZ));    
				}
				else if(line.startsWith("vt "))
				{
					textureList.add(new Vector2f(Float.parseFloat(tempLine[1]), Float.parseFloat(tempLine[2])));
				}
				else if(line.startsWith("vn "))
				{
					normalVecList.add(new Vector3f(Float.parseFloat(tempLine[1]), Float.parseFloat(tempLine[2]), Float.parseFloat(tempLine[3])));
				}
				else if(line.startsWith("f "))
				{
					if(check)
					{
						textureArr = new float[vertexList.size()*2];
						//textureArr2 = new float[vertexList.size()*2];
						normalArr = new float[vertexList.size()*3];
					
						check = false;
					}
						
                                   
                                        
                                        
					String[] vertexFirst = tempLine[1].split("/");
					String[] vertexSecond = tempLine[2].split("/");
					String[] vertexThird = tempLine[3].split("/");
                                        
                                     
					
                                        
					processVertices(vertexFirst,indexList,textureList,normalVecList,textureArr,textureArr2,normalArr,textureBreakPoint);
					processVertices(vertexSecond,indexList,textureList,normalVecList,textureArr,textureArr2,normalArr,textureBreakPoint);
					processVertices(vertexThird,indexList,textureList,normalVecList,textureArr,textureArr2,normalArr,textureBreakPoint);
                                       
					
						
					
				}
				else if(line.contains("usemtl"))
				{
					textureBreakPoint = !textureBreakPoint;
				}
				else
				{
					continue;
				}
				
				
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vertexArr = new float[vertexList.size()*3];
		indexArr = new int[indexList.size()];
		int tempVertex = 0;
		for(Vector3f vertex: vertexList)
		{
			vertexArr[tempVertex++] = vertex.x;
			vertexArr[tempVertex++] = vertex.y;
			vertexArr[tempVertex++] = vertex.z;
			//System.out.println("VERTEX: "+vertex.x+" ");
			
		}
		for(int i = 0; i < indexList.size();i++)
		{
			indexArr[i] = indexList.get(i);
			
		}
		
                
                vertexArrS = vertexArr;
                indexArrS = indexArr;
                textureArrS = textureArr;
                normalArrS = normalArr;
		//return mdl.loadVAO(new int[]{0,1,2},vertexArr, indexArr, textureArr,normalArr,"");
		//return loader.loadVAO(vertexArr, indexArr, textureArr,normalArr);
	}
	private static void processVertices(String[] vertex, List<Integer> index, List<Vector2f> textures, List<Vector3f> normalVec, float[] textureArr,float[] textureArr2, float[] normalArr, boolean textureCheck)
	{
			
		int currentVertex = Integer.parseInt(vertex[0]) -1; 
		
		index.add(currentVertex);
               
		Vector2f currentTexture = textures.get(Integer.parseInt(vertex[1])-1);
                
		
		//if(!textureCheck)
		//{
			
			textureArr[currentVertex*2] = currentTexture.x;
			textureArr[currentVertex*2+1] = currentTexture.y;
			
			//System.out.println("false");
		
		//}
		/*else
		{
			textureArr2[currentVertex*2] = currentTexture.x;
			textureArr2[currentVertex*2+1] = 1 - currentTexture.y;
			//System.out.println("true");
		}*/
		
		//System.out.println("sdasd "+currentVertex);
		Vector3f currentNormal = normalVec.get(Integer.parseInt(vertex[2])-1);
		normalArr[currentVertex*3] = currentNormal.x;
		normalArr[currentVertex*3+1] = currentNormal.y;
		normalArr[currentVertex*3+2] = currentNormal.z;
			
	}
}