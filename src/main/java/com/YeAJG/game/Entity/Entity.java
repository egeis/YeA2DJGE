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
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.utils.VertexData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public abstract class Entity implements Cloneable {
    protected static final Logger logger = LogManager.getLogger( Game.class.getName() );
   
    protected boolean visible = true;
    
    protected Vector3f modelPos = null;
    protected Vector3f modelAngle = null;
    protected Vector3f modelScale = null;
    protected Matrix4f modelMatrix = null;          
    
    protected Vector3f modelVelcity;         
    protected Vector3f modelAccel;       
    protected Vector3f modelSpin;
    
    protected int[] texIds;
    protected int textureSelector = 0;
    
    protected int projectionMatrixLocation = 0;
    protected int viewMatrixLocation = 0;
    protected int modelMatrixLocation = 0;
    
    protected VertexData[] vertices = null;
    protected ByteBuffer verticesByteBuffer = null;
    protected int indicesCount = 0;
    protected int vaoId = 0;
    protected int vboId = 0;
    protected int vboiId = 0;
    
    protected int pId = 0;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public Vector3f getModelPos() {
        return modelPos;
    }

    public void setModelPos(Vector3f modelPos) {
        this.modelPos = new Vector3f(modelPos);
    }

    public Vector3f getModelAngle() {
        return modelAngle;
    }

    public void setModelAngle(Vector3f modelAngle) {
        this.modelAngle = new Vector3f(modelAngle);
    }

    public Vector3f getModelScale() {
        return modelScale;
    }

    public void setModelScale(Vector3f modelScale) {
        this.modelScale = new Vector3f(modelScale);
    }

    public Vector3f getModelVelcity() {
        return modelVelcity;
    }

    public void setModelVelcity(Vector3f modelVelcity) {
        this.modelVelcity = new Vector3f(modelVelcity);
    }

    public Vector3f getModelAccel() {
        return modelAccel;
    }

    public void setModelAccel(Vector3f modelAccel) {
        this.modelAccel = new Vector3f(modelAccel);
    }

    public Vector3f getModelSpin() {
        return modelSpin;
    }

    public void setModelSpin(Vector3f modelSpin) {
        this.modelSpin = new Vector3f(modelSpin);
    }
    
    public void destroy()
    {
        // Delete the texture
        GL11.glDeleteTextures(texIds[0]);
        GL11.glDeleteTextures(texIds[1]);
                         
        // Delete the shaders
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(pId);
         
        // Select the VAO
        GL30.glBindVertexArray(vaoId);
         
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
         
        // Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);
         
        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboiId);
         
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }
    
    
}
