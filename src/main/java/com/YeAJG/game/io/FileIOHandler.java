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
package main.java.com.YeAJG.game.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import main.java.com.YeAJG.game.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Richard Coan
 */
public class FileIOHandler {
        
    public static String load(String PathToFile) 
            throws FileNotFoundException, IOException
    {
        File file = new File(PathToFile);
        StringBuilder contents = new StringBuilder();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            
            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(
                        System.getProperty("line.separator"));
            }
            
            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        
        return contents.toString();
    }
    
    public static int loadShader(String filename, int type) throws IOException {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
         
        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
         
        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new IOException("Could not compile shader.");
        }
         
        Game.exitOnGLError("loadShader");
        return shaderID;
    }
     
}