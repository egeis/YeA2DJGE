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
package game;

import game.io.ConfigHandler;
import game.io.InputHandler;
import java.awt.Canvas;
import java.awt.Dimension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Richard Coan
 */
public class Game extends Canvas implements Runnable {
    private static Game instance = null;
    private static final Logger LOG = LogManager.getLogger( Game.class.getName() );
    
    public static final String Name = "ConnectIT";
    public final int SCALE = 4;
    public int WIDTH = 160;
    public int HEIGHT = WIDTH / 12 * 9;
    
    public final Dimension MIN_DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    public Dimension TARGET_DIMENSIONS;
    
    protected ConfigHandler config;
    protected InputHandler input;

    public static Game getInstance() {
        if(instance == null) instance = new Game();
        return instance;
    }
    
    private Game() {                
        config = ConfigHandler.getInstance();
        
        if(config.containsKey("screen.width") && config.containsKey("screen.height") )
        {
            WIDTH = Integer.parseInt(config.get("screen.width").toString()) / SCALE;
            HEIGHT = Integer.parseInt( (String) config.get("screen.height")) / SCALE;
            TARGET_DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
            
            config.replace("screen.width", WIDTH);
            config.replace("screen.height", HEIGHT);
        } else {
            TARGET_DIMENSIONS = MIN_DIMENSIONS;
        }
        
        input = new InputHandler();
        config.store();
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
