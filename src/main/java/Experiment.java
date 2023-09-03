
import ShittyThings.Calculations;
import ShittyThings.Camera;
import ShittyThings.OBJLoader;
import com.mycompany.shaderconfigurator.ShaderLocationPath;
import com.mycompany.shaderconfigurator.ShaderParameter;
import com.mycompany.shaderconfigurator.ShaderedRenderable;
import com.mycompany.shaderconfigurator.Usage;
import org.joml.Vector3f;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author arda.eksi
 */
public class Experiment extends ShaderedRenderable {

    
    Camera cam = new Camera();
    private float[] rx = new float[]{0,0,0};
    
    @ShaderParameter(Type = int[].class, Usage = Usage.indexAttrib)
    private int[] index = OBJLoader.indexArrS;

    @ShaderLocationPath(shaderType = Usage.vertexShader)
    String vertexShaderPath = "src/vertexShader.txt";

    @ShaderLocationPath(shaderType = Usage.fragmentShader)
    String fragmentShaderPath = "src/fragmentShader.txt";

    @ShaderParameter(Type = String.class, Usage = Usage.textureFilePath)
    private String texturePath = "res/s4.jpg";
    
     @ShaderParameter(Type = String.class, Usage = Usage.textureFilePath)
    private String texturePath2 = "res/s5.jpg";

    @ShaderParameter(Type = float[].class, Usage = Usage.modelAttrib)
    private float[] position = OBJLoader.vertexArrS;

    @ShaderParameter(Type = float[].class, Usage = Usage.modelAttrib)
    private float[] normal = OBJLoader.normalArrS;

    @ShaderParameter(Type = float[].class, Usage = Usage.modelAttrib)
    private float[] textureCoordinates = OBJLoader.textureArrS;

    @ShaderParameter(Type = float.class, Usage = Usage.uniformVariable)
    private float angle = 7f;

    @ShaderParameter(Type = float.class, Usage = Usage.uniformVariable)
    private float shineFactor = 2f;

    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] lightColor = new float[]{1f, 1f, 1f};

    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] lightPos = new float[]{0f, 0f, 0f};

    /*@ShaderParameter(Type = float[].class ,Usage = Usage.standardUniformVariable)
	private float[] Matriz = new float[]{1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f, 13f, 14f, 15f,16f};*/
    @ShaderParameter(Type = int.class, Usage = Usage.uniformVariable)
    private int textureSampler = 0;
    
    @ShaderParameter(Type = int.class, Usage = Usage.uniformVariable)
    private int textureSampler2 = 1;

    @ShaderParameter(Type = float.class, Usage = Usage.uniformVariable)
    private float reflectivity = 0.5f;
    
    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] projectionMatrix = Calculations.projectionMat();
    
    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] viewMatrix = Calculations.viewMatrix(cam);
    
    
    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] Matrix;
    
    /*
    @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] angle2 = new float[]{1f,2f};*/
    
     @ShaderParameter(Type = float[].class, Usage = Usage.uniformVariable)
    private float[] Matriz = new float[]{1f,2f,3f,5f,9f,10f,7f,3f} ;
    
    
   
   
    

    
    
    
    public Experiment() {

    }

    

    @Override
    public void render() {
        
        /*
        this.lightColor[0] += (int) ((9 + (Math.random() * 13))) * 0.001f;
        this.lightColor[1] += (int) ((1 + (Math.random() * 3))) * 0.001f;
        this.lightColor[2] += (int) ((23 + (Math.random() * 30))) * 0.001f;

        */
         Matrix = Calculations.transformationMat(new Vector3f(-6, 0, -20), rx, 1);
         /*
        this.lightColor[0] %= 1f;
        this.lightColor[1] %= 1f;
        this.lightColor[2] %= 1f;
       */
        rx[0]++;
        rx[1]++;
        rx[2]++;
        
        rx[0] %= 360;
        rx[1] %= 360;
        rx[2] %= 360;
        
        super.render();
    }

}
