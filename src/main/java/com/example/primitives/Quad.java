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
import main.java.com.YeAJG.api.IEntity;
import main.java.com.YeAJG.game.Entity.AEntity;
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.io.FileIOHandler;
import main.java.com.YeAJG.game.utils.VertexData;
import org.lwjgl.BufferUtils;
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
public class Quad extends AEntity implements IEntity {

    
    private void setupShaders()
    {
        
        int vsId = 0, fsId = 0;
       
        try {
            // Load the vertex shader
            vsId = FileIOHandler.loadShader("assets/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);      
            
            // Load the fragment shader
            fsId = FileIOHandler.loadShader("assets/shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        
        logger.info(vsId + " " + fsId);
        
        if(vsId != 0 && fsId != 0)
        {
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
        }
 
        Game.exitOnGLError("setupShaders");
    }
    
    private void setupQuad() { 
       // Setup model matrix
        modelMatrix = new Matrix4f();
        
        //Default Position, Angle, Scale of the Quad.
        modelPos = new Vector3f(0, 0, 0);
        modelAngle = new Vector3f(0, 0, 0);
        modelScale = new Vector3f(1, 1, 1);
        
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
        
        Game.exitOnGLError("setupQuad");
    } 
    
    private void setupTextures() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setup() {
        this.setupQuad();
        this.setupShaders();
        this.setupTextures();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render() {
        
    }

    

   
    
}
