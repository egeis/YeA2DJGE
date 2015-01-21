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
package game.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Richard Coan
 */
public class ConfigHandler 
{
    private static ConfigHandler instance = null;
    private static String PATH;   
    private static Logger LOG;
        
    private ConfigHandler()
    {
        LOG = LogManager.getLogger( ConfigHandler.class.getName() );
        PATH = System.getProperty("user.dir")
                +System.getProperty("file.separator")
                +"config.json";
        
        File file = new File(PATH);
        
        if(file.exists()) {
            try {
                InputStream input = new FileInputStream(file);
                
                input.close();
            } catch(IOException e) {
                LOG.error("Error loading: Configuration File"
                    +System.getProperty("line.separator")
                    +e.getMessage());
                System.exit(1);
            }
        } else {
            LOG.info("No Config file present, using Default Configuration.");
            PATH = "D:\\Users\\Richard\\Documents\\NetBeansProjects\\ConnectIT\\src\\game\\io\\default.json";       
            file = new File(PATH);
            
            try {
                InputStream input = new FileInputStream(file);
                
                input.close();
            } catch (IOException e) {
                LOG.error("Error loading: Default Configuration File"
                    +System.getProperty("line.separator")
                    +e.getMessage());
                System.exit(1);
            }
        }
    } 
    
    /**
     * Loads the Configuration YML File.  If one is not present
     * then the default file is loaded and and editable copy is stored.
     * @return Properties from the loaded Configuration file.
     */
    public static ConfigHandler getInstance()
    {        
        synchronized(ConfigHandler.class) {
            if(instance == null) instance = new ConfigHandler();
        }
        
        return instance;
    }
}