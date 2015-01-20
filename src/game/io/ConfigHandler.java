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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Richard Coan
 */
public class ConfigHandler extends Properties
{
    private static ConfigHandler instance = null;
    private static String PATH;   
    private static Logger LOG;
    
    private ConfigHandler()
    {
        LOG = LogManager.getLogger( ConfigHandler.class.getName() );
        PATH = System.getProperty("user.dir")+System.getProperty("file.separator");         
    }    
    
    /**
     * Writes this property list (key and element pairs) in this Properties 
     * table to the output stream in a format suitable for loading into a 
     * Properties table using the load(InputStream) method.
     * 
     * Properties from the defaults table of this Properties table (if any) 
     * are not written out by this method. This method outputs the comments,
     * properties keys and values in the same format as specified 
     * in store(Writer), with the following differences:
     * 
     * <li>The stream is written using the ISO 8859-1 character encoding.</li>
     * <li>Characters not in Latin-1 in the comments are written as \\uxxxx for 
     * their appropriate unicode hexadecimal value xxxx.</li>
     * <li>Characters less than \u0020 and characters greater than \u007E in 
     * property keys or values are written as \\uxxxx for the appropriate 
     * hexadecimal value xxxx.</li>
     * 
     * After the entries have been written, the output stream is flushed. 
     * The output stream remains open after this method returns.
     */
    public void store() 
    {
        File config = new File(PATH+"config.properties");
        try {
            OutputStream out = new FileOutputStream(config);
            instance.store(out, "Configuration and Settings");
            out.close();
        } catch(IOException e) {
            LOG.warn("Error saving: Configuration File"
                +System.getProperty("line.separator")
                +e.getMessage());
        } 
    }
    
    /**
     * Loads the Configuration properties File.  If one is not present
     * then the default file is loaded and and editable copy is stored.
     * @return Properties from the loaded Configuration file.
     */
    public static ConfigHandler getInstance()
    {        
        if(instance == null) instance = new ConfigHandler();
        
        File config = new File(PATH+"config.properties");
        
        if(config.exists()) {
            try {
                InputStream input = new FileInputStream(config);
                instance.load(input);
                input.close();
            } catch(IOException e) {
                LOG.error("Error loading: Configuration File"
                    +System.getProperty("line.separator")
                    +e.getMessage());
                System.exit(1);
            }
        } else {            
            try {
                LOG.info("No Config file present, using Default Configuration.");
                config = new File("D:\\Users\\Richard\\Documents\\NetBeansProjects\\ConnectIT\\src\\game\\io\\default.properties");
                InputStream input = new FileInputStream( config );                
                instance.load( input );
                input.close();
            } catch(IOException e) {
                LOG.error("Error loading: Default Configuration File"
                    +System.getProperty("line.separator")
                    +e.getMessage());
                System.exit(1);
            } finally {
                instance.store();   //Creates a new Configuration file from the Default.
            }
        }
                    
        return instance;
    }
    
    /**
     * 
     * @param type 
     */
    public void restoreDefault(String type)
    {
        Properties default_config = new Properties();
        
        try {
            LOG.info("Restoring Default ");
            File default_file = new File("D:\\Users\\Richard\\Documents\\NetBeansProjects\\ConnectIT\\src\\game\\io\\default.properties");
            InputStream input = new FileInputStream( default_file );                
            default_config.load( input );
            input.close();
        } catch(IOException e) {
            LOG.error("Error loading: Default Configuration File"
                +System.getProperty("line.separator")
                +e.getMessage());

            System.exit(1);
        } 
        
        
        
        instance.store();
    }
}