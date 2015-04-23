/*
 * The MIT License
 *
 * Copyright 2015 Richard.
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
package main.java.com.YeAJG.game.Entity;

import java.nio.ByteBuffer;
import java.util.Map;
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.utils.VertexData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public abstract class AEntity {
    protected static final Logger logger = LogManager.getLogger( Game.class.getName() );
   
    public boolean visible;
              
    public Vector3f location;        
    public Vector3f rotation;
    public Vector3f scale; 
    public Vector3f velocity;         
    public Vector3f acceleration;       
    public Vector3f spin;
    
    protected Color color;
    protected int[] textureIds;
    
    protected Vector3f modelPos = null;
    protected Vector3f modelAngle = null;
    protected Vector3f modelScale = null;
    protected Matrix4f modelMatrix = null;
    protected VertexData[] vertices = null;
    protected ByteBuffer verticesByteBuffer = null;
    protected int indicesCount = 0;
    protected int vaoId = 0;
    protected int vboId = 0;
    protected int vboiId = 0;
}
