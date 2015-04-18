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
 * HTE SOFTWARE.
 */
package main.java.com.YeAJG.fx.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Particle extends AParticle {
          
    /**
     * @param location
     * @param size
     * @param scale
     * @param rotation
     * @param velocity
     * @param acceleration
     * @param ageStep
     * @param maxAge
     * @param keepAlive 
     */
    public Particle(Vector3f location, Vector3f size, Vector3f scale, Vector3f rotation, Vector3f velocity, 
            Vector3f acceleration, float ageStep, float maxAge,
            boolean keepAlive) {
        this.location = location;
        this.prevLocation = new Vector3f(location.x, location.y, location.z);
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.rotation = rotation;
        this.age = 0;
        this.ageStep = ageStep;
        this.maxAge = maxAge;
        this.scale = scale;
        this.size = size;
        this.keepAlive = keepAlive;
        this.visible = true;
        this.texture = null;
        this.color = new Color(255,0,0,255);
    }   
        
//    public void draw( float interpolation ) 
//    {
//        GL11.glColor4b((byte)(color.getRedByte()-128),(byte)(color.getGreenByte()-128),
//                (byte)(color.getBlueByte()-128),(byte)(color.getAlphaByte()-128));
//        
//        GL11.glLineWidth(30.8f);
//        GL11.glBegin(GL11.GL_LINES);
//            GL11.glVertex3f(prevLocation.x, prevLocation.y, prevLocation.z);
//            GL11.glVertex3f(location.x, location.y, location.z);
//        GL11.glEnd();
//    }
//    
//    public void update( long next_game_tick )
//    {        
//        prevLocation.x = location.x;
//        prevLocation.y = location.y;
//        prevLocation.z = location.z;
//
//        Vector3f.add(velocity, acceleration, velocity);
//        Vector3f.add(location, velocity, location);
//
//        age += ageStep;
//    }
}