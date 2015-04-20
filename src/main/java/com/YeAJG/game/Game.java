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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import main.java.com.YeAJG.fx.particle.Emitter;
import main.java.com.YeAJG.fx.particle.IEmitUpdater;
import main.java.com.YeAJG.fx.particle.Particle;
import main.java.com.YeAJG.fx.particle.graphics.filters.MotionBlurPostFilter;
import main.java.com.YeAJG.game.io.ConfigHandler;
import main.java.com.YeAJG.game.io.InputHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import org.lwjgl.util.Color;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Game implements Runnable {
    private static Game instance = null;
    private static final Logger logger = LogManager.getLogger( Game.class.getName() );
        
    protected ConfigHandler Config;
    protected InputHandler Input;
    
    private long window;
    
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    
    public static final String Name = "Example";

    private Particle p;
    private Emitter emit;
    
    private int fps;
    private long lastFPS;
            
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
        init();
    }
    
    @Override
    public void run() {  
        final int TICKS_PER_SECOND = 25;
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIP = 5;

        long next_game_tick = System.currentTimeMillis();
        int loops;
        float interpolation; 
        
        Random r = new Random();

        Map<String, Object> parameters = new HashMap();
        parameters.put("Age.Count", 0.0f );
        parameters.put("Age.Max", 2.0f );
        parameters.put("Age.Step", 1.0f );
        parameters.put("Distance.Min", 0.25f);
        parameters.put("Distance.Max", 3.0f);
        parameters.put("Death.FADE.Step", 5);
        parameters.put("Death.FADE.End", 0);
        
        //Single Particle Test
        p = new Particle(
            new Vector3f(0,0,0),
            new Vector3f(0.2f,10.0f,0.0f),
            new Vector3f(1.0f,1.0f,1.0f),
            new Vector3f( 10f, -50.0f, 0f),
            new Vector3f(0, 0.0f, 0),
            new Vector3f(0.0f,0.0f,10.0f),
            new Vector3f(0,0,0),
            parameters,
            new Color(0,0,255,255),
            false );
        
        try {    
            try {
                Vector3f location = new Vector3f(0, WINDOW_HEIGHT + 10, 0);
                Vector3f size = new Vector3f(1024.0f,10.0f,100.0f);
                
                emit = new Emitter((IEmitUpdater) Class.forName("main.java.com.YeAJG.fx.particle.emitters.CodeEmitter").newInstance(), 
                        p, location, size, 4, 1000);
            } catch (InstantiationException | IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
                
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
        
        Display.destroy();
    }
 
    /**
     * Initiates Display.
     */
    private void init() {
        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setTitle(Name+" "+ConfigHandler.getVersion());
            Display.create();
        } 
        catch(LWJGLException e)
        {
            logger.fatal(e.getMessage());
            System.exit(-1);
        }
        
        initGL();
    }
    
    /**
     * Initiates GL.
     */
     private void initGL()
     {
        GL11.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //gluPerspective(60f, ((float)WINDOW_WIDTH) / ((float)WINDOW_HEIGHT), 0.001f, 100.0f);
        GL11.glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 0, 110);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glShadeModel(GL_SMOOTH);   // Enable smooth shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); 
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
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
        emit.update(next_game_tick);
    }
 
    private void render( float interpolation ) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        //if( !p.isDead() ) p.draw( interpolation );
        emit.draw();
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
    
}