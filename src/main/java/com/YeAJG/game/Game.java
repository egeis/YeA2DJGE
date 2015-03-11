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


import java.nio.ByteBuffer;

import main.java.com.YeAJG.game.io.ConfigHandler;
import main.java.com.YeAJG.game.io.InputHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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
    }
    
    @Override
    public void run() {
        
        try {
            init();
            loop(); 
        } finally {
            
        }
        
    }
 
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
        
    }
 
    private void loop() {
        while(!Display.isCloseRequested())
        {
            
            Display.update();
        }
        
        Display.destroy();
    }
    
}
