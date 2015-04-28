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
package main.java.com.example.primitives;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.YeAJG.api.IEntity;
import main.java.com.YeAJG.game.Entity.Entity;
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.io.FileIOHandler;
import main.java.com.YeAJG.game.utils.Conversions;
import main.java.com.YeAJG.game.utils.VertexData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Quad extends Entity implements IEntity {
    private void setupShaders()
    {
        // Load the vertex shader and fragment shader
        int vsId;
        int fsId;
        
        try {
            vsId = FileIOHandler.loadShader("assets/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);      
            fsId = FileIOHandler.loadShader("assets/shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
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
    
    private void setupQuad() {         
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
        VertexData v0 = new VertexData(); 
        v0.setXYZ(-0.5f, 0.5f, 0); v0.setRGB(1, 0, 0); v0.setST(0, 0);
        VertexData v1 = new VertexData(); 
        v1.setXYZ(-0.5f, -0.5f, 0); v1.setRGB(0, 1, 0); v1.setST(0, 1);
        VertexData v2 = new VertexData(); 
        v2.setXYZ(0.5f, -0.5f, 0); v2.setRGB(0, 0, 1); v2.setST(1, 1);
        VertexData v3 = new VertexData(); 
        v3.setXYZ(0.5f, 0.5f, 0); v3.setRGB(1, 1, 1); v3.setST(1, 0);
         
        vertices = new VertexData[] {v0, v1, v2, v3};
         
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
    
    private void setupTextures() {
        try {
            texIds[0] = FileIOHandler.loadPNGTexture("assets/textures/stGrid1.png", GL13.GL_TEXTURE0);
            texIds[1] = FileIOHandler.loadPNGTexture("assets/textures/stGrid2.png", GL13.GL_TEXTURE0);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
         
        Game.exitOnGLError("setupTexture");
    }
    
    @Override
    public void Setup(Vector3f pos, Vector3f angle, Vector3f scale) {
        // Setup model matrix
        modelMatrix = new Matrix4f();
        
        //Default Position, Angle, Scale of the Quad.
        modelPos = new Vector3f(pos);
        modelAngle = new Vector3f(angle);
        modelScale = new Vector3f(scale);
        
        texIds = new int[2];
        
        this.setupQuad();
        this.setupShaders();
        this.setupTextures();
    }

    @Override
    public void Tick() {
        
        //-- Input processing
        float rotationDelta = 15f;
        float scaleDelta = 0.1f;
        float posDelta = 0.1f;
        Vector3f scaleAddResolution = new Vector3f(scaleDelta, scaleDelta, scaleDelta);
        Vector3f scaleMinusResolution = new Vector3f(-scaleDelta, -scaleDelta, 
                -scaleDelta);
        
        //TODO: Stuff
        
        //-- Update matrices
        // Reset view and model matrices
        Game.viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
         
        // Translate camera
        Matrix4f.translate(Game.cameraPos, Game.viewMatrix, Game.viewMatrix);
         
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
}