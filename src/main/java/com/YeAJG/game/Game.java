/*
 * The MIT License
 *
 * Copyright 2015 Richard Coan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package main.java.com.YeAJG.game;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import main.java.com.YeAJG.fx.ps.Emitter;
import main.java.com.YeAJG.fx.ps.Particle;
import main.java.com.YeAJG.game.io.ConfigHandler;
import main.java.com.YeAJG.game.io.InputHandler;
import main.java.com.YeAJG.game.utils.Conversions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;



/**
 *
 * @author Richard Coan
 */
public class Game implements Runnable {
    private static Game instance = null;
    private static final Logger logger = LogManager.getLogger( Game.class.getName() );
    public static final String Name = "YeAJGE: Demonstration";
    
    protected ConfigHandler Config;
    protected InputHandler Input;
    
    private long lastFPS;
    private int fps;
    
    private Particle p;
    private Emitter emit;
    
    //Global Static Values
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static Vector3f cameraPos = null;
    
    //Moving Varibles
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private FloatBuffer matrix44Buffer = null;
            
    public static Game getInstance() {
        if(instance == null) instance = new Game();
        return instance;
    }
    
    private Game() {                
        Config = ConfigHandler.getInstance();
        Input = InputHandler.getInstance();
        
        //Load Screen Size
        WINDOW_WIDTH = Config.getSettings().getJsonObject("display").getInt("width");
        WINDOW_HEIGHT = Config.getSettings().getJsonObject("display").getInt("height");  
        
        lastFPS = getTime();
        
        setupGL();
        setupMatrices();
        
        cameraPos = new Vector3f(0, 0, -1);
    }
    
    private void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)WINDOW_WIDTH / (float)WINDOW_HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;
         
        float y_scale = Conversions.coTangent(Conversions.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;
         
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
                projectionMatrix.m33 = 0;
         
        // Setup view matrix
        viewMatrix = new Matrix4f();
                  
        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
    }
    
    @Override
    public void run() {  
        final int TICKS_PER_SECOND = 25;
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIP = 5;

        long next_game_tick = System.currentTimeMillis();
        int loops;
        float interpolation; 
            
        while(!Display.isCloseRequested())
        {
            loops = 0;
            while( System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                doTick( next_game_tick );

                next_game_tick += SKIP_TICKS;
                loops++;
            }
            
            interpolation = (float) ( System.currentTimeMillis() + 
                    SKIP_TICKS - next_game_tick ) / (float) SKIP_TICKS;
            render( interpolation );
            updateFPS();
        }
        
        this.destroyGL();
        Display.destroy();
    }
 
    /**
     * Initiates Display and OpenGL.
     */
    private void setupGL() {
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
            
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setTitle(Name+" "+ConfigHandler.getVersion());
            Display.create(pixelFormat,contextAtrributes);
        } 
        catch(LWJGLException e)
        {
            logger.fatal(e.getMessage());
            System.exit(-1);
        }
        
        //Creates the Viewport.       
        GL11.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); 
        
        //Sets the Background color.
        GL11.glClearColor(0.3f, 0.3f, 0.3f, 0.0f);
    }
    
     /**
      * Destroys OpenGL
      */
     private void destroyGL()
     {
         
     }
     
    /**
     * Get the time in milliseconds
     * 
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    private void doTick( long next_game_tick )
    {

    }
 
    private void render( float interpolation ) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        Display.sync(60);
        Display.update();
    }
    
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle(Name+" "+ConfigHandler.getVersion()+" (FPS: " + fps+")"); 
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }
    
    public static void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();
         
        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            logger.error(errorMessage + ": " + errorString);
             
            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }
    }
    
}