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
package main.java.com.YeAJG.game.gfx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import main.java.com.YeAJG.game.Base;
import main.java.com.YeAJG.game.io.FileIOHandler;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Richard Coan
 */
public class ShaderHandler extends Base{
    protected static Map<String, Integer> Shaders = new HashMap();
    protected static Map<String, Integer> Fragments = new HashMap();
    
    public static int loadShader(String path)
    {
        int id = 0;
        if(Shaders.containsKey(path)) return getShader(path);
        
        try {
            id = FileIOHandler.loadShader(path, GL20.GL_VERTEX_SHADER);  
            Shaders.put(path, id);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        
        return id;
    }
    
    public static int loadFrag(String path)
    {
        int id = 0;
        if(Fragments.containsKey(path)) return getFragment(path);
        
        try {
            id = FileIOHandler.loadShader(path, GL20.GL_FRAGMENT_SHADER); 
            Fragments.put(path, id);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        
        return id;
    }
    
    public static int getShader(String path)
    {
        return Shaders.get(path);
    }
    
    public static int getFragment(String path)
    {
        return Fragments.get(path);
    }
    
    
}
