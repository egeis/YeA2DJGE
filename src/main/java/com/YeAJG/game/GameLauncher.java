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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.java.com.YeAJG.game.io.ConfigHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

/**
 *
 * @author Richard Coan
 */
public class GameLauncher {
    
    private static final Logger LOG = LogManager.getLogger( Game.class.getName() );
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);
    
    public static Game game;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOG.info(Game.Name+" "+ConfigHandler.getVersion()
                +System.getProperty("line.separator")
                +"Java Version: "+System.getProperty("java.version")
                +System.getProperty("line.separator")
                +"OS: "+System.getProperty("os.name")
                +" v."+System.getProperty("os.version")
                +System.getProperty("line.separator")
                +"LWJGL v."+Sys.getVersion()
                +"Classpath: "+System.getProperty("java.class.path")
        );

        game = Game.getInstance();
        game.run();
    }
}