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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import main.java.com.YeAJG.api.IEntity;
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.io.FileIOHandler;
import main.java.com.YeAJG.game.utils.Conversions;
import main.java.com.YeAJG.game.utils.VertexData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public abstract class Entity implements Cloneable, IEntity {
    protected static final Logger logger = LogManager.getLogger( Game.class.getName() );
   
    protected boolean visible = true;
    
    protected Vector3f modelPos = null;
    protected Vector3f modelAngle = null;
    protected Vector3f modelScale = null;
    protected Matrix4f modelMatrix = null;          
    
    protected Vector3f modelVelcity = new Vector3f(0,0,0);         
    protected Vector3f modelAccel = new Vector3f(0,0,0); ;       
    protected Vector3f modelSpin = new Vector3f(0,0,0); ;
    
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

    @Override
    public void Setup(Vector3f pos, Vector3f angle, Vector3f scale,
            String shaderPath, String fragmentPath, String[] texturePaths,
            Vector3f[] vertex, Vector3f[] color, Vector2f[] uv) {
        // Setup model matrix
        modelMatrix = new Matrix4f();
        
        //Default Position, Angle, Scale of the Quad.
        this.setModelPos(pos);
        this.setModelAngle(angle);
        this.setModelScale(scale);
        
        if(uv != null && color != null && vertex != null )
            this.SetupEntity(vertex, color, uv);
        
        if(!shaderPath.equals("") && !fragmentPath.equals("")
                ) this.SetupShaders(shaderPath, fragmentPath);
        
        if(texturePaths != null) this.SetupTextures(texturePaths);
    }
    
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    private void SetupShaders(String shaderPath, String fragPath)
    {
        // Load the vertex shader and fragment shader
        //TODO: Shader Handler.
        int vsId;
        int fsId;
        
        try {
            vsId = FileIOHandler.loadShader(shaderPath, GL20.GL_VERTEX_SHADER);      
            fsId = FileIOHandler.loadShader(fragPath, GL20.GL_FRAGMENT_SHADER);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return;
        }
         
        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);
 
        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");
 
        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);
 
        // Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,"projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
 
        Game.exitOnGLError("setupShaders");
    }
    
    private void SetupTextures(String[] texturePaths) {
        texIds = new int[texturePaths.length];
        
        try {
            for(int i = 0; i < texIds.length; i++)
            {
                texIds[i] = FileIOHandler.loadPNGTexture(texturePaths[i], GL13.GL_TEXTURE0);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
         
        Game.exitOnGLError("setupTexture");
    }
    
    private void SetupEntity(Vector3f[] vertex, Vector3f[] color, 
            Vector2f[] uv) {         
        
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
        vertices = new VertexData[vertex.length];
        
        for(int i = 0; i < vertex.length; i++)
        {
            vertices[i] = new VertexData(); 
            vertices[i].setXYZ(vertex[i].x, vertex[i].y, vertex[i].z);
            vertices[i].setRGBA(color[i].x, color[i].y, color[i].z, 1.0f);
            vertices[i].setST(uv[i].x, uv[i].y);
        }    
         
        // Put each 'Vertex' in one FloatBuffer
        verticesByteBuffer = BufferUtils.createByteBuffer(vertices.length * 
                VertexData.stride);             
        FloatBuffer verticesFloatBuffer = verticesByteBuffer.asFloatBuffer();
        for (int i = 0; i < vertices.length; i++) {
            // Add position, color and texture floats to the buffer
            verticesFloatBuffer.put(vertices[i].getElements());
        }
        verticesFloatBuffer.flip();
         
        // OpenGL expects to draw vertices in counter clockwise order by default
        // TODO: Move this to Setup.
        byte[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        
        indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
         
        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
         
        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STREAM_DRAW);
         
        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, VertexData.positionElementCount, GL11.GL_FLOAT, 
                false, VertexData.stride, VertexData.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, VertexData.colorElementCount, GL11.GL_FLOAT, 
                false, VertexData.stride, VertexData.colorByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, VertexData.textureElementCount, GL11.GL_FLOAT, 
                false, VertexData.stride, VertexData.textureByteOffset);
         
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
         
        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);
         
        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, 
                GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
         
        Game.cameraPos = new Vector3f(0, 0, -1);
        
        Game.exitOnGLError("setupQuad");
    } 
    
    @Override
    public void Render(float interpolation) {
         
            GL20.glUseProgram(pId);

            // Bind the texture
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[textureSelector]);

            // Bind to the VAO that has all the information about the vertices
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            // Bind to the index VBO that has all the information about the order of the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

            // Draw the vertices
            GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

            // Put everything back to default (deselect)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);

            GL20.glUseProgram(0);
         
        Game.exitOnGLError("renderCycle");
    }
    
    @Override
    public void Tick() {
        // Update Positions
        Vector3f.add(this.modelAngle, this.modelSpin, this.modelAngle);
        Vector3f.add(this.modelVelcity, this.modelAccel, this.modelVelcity);
        Vector3f.add(this.modelPos, this.modelVelcity, this.modelPos);

        // Reset view and model matrices
        modelMatrix = new Matrix4f();
         
        // Scale, translate and rotate model
        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
        Matrix4f.rotate(Conversions.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1), 
                modelMatrix, modelMatrix);
        Matrix4f.rotate(Conversions.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0), 
                modelMatrix, modelMatrix);
        Matrix4f.rotate(Conversions.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0), 
                modelMatrix, modelMatrix);
         
        // Upload matrices to the uniform variables
        GL20.glUseProgram(pId);
         
        Game.projectionMatrix.store(Game.matrix44Buffer);
        Game.matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, Game.matrix44Buffer);
        Game.viewMatrix.store(Game.matrix44Buffer); 
        Game.matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, Game.matrix44Buffer);
        modelMatrix.store(Game.matrix44Buffer);
        Game.matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, Game.matrix44Buffer);
         
        GL20.glUseProgram(0);
    }
    
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
        if(modelPos != null) this.modelPos = new Vector3f(modelPos);
    }

    public Vector3f getModelAngle() {
        return modelAngle;
    }

    public void setModelAngle(Vector3f modelAngle) {
        if(modelAngle != null) this.modelAngle = new Vector3f(modelAngle);
    }

    public Vector3f getModelScale() {
        return modelScale;
    }

    public void setModelScale(Vector3f modelScale) {
        if(modelScale != null) this.modelScale = new Vector3f(modelScale);
    }

    public Vector3f getModelVelcity() {
        return modelVelcity;
    }

    public void setModelVelcity(Vector3f modelVelcity) {
        if(modelVelcity != null) this.modelVelcity = new Vector3f(modelVelcity);
    }

    public Vector3f getModelAccel() {
        return modelAccel;
    }

    public void setModelAccel(Vector3f modelAccel) {
        if(modelAccel != null) this.modelAccel = new Vector3f(modelAccel);
    }

    public Vector3f getModelSpin() {
        return modelSpin;
    }

    public void setModelSpin(Vector3f modelSpin) {
        if(modelSpin != null) this.modelSpin = new Vector3f(modelSpin);
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
