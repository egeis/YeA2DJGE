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

import java.util.Random;
import java.util.logging.Level;
import main.java.com.YeAJG.fx.particle.Emitter;
import main.java.com.YeAJG.fx.particle.IEmitUpdater;
import main.java.com.YeAJG.fx.particle.Particle;
import main.java.com.YeAJG.game.io.ConfigHandler;
import main.java.com.YeAJG.game.io.InputHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
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
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    
    public static final String Name = "Example";

    private Particle p;
    private Emitter emit;
    
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

            //Single Particle Test
        p = new Particle(
            new Vector3f(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 10, 0),
            new Vector3f(10.0f,10.0f,0.0f),
            new Vector3f(1.0f,1.0f,1.0f),
            new Vector3f(0f,0f,0f),
            new Vector3f((float) (r.nextInt(2) - 1), (float) (r.nextInt(2) - 2), 0f),
            new Vector3f(0, -0.05f, 0),
            1,
            0,
            1,
            true );
        
        try {    
            try {
                emit = new Emitter((IEmitUpdater) Class.forName("main.java.com.YeAJG.fx.particle.emitters.FountainEmitter").newInstance(), p, 1000);
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
         GL11.glMatrixMode(GL11.GL_PROJECTION);
         GL11.glLoadIdentity();
         GL11.glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 1, -1);
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         GL11.glEnable(GL11.GL_BLEND);
         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
     }
    
    private void doTick( long next_game_tick )
    {
        //p.update( next_game_tick );        
    }
 
    private void render( float interpolation ) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        //if( !p.isDead() ) p.draw( interpolation );
        
        Display.update();
        
    }
    
}