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

import java.util.concurrent.TimeUnit;
import main.java.com.YeAJG.game.io.ConfigHandler;
import main.java.com.YeAJG.game.io.InputHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 *
 * @author Richard Coan
 */
public class Game implements Runnable {
    private static Game instance = null;
    private static final Logger logger = LogManager.getLogger( Game.class.getName() );
        
    protected ConfigHandler Config;
    protected InputHandler Input;
    
    private int ticks;
    private int frames;
    private long currentTime;
    private long lastTime;
    private long nextFrame;
    private long lastFrame;
    private long nextTick;
    private long lastTick;

    private boolean vSync = false;
    
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    
    public static final String Name = "Example";

    public static Game getInstance() {
        if(instance == null) instance = new Game();
        return instance;
    }
    
    private Game() {      
        lastFrame = System.currentTimeMillis();
        Config = ConfigHandler.getInstance();
        Input = InputHandler.getInstance();
        
        //Load Screen Size
        WINDOW_WIDTH = Config.getSettings().getJsonObject("display").getInt("width");
        WINDOW_HEIGHT = Config.getSettings().getJsonObject("display").getInt("height");
        
        try {
            Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
            Display.setTitle(Name+" "+ConfigHandler.getVersion());
            Display.create();
        } 
        catch(LWJGLException e)
        {
            logger.fatal(e.getMessage());
        }
        
        initGL();
    }
    
    @Override
    public void run() {
        logger.debug("Starting");
        
        ticks = 0;
        frames = 0;
        lastTime = System.nanoTime();
        
        nextTick = 0;
        nextFrame = 0;
        
        while(!Display.isCloseRequested())
        {
            currentTime = System.nanoTime();
            
            if(nextTick <= currentTime)
            {
                ticks++;
                //doTick();
                logger.info("Ticks:"+ticks);
                //nextTick += 10;
            }
                
            if(nextFrame <= currentTime)
            {
                frames++;
                logger.info("Frames:"+frames);
                
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                //render();
                Display.update();
            }
            
            if( (currentTime - lastTime) > TimeUnit.NANOSECONDS.convert(1L, TimeUnit.SECONDS));
            {
                logger.info("Ending Frame:"+frames+" Ticks:"+ticks+ " " 
                        + (currentTime - lastTime) + " "
                +TimeUnit.NANOSECONDS.convert(1L, TimeUnit.SECONDS) );
                ticks = 0;
                frames = 0;
                lastTime = System.nanoTime();
            }
                        
        }
        
        Display.destroy();       
    }
    
    private void initGL()
    {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
    }
}